package com.reus.basedemo.modules.list.vm;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.reus.basedemo.BR;
import com.reus.basedemo.common.BaseParams;

/**
 * Created by gaoxl on 2021/3/5.
 */
public class FragListVM extends BaseObservable {
    private String title;
    private int id;
    private String mediaUrl;

    @BindingAdapter({"mediaUrl"})
    public static void loadImage(ImageView imageView,String imgUrl){
        Glide.with(imageView.getContext()).load(BaseParams.URI+imgUrl).into(imageView);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        notifyPropertyChanged(BR.mediaUrl);
    }
}