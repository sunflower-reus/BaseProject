package com.views.tools.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 */
@SuppressWarnings("unused")
public class RefObject {
    private RefObject parent;
    private Field     field;
    private Object    target;

    public RefObject(Object target) throws RefException {
        checkArgs(target, null, null, true);
        this.target = target;
        this.parent = null;
        this.field = null;
    }

    public RefObject(RefObject parent, Field field) throws RefException {
        checkArgs(null, parent, field, false);
        this.target = null;
        this.parent = parent;
        this.field = field;
    }

    private void checkArgs(Object target, RefObject parent, Field field, boolean isPublic) throws RefException {
        if (isPublic) {
            if (target instanceof RefObject) {
                throw new RefException("target must not be a instance of RefObject!");
            }
        } else {
            if (field == null) {
                throw new RefException("field must not be a null!");
            }

            if (parent == null) {
                throw new RefException("parent must not be a null!");
            }

            if (field.getDeclaringClass() == RefObject.class) {
                throw new RefException("target must not be a instance of RefObject!");
            }
        }
    }

    public RefObject getParent() {
        return this.parent;
    }

    public Class<?> getType() throws RefException {
        if (field != null) {
            return field.getType();
        }
        final Object obj = this.unwrap();
        if (obj == null) {
            return NULL.class;
        } else {
            return obj.getClass();
        }
    }

    public String getName() {
        if (this.field != null) {
            return this.field.getName();
        } else {
            return null;
        }
    }

    public boolean isClass() throws RefException {
        final Class<?> clazz = getType();
        return clazz == Class.class;
    }

    public boolean isArray() throws RefException {
        return getType().isArray();
    }

    public boolean isNull() throws RefException {
        return this.unwrap() == null;
    }

    /**
     * @param name
     *         参数名
     *
     * @return 指定field 值
     *
     * @throws RefException
     */
    public RefObject get(String name) throws RefException {
        if (isTextEmpty(name)) {
            throw new RefException("");
        } else if (this.isClass()) {
            throw new RefException("");
        } else if (this.isNull()) {
            throw new RefException("");
        } else {
            final Field fd = getField(getType(), name);
            return new RefObject(this, fd);
        }
    }

    /**
     * @param value
     *         值
     *
     * @return 设置field 值
     *
     * @throws RefException
     */
    public RefObject set(Object value) throws RefException {
        value = unwrap(value);

        if (parent == null) {
            target = value;
        } else {
            try {
                field.set(parent.unwrap(), value);
            } catch (IllegalAccessException e) {
                throw new RefException("", e);
            } catch (IllegalArgumentException e) {
                throw new RefException("", e);
            }
        }
        return this;
    }

    public RefObject set(String name, Object value) throws RefException {
        final RefObject refObj = this.get(name);
        refObj.set(value);
        return this;
    }

    private static boolean isTextEmpty(String name) {
        return name == null || name.length() == 0;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param name
     *         参数名
     *
     * @return 是否存在
     *
     * @throws RefException
     */
    public boolean contains(String name) throws RefException {
        final Field fd = getField(getType(), name);
        return fd != null;
    }

    public boolean isPrimitive() throws RefException {
        return RefWrapper.isPrimitive(unwrap());
    }

    public Class<?> getClazz() throws RefException {
        return RefWrapper.wrapClass(unwrap().getClass());
    }

    public int getIntClazz() throws RefException {
        return RefWrapper.intWrapClass(unwrap().getClass());
    }

    /**
     * 获取指定
     *
     * @param clazz
     *         类型
     * @param name
     *         参数名
     */
    private static Field getField(Class<?> clazz, String name) {
        while (clazz != null) {

            try {
                return clazz.getField(name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            try {
                final Field fd = clazz.getDeclaredField(name);
                return accessible(fd);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public List<RefObject> getAll() throws RefException {
        List<RefObject> list = new LinkedList<>();

        final List<Field> fdList = getFieldList(getType());

        for (Field fd : fdList) {
            list.add(new RefObject(this, fd));
        }
        return list;
    }

    public HashMap<String, Method> getPublicMethod() throws RefException {
        HashMap<String, Method> map = new HashMap<>();
        for (Method method : getMethods(getType())) {
            map.put(method.getName(), method);
        }
        return map;
    }

    private static List<Field> getFieldList(Class<?> clazz) {
        final List<Field> fieldList = new LinkedList<>();
        while (clazz != null) {

            final Field[] fields = clazz.getFields();
            if (fields != null) {
                Collections.addAll(fieldList, fields);
            }

            final Field[] declaredFields = clazz.getDeclaredFields();
            if (declaredFields != null) {
                Collections.addAll(fieldList, declaredFields);
                for (Field field : declaredFields) {
                    fieldList.add(accessible(field));
                }
            }

            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    private static List<Method> getMethods(Class<?> clazz) {
        final List<Method> methodList = new LinkedList<>();
        while (clazz != null) {

            final Method[] methods = clazz.getMethods();
            if (methods != null) {
                Collections.addAll(methodList, methods);
            }
            clazz = clazz.getSuperclass();
        }
        return methodList;
    }

    /**
     * getMethod()方法返回的是public的Method对象，
     * getDeclaredMethod()返回的Method对象可以是非public的
     * Field的方法同理
     * 访问私有属性和方法，在使用前要通过AccessibleObject类
     * （Constructor、 Field和Method类的基类）中的setAccessible()方法来抑制Java访问权限的检查。
     */
    private static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    public Object unwrap() throws RefException {
        try {
            return target != null ? target : field.get(parent.unwrap());
        } catch (IllegalAccessException e) {
            throw new RefException("", e);
        } catch (IllegalArgumentException e) {
            throw new RefException("", e);
        }
    }

    public static RefObject wrap(Object obj) throws RefException {
        return new RefObject(obj);
    }

    public static Object unwrap(Object obj) throws RefException {
        if (obj instanceof RefObject) {
            return ((RefObject) obj).unwrap();
        }
        return obj;
    }

    public static final class NULL {
    }

    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RefObject) {
            final RefObject refO1 = (RefObject) obj;
            final RefObject refO2 = this;

            if (refO1.target == refO2.target && refO1.parent == refO2.parent && refO1.field == refO2.field) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("target:").append(target).append(",");

        if (parent == null) {
            sb.append("parent:null,");
        } else {
            sb.append("parent:");
            try {
                sb.append(unwrap(parent));
            } catch (RefException e) {
                sb.append(e.getMessage());
            }
            sb.append(",");
        }

        if (field == null) {
            sb.append("field:null");
        } else {
            sb.append("field:").append(field.getDeclaringClass().getName()).append("-").append(field.getName());
        }

        return sb.toString();
    }

    public int hashCode() {
        final int hash0 = System.identityHashCode(this.target);
        final int hash1 = System.identityHashCode(this.parent);
        final int hash2 = System.identityHashCode(this.field);
        return hash0 ^ hash1 ^ hash2;
    }
}