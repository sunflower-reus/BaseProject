package com.reus.basedemo.modules.list.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reus.basedemo.R;
import com.reus.basedemo.common.base.BaseFragment;
import com.reus.basedemo.databinding.FragmentListBinding;
import com.reus.basedemo.modules.list.vc.ListFragViewCtrl;

/**
 * create an instance of this fragment.
 */
public class ListFragment extends BaseFragment {

    private FragmentListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, null, false);
        ListFragViewCtrl listFragViewCtrl = new ListFragViewCtrl(getContext(), binding);
        binding.setFraglistVC(listFragViewCtrl);
        return binding.getRoot();
    }
}