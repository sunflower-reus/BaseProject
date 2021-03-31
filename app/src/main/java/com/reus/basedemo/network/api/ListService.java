package com.reus.basedemo.network.api;

import com.reus.basedemo.modules.list.vm.FragListVM;
import com.views.network.entity.HttpResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by reus on 2021/3/5.
 * Server接口类.
 */
public interface ListService {

    /**
     * 获取列表数据.
     * @param pgNo
     * @param pgSize
     * @return
     */
    @GET("/api-infos/home/info/news")
    Observable<HttpResult<ArrayList<FragListVM>>> queryData(@Query("pgNo") int pgNo, @Query("pgSize") int pgSize);

}