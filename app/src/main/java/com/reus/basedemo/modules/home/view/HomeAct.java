package com.reus.basedemo.modules.home.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.reus.basedemo.R;
import com.reus.basedemo.common.base.BaseActivity;

/**
 * Created by gaoxl on 2021/3/16.
 */
@Route(path="/basedemo/modules/home/view/homeact")
public class HomeAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

    }
}
