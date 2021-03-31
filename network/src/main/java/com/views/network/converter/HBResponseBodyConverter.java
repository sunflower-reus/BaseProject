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

import android.util.Log;

import com.views.network.entity.HttpResult;
import com.views.network.entity.Params;
import com.views.network.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Description:  JSON response 解析
 */
@SuppressWarnings("unused")
final class HBResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HBResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string().trim();
        value.close();

//        此处的response为请求获得到的的json字符串.
        Log.e("print","期待的Json:"+response);

        try {
            JSONObject object = new JSONObject(response);

            // 解析 resCode ,对不成功的返回做统一处理   6002 密码错误.  6003 账户被琐死.
            if (!object.getBoolean(Params.RES_SUCCESS) ) {
                throw new ApiException(new Gson().fromJson(response, HttpResult.class));
            }

            StringReader reader = new StringReader(response);
            JsonReader jsonReader = gson.newJsonReader(reader);
            return adapter.read(jsonReader);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}