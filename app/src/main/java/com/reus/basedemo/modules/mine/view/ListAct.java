package com.reus.basedemo.modules.mine.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.reus.basedemo.R;
import com.reus.basedemo.databinding.ActivityListBinding;
import com.reus.basedemo.modules.list.vc.ListViewCtrl;

public class ListAct extends AppCompatActivity {

    private ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewCtrl(new ListViewCtrl(this,binding));
    }
}