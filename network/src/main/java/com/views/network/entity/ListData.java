package com.views.network.entity;

import java.util.List;

/**
 * Description: 反序列化带page的List数据
 */
@SuppressWarnings("unused")
public class ListData<T> extends PageMo {
    /** list数据 */
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}