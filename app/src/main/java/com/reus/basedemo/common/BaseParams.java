package com.reus.basedemo.common;

import android.os.Environment;

import java.io.File;

/**
 * Description:  基础参数配置.
 */
public class BaseParams {


    /**
     * 请求地址
     */
    public static final String URI = "http://taichi.chint.com";

    /**
     * SP文件名.
     */
    public static final String SP_NAME = "basic_params";

    /**
     * 获取SD卡的根目录.
     */
    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在.
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {//sd卡已存在时.
            // 获取跟目录.
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }
}