package com.reus.basedemo.modules.mine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reus.basedemo.R;
import com.reus.basedemo.common.base.BaseFragment;
import com.reus.basedemo.databinding.FragmentListBinding;
import com.reus.basedemo.databinding.FragmentMineBinding;

/**
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMineBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, null, false);
        //        ctrl = new HomeCtrl(getContext(), binding);
        //
        //        binding.setViewCtrl(ctrl);
        //        return binding.getRoot();

        return binding.getRoot();


    }
}