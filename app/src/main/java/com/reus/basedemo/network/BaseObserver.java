package com.reus.basedemo.network;

import com.views.tools.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by gaoxl on 2021/3/5.
 * 观察者基类   请求返回的数据处理  成功时数据返回   失败时统一处理错误信息.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 请求失败
     * @param e
     */
    protected  void onFailure(Throwable e){
        //统一处理错误信息.
        //1.网络错误  2.服务器错误
        ToastUtil.toast("请求失败："+e.toString());
    }
}