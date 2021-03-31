package com.reus.basedemo.modules.list.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reus.basedemo.R;
import com.reus.basedemo.modules.list.vm.FragListVM;
import com.views.network.entity.HttpResult;

import java.util.ArrayList;

/**
 * Created by gaoxl on 2021/3/8.
 */
public class MineAdapter extends RecyclerView.Adapter<MineAdapter.VH> {

    private Context context;
    private HttpResult<ArrayList<FragListVM>> data;

    public MineAdapter(Context context, HttpResult<ArrayList<FragListVM>> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.e("print","onCreateViewHolder()方法");

        VH vh=new VH(View.inflate(context, R.layout.recycleview_item,null));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        Log.e("print","onBindViewHolder()方法:"+position);

        holder.mTextView.setText(data.getObj().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.getObj().size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView mTextView;
        public VH(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_text);
        }
    }

}
