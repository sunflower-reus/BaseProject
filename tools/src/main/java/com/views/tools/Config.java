package com.views.tools;

/**
 * Description: 配置信息
 */
@SuppressWarnings("unused")
public class Config {
    /** 项目根目录路径 */
    public static Observable<String>  ROOT_PATH = new Observable<>(null);
    /** 是否是debug模式 */
    public static Observable<Boolean> DEBUG     = new Observable<>(true);
}