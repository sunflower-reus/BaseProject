package com.reus.basedemo.modules.list.vc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.reus.basedemo.R;
import com.reus.basedemo.databinding.FragmentListBinding;
import com.reus.basedemo.modules.list.adapter.ListFragmentAdapter;
import com.reus.basedemo.modules.list.vm.FragListVM;
import com.reus.basedemo.network.BaseObserver;
import com.reus.basedemo.network.HBClient;
import com.reus.basedemo.network.api.ListService;
import com.views.network.entity.HttpResult;

import java.util.ArrayList;

/**
 * Created by gaoxl on 2021/3/5.
 * 逻辑的具体实现类.
 */
public class ListFragViewCtrl {

    private Context context;
    private FragmentListBinding binding;
    public FragListVM fragListVM;

    public ListFragViewCtrl(Context context, FragmentListBinding binding) {
        this.context = context;
        this.binding = binding;

        fragListVM = new FragListVM();
        initData();
    }

    /**
     * 初始化数据.
     */
    @SuppressLint("CheckResult")
    private void initData() {
        binding.recycleview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

        //网络请求.
        HBClient.getService(ListService.class).queryData(1, 20)
                .compose(HBClient.applySchedulers(new BaseObserver<HttpResult<ArrayList<FragListVM>>>() {
                    @Override
                    protected void onSuccess(HttpResult<ArrayList<FragListVM>> arrayListHttpResult) {
                        Log.e("print", "期待的数据集大小：" + arrayListHttpResult.getObj().size());

//                        fragListVM.setTitle(arrayListHttpResult.getObj().get(1).getTitle());

                        ListFragmentAdapter adapter=new ListFragmentAdapter();
                        binding.recycleview.setAdapter(adapter);
                        adapter.setList(arrayListHttpResult.getObj());

                    }
                }));

    }
}