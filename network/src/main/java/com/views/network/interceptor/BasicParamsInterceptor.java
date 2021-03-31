package com.views.network.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Description: 公共参数拦截器
 */
@SuppressWarnings("unused")
public class BasicParamsInterceptor implements Interceptor {
    /** headerLines 参数List */
    private List<String>        headerLinesList = new ArrayList<>();
    /** header 参数Map */
    private Map<String, String> headerParamsMap = new HashMap<>();
    /** url 参数Map */
    private Map<String, String> urlParamsMap    = new HashMap<>();
    /** body 参数Map */
    private Map<String, String> bodyParamsMap   = new HashMap<>();
    /** 加入动态参数 */
    private IBasicDynamic iBasicDynamic;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request         request        = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        ///////////////////////////////////////////////////////////////////////////
        // process header params inject
        ///////////////////////////////////////////////////////////////////////////
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerLinesList.size() > 0) {
            for (String line : headerLinesList) {
                headerBuilder.add(line);
            }
        }
        if (headerParamsMap.size() > 0) {
            for (Map.Entry entry : headerParamsMap.entrySet()) {
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        requestBuilder.headers(headerBuilder.build());

        ///////////////////////////////////////////////////////////////////////////
        // process url params inject whatever it's GET or POST
        ///////////////////////////////////////////////////////////////////////////
        if (urlParamsMap.size() > 0) {
            injectParamsIntoUrl(request, requestBuilder, urlParamsMap);
        }

        ///////////////////////////////////////////////////////////////////////////
        // process body params inject
        ///////////////////////////////////////////////////////////////////////////
        if (request.method().equals("POST")) {
            if (null != request.body() && request.body() instanceof MultipartBody) {
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                // add new params to new multipartBodyBuilder
                if (bodyParamsMap.size() > 0) {
                    for (Map.Entry entry : bodyParamsMap.entrySet()) {
                        multipartBodyBuilder.addFormDataPart((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                // add old parts to new multipartBodyBuilder
                for (MultipartBody.Part part : ((MultipartBody) request.body()).parts()) {
                    multipartBodyBuilder.addPart(part);
                }
                requestBuilder.post(multipartBodyBuilder.build());
            } else {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                // add new params to new formBodyBuilder
                if (bodyParamsMap.size() > 0) {
                    for (Map.Entry entry : bodyParamsMap.entrySet()) {
                        formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                // add old params to new formBodyBuilder
                FormBody formBody       = formBodyBuilder.build();
                String   postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                if (null != iBasicDynamic) {
                    postBodyString = iBasicDynamic.signParams(postBodyString);
                }
                requestBuilder.post(RequestBody.create(formBody.contentType(), postBodyString));
            }
        } else {
            // if can't inject into body, then inject into url
            injectParamsIntoUrl(request, requestBuilder, bodyParamsMap);
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            for (Map.Entry entry : paramsMap.entrySet()) {
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        requestBuilder.url(httpUrlBuilder.build());
    }

    // RequestBody to String
    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (IOException e) {
            return "did not work";
        }
    }

    public void setIBasicDynamic(IBasicDynamic iBasicDynamic) {
        this.iBasicDynamic = iBasicDynamic;
    }

    public static class Builder {
        BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.headerLinesList.add(headerLine);
            return this;
        }

        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine : headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
            }
            return this;
        }
        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }
        public Builder addUrlParam(String key, String value) {
            interceptor.urlParamsMap.put(key, value);
            return this;
        }
        public Builder addUrlParamsMap(Map<String, String> urlParamsMap) {
            interceptor.urlParamsMap.putAll(urlParamsMap);
            return this;
        }
        public Builder addBodyParam(String key, String value) {
            interceptor.bodyParamsMap.put(key, value);
            return this;
        }
        public Builder addBodyParamsMap(Map<String, String> bodyParamsMap) {
            interceptor.bodyParamsMap.putAll(bodyParamsMap);
            return this;
        }
        public BasicParamsInterceptor build() {
            return interceptor;
        }
    }
}