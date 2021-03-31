package com.reus.basedemo.common;

/**
 * Description: 登录信息.
 */
public class OauthTokenMo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * token
     */
    private String oauthToken;

    /**
     * 登录错误次数
     */
    private int errorTimes;

    public int getErrorTimes() {
        return errorTimes;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public String getUserId() {
        return userId;
    }
}