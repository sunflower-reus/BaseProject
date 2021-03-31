package com.views.tools.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description: Toast工具类.
 */
@SuppressWarnings("unused")
public class ToastUtil {
    /**
     * 禁止重复提示.
     */
    private static final long Interval = 1 * 1000;
    /**
     * 消息软引用.
     */
    private static final SoftHashMap<String, Long> map = new SoftHashMap<>();
    /**
     * Toast 对象.
     */
    private static Toast toast;

    public static void toast(String msg) {
        toast(ContextHolder.getContext(), msg);
    }

    public static void toast(int id) {
        toast(ContextHolder.getContext(), ContextHolder.getContext().getString(id));
    }

    private static void toast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            long preTime = 0;
            if (map.containsKey(msg)) {
                preTime = map.get(msg);
            }
            final long now = System.currentTimeMillis();
            if (now >= preTime + Interval) {
                if (toast != null) {
                    toast.cancel();
                }
                if (context != null) {
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//设置显示位置.
                    toast.show();
                    map.put(msg, now);
                    ToastUtil.toast = toast;
                }
            }
        }
    }
}