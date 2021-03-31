package com.reus.basedemo.network;

import com.views.network.interceptor.BasicParamsInterceptor;
import com.views.network.interceptor.IBasicDynamic;

import okhttp3.Interceptor;

/**
 * Description: 拦截器 - 用于添加签名参数
 *
 * 个人理解：请求拦截器  用于设置请求头及每个接口都包含的字段  不用反复写.  OKHttp在每次请求前进行拦截 然后封装进去  在去进行请求.
 */
class BasicParamsInject {
    private BasicParamsInterceptor interceptor;

    BasicParamsInject() {
        // 设置静态参数
        interceptor = new BasicParamsInterceptor.Builder()
                //设置请求头  及共有参数.
//                .addHeaderParam()
//                .addBodyParam(Constant.APP_KEY, BaseParams.APP_KEY)
//                .addBodyParam(Constant.MOBILE_TYPE, BaseParams.MOBILE_TYPE)
//                .addBodyParam(Constant.VERSION_NUMBER, DeviceInfoUtils.getVersionName(ContextHolder.getContext()))
                .build();
        // 设置动态参数
        interceptor.setIBasicDynamic(new IBasicDynamic() {
            @Override
            public String signParams(String postBodyString) {
                //这行代码的意思是对字符串进行了签名处理.
//                return UrlUtils.getInstance().signParams(postBodyString);
                return postBodyString;
            }
        });
    }

    Interceptor getInterceptor() {
        return interceptor;
    }
}