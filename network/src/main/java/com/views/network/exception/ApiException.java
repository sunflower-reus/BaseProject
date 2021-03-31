package com.views.network.exception;

import com.views.network.entity.HttpResult;

/**
 * Description: HTTP请求异常类   OKHttp返回拦截器  对错误进行处理
 */
@SuppressWarnings("unused")
public class ApiException extends RuntimeException {
    private HttpResult result;

    public ApiException(HttpResult result) {
        this.result = result;
    }

    public HttpResult getResult() {
        return result;
    }

    public void setResult(HttpResult result) {
        this.result = result;
    }
}