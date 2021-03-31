package com.views.tools.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description: 反射的Utils函数集合. 提供访问私有变量,获取泛型类型Class,提取集合中元素的属性等Utils函数.
 */
@SuppressWarnings("unused")
public class Reflection {
    /**
     * 设置某个成员变量的值
     *
     * @param owner
     *         对象
     * @param fieldName
     *         属性名
     * @param value
     *         值
     *
     * @throws Exception
     */
    public static void setField(Object owner, String fieldName, Object value) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field    field      = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(owner, value);
    }

    /**
     * 可以设置父类的field的值
     *
     * @param owner
     *         对象
     * @param fieldName
     *         属性名
     * @param value
     *         值
     *
     * @throws Exception
     */
    public static void setFieldAll(Object owner, String fieldName, Object value) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field    field      = null;
        for (Class<?> clazz = ownerClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        field.setAccessible(true);
        field.set(owner, value);
    }

    /**
     * 得到某个对象的属性
     *
     * @param owner
     *         对象
     * @param fieldName
     *         属性名
     *
     * @return 该属性对象
     *
     * @throws Exception
     */
    public static Object getField(Object owner, String fieldName) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field    field      = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(owner);
    }

    /**
     * 得到某类的静态属性
     *
     * @param className
     *         类名
     * @param fieldName
     *         属性名
     *
     * @return 该属性对象
     *
     * @throws Exception
     */
    public static Object getStaticField(String className, String fieldName) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Field    field      = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(ownerClass);
    }

    /**
     * 执行某对象方法
     *
     * @param owner
     *         对象
     * @param methodName
     *         方法名
     * @param args
     *         参数
     *
     * @return 方法返回值
     *
     * @throws Exception
     */
    public static Object invokeMethod(Object owner, String methodName, Object... args) throws Exception {

        Class<?> ownerClass = owner.getClass();

        Class<?>[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            if (args[i].getClass() == Integer.class) {          // 一般的函数都是 int 而不是 Integer
                argsClass[i] = int.class;
            } else if (args[i].getClass() == Float.class) {     // 一般的函数都是 float 而不是 Float
                argsClass[i] = float.class;
            } else if (args[i].getClass() == Double.class) {    // 一般的函数都是 double 而不是 Double
                argsClass[i] = double.class;
            } else {
                argsClass[i] = args[i].getClass();
            }
        }

        Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * 调用所有的函数, 包括父类的所有函数
     *
     * @param owner
     *         对象
     * @param methodName
     *         方法名
     * @param args
     *         参数
     *
     * @return 方法返回值
     *
     * @throws Exception
     */
    public static Object invokeMethodAll(Object owner, String methodName, Object... args) throws Exception {

        Class<?> ownerClass = owner.getClass();

        Class<?>[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            if (args[i].getClass() == Integer.class) {          // 一般的函数都是 int 而不是 Integer
                argsClass[i] = int.class;
            } else if (args[i].getClass() == Float.class) {     // 一般的函数都是 float 而不是 Float
                argsClass[i] = float.class;
            } else if (args[i].getClass() == Double.class) {    // 一般的函数都是 double 而不是 Double
                argsClass[i] = double.class;
            } else {
                argsClass[i] = args[i].getClass();
            }
        }
        Method method = null;

        for (Class<?> clazz = ownerClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, argsClass);
                return method;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * 执行某类的静态方法
     *
     * @param className
     *         类名
     * @param methodName
     *         方法名
     * @param args
     *         参数数组
     *
     * @return 执行方法返回的结果
     *
     * @throws Exception
     */
    public static Object invokeStaticMethod(String className, String methodName, Object... args) throws Exception {
        Class<?> ownerClass = Class.forName(className);

        Class<?>[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = ownerClass.getMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 新建实例
     *
     * @param className
     *         类名
     * @param args
     *         构造函数的参数 如果无构造参数，args 填写为 null
     *
     * @return 新建的实例
     */
    public static Object newInstance(String className, Object[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return newInstance(className, args, null);
    }

    /**
     * 新建实例
     *
     * @param className
     *         类名
     * @param args
     *         构造函数的参数 如果无构造参数，args 填写为 null
     *
     * @return 新建的实例
     */
    public static Object newInstance(String className, Object[] args, Class<?>[] argsType) throws NoSuchMethodException, SecurityException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> newOneClass = Class.forName(className);

        if (args == null) {
            return newOneClass.newInstance();
        } else {
            Constructor<?> cons;
            if (argsType == null) {
                Class<?>[] argsClass = new Class[args.length];

                for (int i = 0, j = args.length; i < j; i++) {
                    argsClass[i] = args[i].getClass();
                }

                cons = newOneClass.getConstructor(argsClass);
            } else {
                cons = newOneClass.getConstructor(argsType);
            }
            return cons.newInstance(args);
        }
    }

    /**
     * 是不是某个类的实例
     *
     * @param obj
     *         实例
     * @param cls
     *         类
     *
     * @return 如果 obj 是此类的实例，则返回 true
     */
    public static boolean isInstance(Object obj, Class<?> cls) {
        return cls.isInstance(obj);
    }

    /**
     * 得到数组中的某个元素
     *
     * @param array
     *         数组
     * @param index
     *         索引
     *
     * @return 返回指定数组对象中索引组件的值
     */
    public static Object getItemInArray(Object array, int index) {
        return Array.get(array, index);
    }

    /**
     * 获取包下的所有Class
     *
     * @param pPackage
     *         包名
     *
     * @return 方法返回值
     */
    public static Class<?> getClassListByPackage(String pPackage) {
        Package _Package = Package.getPackage(pPackage);
        return _Package.getClass();
    }
}