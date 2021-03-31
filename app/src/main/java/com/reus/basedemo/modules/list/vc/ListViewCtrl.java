package com.reus.basedemo.modules.list.vc;

import com.reus.basedemo.databinding.ActivityListBinding;
import com.reus.basedemo.modules.mine.view.ListAct;
import com.reus.basedemo.modules.list.vm.ListVM;

/**
 * Created by gaoxl on 2021/3/3.
 */
public class ListViewCtrl {

    private ListAct act;
    private ActivityListBinding mainBinding;
    public ListVM listVM;

    public ListViewCtrl(ListAct act, ActivityListBinding mainBinding) {
        this.act=act;
        this.mainBinding=mainBinding;

        listVM=new ListVM();
        dataInit();
    }

    /**
     * 初始化数据.
     */
    private void dataInit() {
        //网络请求.
//        Call<HttpResult<ArrayList<ListVM>>> call = HBClient.getService(MainService.class).queryData(1,10);
//        call.enqueue(new RequestCallBack<HttpResult<ArrayList<ListVM>>>() {
//            @Override
//            public void onSuccess(Call<HttpResult<ArrayList<ListVM>>> call, Response<HttpResult<ArrayList<ListVM>>> response) {
//                ArrayList<ListVM> rec = response.body().getObj();
//
//                Log.e("print","rec是否为空："+(rec.size()));
//
//                listVM.setTitle(rec.get(0).getTitle());
//            }
//        });

    }
}
