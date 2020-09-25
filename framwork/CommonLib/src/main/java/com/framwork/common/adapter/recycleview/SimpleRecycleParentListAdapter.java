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
public abstract class SimpleRecycleParentListAdapter<T> extends RecycleListAdapter<T, SimpleRecycleParentViewHolder> {
    
    private int layoutId;
    
    
    public SimpleRecycleParentListAdapter(int layoutId) {
        this(layoutId, new ArrayList<>());
    }
    
    public SimpleRecycleParentListAdapter(int layoutId, List<T> mData) {
        super(mData);
        this.layoutId = layoutId;
    }
    
    @Override
    public final SimpleRecycleParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SimpleRecycleParentViewHolder.getViewHolder(parent, layoutId);
    }
    
    @Override
    public final void onBindViewHolder(SimpleRecycleParentViewHolder holder, int position) {
        fillData(holder, position, getItem(position), mData);
    }
    
    protected abstract void fillData(SimpleRecycleParentViewHolder holder, int position, T data, List<T> datas);
    
    
}
