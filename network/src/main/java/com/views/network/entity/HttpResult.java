package com.views.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Description: 网络返回消息，最外层解析实体
 */
@SuppressWarnings("unused")
public class HttpResult<T> {
    /**
     * 是否成功
     */
    @SerializedName(Params.RES_SUCCESS)
    private boolean success;
    /**
     * 错误信息
     */
    @SerializedName(Params.RES_MSG)
    private String msg;
    /**
     * 错误Code
     */
    @SerializedName(Params.RES_CODE)
    private String code;
    /**
     * 消息响应的主体
     */
    @SerializedName(Params.RES_OBJ)
    private T obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}