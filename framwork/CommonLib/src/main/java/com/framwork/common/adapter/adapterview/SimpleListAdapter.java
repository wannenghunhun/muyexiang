package com.framwork.common.adapter.adapterview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressWarnings({"unused", "WeakerAccess",})
public abstract class SimpleListAdapter<T> extends ArrayListAdapter<T> {
    
    private int layoutId;
    
    public SimpleListAdapter(int layoutId) {
        this(new ArrayList<>(), layoutId);
    }
    
    public SimpleListAdapter(List<T> mData, int layoutId) {
        super(mData);
        this.layoutId = layoutId;
    }
    
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        SimpleViewHolder holder = SimpleViewHolder.getViewHolder(convertView, parent, layoutId);
        fillData(getItem(position), holder, position);
        return holder.getConvertView();
    }
    
    protected abstract void fillData(T item, SimpleViewHolder viewHolder, int position);
}
