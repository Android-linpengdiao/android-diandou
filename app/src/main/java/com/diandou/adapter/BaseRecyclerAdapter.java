package com.diandou.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<T> mList = new ArrayList<>();

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void refreshData(List<T> list) {
        if (null != list) {
            this.mList = list;
            notifyDataSetChanged();
        }
    }

    public void loadMoreData(List<T> list) {
        if (null != list) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(viewType), parent, false);
        return new RecyclerHolder(bing.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        final T t = mList.get(position);
        onBindItem(binding, t, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, T t, int position);

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        public RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

}