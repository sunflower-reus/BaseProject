/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.views.network.converter;

import android.text.TextUtils;

import com.views.network.utils.SerializedUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Description: Object request 解析
 */
@SuppressWarnings("unused")
final class HBRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE   = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private static final Charset   CHARSET_NAME = Charset.forName("UTF-8");

    @Override
    public RequestBody convert(T args) throws IOException {
        StringBuilder builder = new StringBuilder();
        // 反射获取属性值
        Field fields[] = args.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            try {
                Object[] result = SerializedUtil.convertToRequestContent(args, field);
                if (null != result) {
                    String key   = result[0].toString();
                    String value = result[1].toString();
                    if (!TextUtils.isEmpty(value)) {
                        builder.append(key);
                        builder.append("=");
                        builder.append(URLEncoder.encode(value, "UTF-8"));
                        builder.append("&");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 删除最后一个 '&' 符号
        int    index = builder.length() - 1;
        byte[] bytes;
        if (index >= 0) {
            bytes = builder.deleteCharAt(index).toString().getBytes(CHARSET_NAME);
        } else {
            bytes = builder.toString().getBytes(CHARSET_NAME);
        }
        return RequestBody.create(MEDIA_TYPE, bytes);
    }
}