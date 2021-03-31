package com.views.tools.reflect;

import java.util.HashMap;

/**
 */
@SuppressWarnings("unused")
public class RefWrapper {
    public final static  int                         CLASS_STRING  = 1;
    public final static  int                         CLASS_INT     = 2;
    public final static  int                         CLASS_DOUBLE  = 3;
    public final static  int                         CLASS_FLOAT   = 4;
    public final static  int                         CLASS_LONG    = 5;
    public final static  int                         CLASS_BYTE    = 6;
    public final static  int                         CLASS_BOOLEAN = 7;
    private final static HashMap<Class<?>, Class<?>> UN_WRAPPER    = new HashMap<>();
    private final static HashMap<Class<?>, Class<?>> WRAPPER       = new HashMap<>();
    private final static HashMap<Class<?>, Integer>  INT_WRAPPER   = new HashMap<>();

    static {
        UN_WRAPPER.put(Boolean.class, boolean.class);
        UN_WRAPPER.put(Byte.class, byte.class);
        UN_WRAPPER.put(Character.class, char.class);
        UN_WRAPPER.put(Short.class, short.class);
        UN_WRAPPER.put(Integer.class, int.class);
        UN_WRAPPER.put(Long.class, long.class);
        UN_WRAPPER.put(Float.class, float.class);
        UN_WRAPPER.put(Double.class, double.class);
        UN_WRAPPER.put(Void.class, void.class);

        WRAPPER.put(boolean.class, Boolean.class);
        WRAPPER.put(byte.class, Byte.class);
        WRAPPER.put(char.class, Character.class);
        WRAPPER.put(short.class, Short.class);
        WRAPPER.put(int.class, Integer.class);
        WRAPPER.put(long.class, Long.class);
        WRAPPER.put(float.class, Float.class);
        WRAPPER.put(double.class, Double.class);
        WRAPPER.put(void.class, Void.class);
        WRAPPER.put(String.class, String.class);

        INT_WRAPPER.put(String.class, CLASS_STRING);
        INT_WRAPPER.put(Integer.class, CLASS_INT);
        INT_WRAPPER.put(Double.class, CLASS_DOUBLE);
        INT_WRAPPER.put(Float.class, CLASS_FLOAT);
        INT_WRAPPER.put(Long.class, CLASS_LONG);
        INT_WRAPPER.put(Byte.class, CLASS_BYTE);
        INT_WRAPPER.put(Boolean.class, CLASS_BOOLEAN);
    }

    public static Class<?> wrapClass(Class<?> clazz) {
        final Class<?> clazzWrapped = WRAPPER.get(clazz);
        return clazzWrapped == null ? clazz : clazzWrapped;
    }

    public static Class<?> unwrapClass(Class<?> clazz) {
        final Class<?> clazzUnwrapped = UN_WRAPPER.get(clazz);
        return clazzUnwrapped == null ? clazz : clazzUnwrapped;
    }

    public static boolean isPrimitive(Object obj) {
        return obj != null && isPrimitiveClass(obj.getClass());
    }

    public static boolean isPrimitiveClass(Class<?> clazz) {
        return UN_WRAPPER.containsKey(clazz);
    }

    public static boolean isPrimitiveWrapperClass(Class<?> clazz) {
        return WRAPPER.containsKey(clazz);
    }

    public static int intWrapClass(Class<?> clazz) {
        return INT_WRAPPER.containsKey(clazz) ? INT_WRAPPER.get(clazz) : 0;
    }
}