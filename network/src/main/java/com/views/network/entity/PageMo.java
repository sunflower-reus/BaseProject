package com.views.network.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Description: 页数信息
 */
@SuppressWarnings("unused")
public class PageMo implements Serializable {
    /** 总页数 */
    @SerializedName(value = "totalPage", alternate = {"totalPage"})
    private int totalPage;
    /** 当前页数 - 默认第一页 */
    @SerializedName(value = "current", alternate = {"current"})
    private int page     = 1;
    /** 每次加载数量 - 默认10条 */
    @SerializedName(value = "pageSize", alternate = {"pageSize"})
    private int pageSize = 10;
    /** 总共条数 */
    @SerializedName(value = "total", alternate = {"total"})
    private int total;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 是否是刷新
     */
    public boolean isRefresh() {
        return page == 1;
    }

    /**
     * 下拉刷新
     */
    public void refresh() {
        page = 1;
    }

    /**
     * 上拉加载
     */
    public void loadMore() {
        page++;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    /**
     * @return 是否已经显示完全
     * true - 已经是最后页，无需再次请求，loadMore 无需再显示
     * false - 还不是最后页，需要再次请求数据，loadMore 需要再显示
     */
    public boolean isOver() {
        return totalPage <= page;
    }
}