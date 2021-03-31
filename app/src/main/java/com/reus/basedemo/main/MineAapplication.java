package com.reus.basedemo.main;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.reus.basedemo.common.BaseParams;
import com.reus.basedemo.common.SharedInfo;

/**
 * Created by gaoxl on 2021/3/4.
 */
public class MineAapplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedInfo.init(BaseParams.SP_NAME);

        //初始化ARouter
        ARouter.init(this);
    }
}
