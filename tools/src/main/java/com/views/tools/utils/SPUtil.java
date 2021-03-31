package com.views.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.views.tools.encryption.Base64;
import com.views.tools.encryption.DES3;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;

/**
 * Description: SharePreference 工具类
 */
@SuppressWarnings("unused")
public class SPUtil {
    /** 3des 加解密的key */
    private static String SECRET_KEY;

    /**
     * 获取SP对象
     *
     * @param context
     *         上下文
     * @param fileName
     *         SharePreference 的文件名
     */
    public static SharedPreferences getSp(Context context, String fileName) {
        if (null == context || TextUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("Context and fileName can not be null");
        }
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 保存到 SharePreference 中
     */
    public static boolean saveValue(SharedPreferences sp, String key, Object value) {
        if (null == sp) {
            return false;
        }
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            return editor.putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            return editor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            return editor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            return editor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            return editor.putLong(key, (Long) value).commit();
        } else if (value instanceof Set) {
            throw new IllegalArgumentException("Value can not be Set object!");
        }
        return false;
    }

    /**
     * 从 SharePreference 中取值
     */
    public static Object getValue(SharedPreferences sp, String key, Object defaultValue) {
        if (null == sp) {
            return null;
        }

        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            throw new IllegalArgumentException("Can not to get Set value!");
        }
        return null;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @return 是否存在
     */
    public static boolean contains(SharedPreferences sp, String key) {
        return null != sp && sp.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static boolean remove(SharedPreferences sp, String key) {
        if (null == sp) {
            return false;
        }
        Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @return 是否成功
     */
    public static boolean clear(SharedPreferences sp) {
        if (null == sp) {
            return false;
        }
        Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(SharedPreferences sp) {
        if (null == sp) {
            return null;
        }
        return sp.getAll();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Object 以 JSON 的形式存入SP
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param obj
     *         对象
     *
     * @return 是否保存成功
     */
    public static boolean saveEntity(SharedPreferences sp, final Object obj) {
        if (null != obj) {
            final String innerKey = getKey(obj.getClass());
            if (null != innerKey) {
                String value = obj2str(obj);
                if (TextUtils.isEmpty(value)) {
                    return false;
                }
                if (!TextUtils.isEmpty(SECRET_KEY)) {
                    try {
                        value = DES3.encrypt(SECRET_KEY, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                        value = Base64.encode(value.getBytes());
                    }
                } else {
                    value = Base64.encode(value.getBytes());
                }
                return saveValue(sp, innerKey, value);
            }
        }
        return false;
    }

    /**
     * @param clazz
     *         类型
     * @param defaultValue
     *         默认值
     *
     * @return T对象
     */
    public static <T> T getEntity(SharedPreferences sp, final Class<T> clazz, final T defaultValue) {
        final String innerKey = getKey(clazz);
        if (!TextUtils.isEmpty(innerKey)) {
            String value = (String) getValue(sp, innerKey, "");
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            if (!TextUtils.isEmpty(SECRET_KEY)) {
                try {
                    value = DES3.decrypt(SECRET_KEY, value);
                } catch (Exception e) {
                    e.printStackTrace();
                    value = new String(Base64.decode(value));
                }
            } else {
                value = new String(Base64.decode(value));
            }
            T ret = str2obj(value, clazz);
            if (null != ret) {
                return ret;
            }
        }
        return defaultValue;
    }

    /**
     * 类对应的key
     */
    private static String getKey(final Class<?> clazz) {
        if (null != clazz) {
            return clazz.getName();
        }
        return null;
    }

    /***
     * Object 到 String 的序列化
     */
    private static String obj2str(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String 到 Object 的反序列化
     */
    private static <T> T str2obj(final String string, final Class<T> clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}