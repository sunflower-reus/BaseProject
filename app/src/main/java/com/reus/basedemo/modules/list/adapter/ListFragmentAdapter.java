package com.reus.basedemo.modules.list.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.reus.basedemo.R;
import com.reus.basedemo.databinding.RecycleviewItemBinding;
import com.reus.basedemo.modules.list.vm.FragListVM;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gaoxl on 2021/3/8.
 */
public class ListFragmentAdapter extends BaseQuickAdapter<FragListVM, BaseDataBindingHolder<RecycleviewItemBinding>> {

    public ListFragmentAdapter() {
        super(R.layout.recycleview_item);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<RecycleviewItemBinding> viewHolder, FragListVM fragListVM) {
        RecycleviewItemBinding binding = viewHolder.getDataBinding();
        if (binding != null) {
            //设置各Item里控件数据.
            binding.setViewModel(fragListVM);
            binding.executePendingBindings();
        }
    }
}