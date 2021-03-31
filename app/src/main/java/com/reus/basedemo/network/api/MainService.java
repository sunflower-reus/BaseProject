package com.reus.basedemo.network.api;

import com.views.network.entity.HttpResult;
import com.reus.basedemo.modules.list.vm.ListVM;
import com.views.tools.Observable;

import java.util.ArrayList;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description:
 */
public interface MainService {
    //获取列表数据
    @GET("/api-infos/home/info/news")
    Observable<HttpResult<ArrayList<ListVM>>> queryData(@Query("pgNo") int pgNo, @Query("pgSize") int pgSize);



}
