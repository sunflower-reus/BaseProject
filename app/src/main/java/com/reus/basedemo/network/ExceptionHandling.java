package com.reus.basedemo.network;

import com.views.network.entity.HttpResult;
import com.views.tools.utils.ToastUtil;

/**
 * Description: 异常处理.
 */
@SuppressWarnings("unchecked")
final class ExceptionHandling {
    //发生错误时的逻辑处理.
    static void operate(final HttpResult result) {
        switch (result.getCode()) {
            case "4001":
                ToastUtil.toast(result.getMsg());
                break;
            case "4002":
                ToastUtil.toast(result.getMsg());
                break;
            case "4003":
                ToastUtil.toast(result.getMsg());
                break;
            default:
                ToastUtil.toast(result.getMsg());
                break;
        }
    }
}