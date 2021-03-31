package com.views.network.utils;

import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description: 文件上传工具类
 */
@SuppressWarnings("unused")
public class FileUploadUtil {
    /**
     * @param map
     *         Key - 参数名
     *         Value - 参数值
     */
    public static Map<String, RequestBody> getRequestMap(Map<String, File> map) {
        Map<String, RequestBody> params = new HashMap<>();
        for (Map.Entry entry : map.entrySet()) {
            if (TextUtils.isEmpty((String) entry.getKey()) || null == entry.getValue()) {
                continue;
            }
            if (entry.getValue() instanceof File) {
                File file = (File) entry.getValue();
                params.put(entry.getKey() + "\"; filename=\"" + file.getName() + "", RequestBody.create(MultipartBody.FORM, file));
            } else {
                params.put((String) entry.getKey(), RequestBody.create(MultipartBody.FORM, (String) entry.getValue()));
            }
        }
        return params;
    }

    public static Map<String, RequestBody> getRequestMap(Object args) {
        Map<String, RequestBody> params = new HashMap<>();
        // 反射获取属性值
        Field fields[] = args.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            try {
                Object[] result = SerializedUtil.convertToRequestContent(args, field);
                if (null != result) {
                    String key   = result[0].toString();
                    Object value = result[1];
                    if (value instanceof File) {
                        File file = (File) value;
                        params.put(key + "\"; filename=\"" + file.getName() + "", RequestBody.create(MultipartBody.FORM, file));
                    } else {
                        params.put(key, RequestBody.create(MultipartBody.FORM, value.toString()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}