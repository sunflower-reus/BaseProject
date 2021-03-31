package com.reus.basedemo.network;

import com.views.network.converter.HBConverterFactory;
import com.reus.basedemo.common.BaseParams;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Description: 网络请求client.
 */
public class HBClient {
    /**
     * 网络请求超时时间值(s).
     */
    private static final int DEFAULT_TIMEOUT = 30;
    /**
     * 请求地址.
     */
    private static final String BASE_URL = BaseParams.URI;
    /**
     * retrofit实例.
     */
    private final Retrofit retrofit;

    /**
     * 私有化构造方法.
     */
    private HBClient() {
        // 创建一个OkHttpClient.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置网络请求超时时间.
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 添加签名参数.    并设置请求拦截器.
        builder.addInterceptor(new BasicParamsInject().getInterceptor());
        // 打印参数.   这里没有判断是否为debug模式   只有在debug模式下才去打印请求日志   才是正常逻辑.
//        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        // 失败后尝试重新请求.
        builder.retryOnConnectionFailure(true);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(HBConverterFactory.create())
                //将Retrofit返回回来的Call转换成Observable(被观察者)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 调用单例对象
     */
    private static com.reus.basedemo.network.HBClient getInstance() {
        return RDClientInstance.instance;
    }

    /**
     * 创建单例对象
     */
    private static class RDClientInstance {
        static com.reus.basedemo.network.HBClient instance = new com.reus.basedemo.network.HBClient();
    }

    /**
     * service
     */
    private static TreeMap<String, Object> serviceMap;

    private static TreeMap<String, Object> getServiceMap() {
        if (serviceMap == null)
            serviceMap = new TreeMap<>();
        return serviceMap;
    }

    /**
     * @return 指定service实例.
     */
    public static <T> T getService(Class<T> clazz) {
        if (getServiceMap().containsKey(clazz.getSimpleName())) {
            return (T) getServiceMap().get(clazz.getSimpleName());
        }

        T service = com.reus.basedemo.network.HBClient.getInstance().retrofit.create(clazz);
        getServiceMap().put(clazz.getSimpleName(), service);
        return service;
    }

    /**
     * 线程转换器.
     *
     * @param observer
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        //封装使用   不用每次都去手动切换  只需调用方法即可.
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                //切换线程  在Observable(被观察者)----流向---->Obsever(观察者)之间切换.
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                //设置订阅的"人",也就是Obsever(观察者).
                observable.subscribe(observer);
                return observable;
            }
        };
    }

}