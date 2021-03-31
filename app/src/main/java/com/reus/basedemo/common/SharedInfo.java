package com.reus.basedemo.common;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.views.tools.utils.ContextHolder;
import com.views.tools.utils.SPUtil;
import com.views.tools.utils.SoftHashMap;

/**
 * Description: 数据持久化
 * 在Application中调用 SharedInfo.init(...)方法
 */
@SuppressWarnings(value = {"unused", "unchecked"})
public class SharedInfo {
    private SoftHashMap<String, Object> moMap;
    private SharedPreferences sp;
    private static String fileName;

    private SharedInfo() {
        sp = SPUtil.getSp(ContextHolder.getContext(), fileName);
    }

    public static com.reus.basedemo.common.SharedInfo getInstance() {
        return SharedInfoInstance.instance;
    }

    private static class SharedInfoInstance {
        static com.reus.basedemo.common.SharedInfo instance = new com.reus.basedemo.common.SharedInfo();
    }

    public static void init(String fileName) {
        com.reus.basedemo.common.SharedInfo.fileName = fileName;
    }
    ///////////////////////////////////////////////////////////////////////////
    // get
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取数据
     */
    public <T> T getEntity(Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        String key = getKey(clazz);
        // moMap 中是否存在，存在则从 moMap 中取出
        if (getMoMap().containsKey(key)) {
            return (T) moMap.get(key);
        }
        // 不存在则从 SP 中取出
        T mo = SPUtil.getEntity(sp, clazz, null);
        // 不为空，则 put 进 moMap
        if (null != mo) {
            moMap.put(key, mo);
        }
        return mo;
    }

    public Object getValue(String key, Object defaultValue) {
        // moMap 中是否存在，存在则从 moMap 中取出
        if (getMoMap().containsKey(key)) {
            return moMap.get(key);
        }
        // 不存在则从 SP 中取出

        Object mo = SPUtil.getValue(sp, key, defaultValue);
        // 不为空，则 put 进 moMap
        if (null != mo) {
            moMap.put(key, mo);
        }
        return mo;
    }

    ///////////////////////////////////////////////////////////////////////////
    // save
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 保存数据
     */
    public void saveEntity(Object obj) {
        if (null == obj) {
            return;
        }
        // 每次setValue 替换最新的Object
        getMoMap().put(getKey(obj.getClass()), obj);
        SPUtil.saveEntity(sp, obj);
    }

    public void saveValue(String key, Object value) {
        // 每次setValue 替换最新的Object
        getMoMap().put(key, value);
        SPUtil.saveValue(sp, key, value);
    }

    /**
     * 从mo中删除此键值对
     */
    public void remove(Class<?> clazz) {
        if (null == clazz) {
            return;
        }
        String key = getKey(clazz);
        getMoMap().remove(key);
        SPUtil.remove(sp, key);
    }

    /**
     * 从mo中删除此键值对
     */
    public void remove(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        getMoMap().remove(key);
        SPUtil.remove(sp, key);
    }

    private String getKey(Class<?> clazz) {
        return clazz.getName();
    }

    private SoftHashMap<String, Object> getMoMap() {
        if (moMap == null)
            moMap = new SoftHashMap<>();
        return moMap;
    }
}