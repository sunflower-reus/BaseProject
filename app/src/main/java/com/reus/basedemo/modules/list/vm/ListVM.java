package com.reus.basedemo.modules.list.vm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.reus.basedemo.BR;

/**
 * Created by gaoxl on 2021/3/3.
 */
public class ListVM extends BaseObservable {

    private String title;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}