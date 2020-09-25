package com.framwork.common.adapter.recycleview;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressWarnings({
        "unused",
        "WeakerAccess",
})
public abstract class SimpleRecycleListAdapter<T> extends RecycleListAdapter<T, SimpleRecycleViewHolder> {
    
    private int layoutId;
    
    
    public SimpleRecycleListAdapter(int layoutId) {
        this(layoutId, new ArrayList<>());
    }
    
    public SimpleRecycleListAdapter(int layoutId, List<T> mData) {
        super(mData);
        this.layoutId = layoutId;
    }
    
    @Override
    public final SimpleRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SimpleRecycleViewHolder.getViewHolder(parent, layoutId);
    }
    
    @Override
    public final void onBindViewHolder(SimpleRecycleViewHolder holder, int position) {
        fillData(holder, position, getItem(position), mData);
    }
    
    protected abstract void fillData(SimpleRecycleViewHolder holder, int position, T data, List<T> datas);
    
    
}
