package com.reus.basedemo.modules.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reus.basedemo.R;
import com.reus.basedemo.common.base.BaseFragment;
import com.reus.basedemo.databinding.FragmentHomeBinding;
import com.reus.basedemo.modules.home.vc.HomeCtrl;

/**
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {

    private HomeCtrl homeCtrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
        homeCtrl = new HomeCtrl(getContext(), binding);
        binding.setViewCtrl(homeCtrl);
        return binding.getRoot();
    }
}