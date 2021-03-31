package com.reus.basedemo.modules.home.vc;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.reus.basedemo.databinding.FragmentHomeBinding;

/**
 * Created by gaoxl on 2021/3/4.
 */
public class HomeCtrl {

    private Context mContext;
    private FragmentHomeBinding homeBinding;

    public HomeCtrl(Context context, FragmentHomeBinding binding) {
        mContext=context;
        homeBinding=binding;

        initData();
    }

    /**
     * 初始化数据.
     */
    private void initData() {
        homeBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/basedemo/modules/home/view/homeact").navigation();
            }
        });
    }
}
