package com.views.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Description: 单位转换类
 */
@SuppressWarnings("unused")
public class DensityUtil {
    /** dp转px */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /** sp转px */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /** px转dp */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /** px转sp */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     */
    public static float getWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static float getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 窗口的高度
        return dm.heightPixels;
    }
}