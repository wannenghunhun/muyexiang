package com.framwork.common.adapter.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
@SuppressWarnings({"unchecked",
        "unused",
        "WeakerAccess",
        "SameParameterValue",
        "UnusedReturnValue"
})
public class MultipleRecycleAdapter<T> extends RecycleListAdapter<T, RecyclerView.ViewHolder> {
    
    
    public MultipleRecycleAdapter() {
        this(Collections.emptyList());
    }
    
    public MultipleRecycleAdapter(List<T> mData) {
        super(mData);
        delegateManager = createManager();
    }
    
    private IDelegateManager<T> delegateManager;
    
    protected IDelegateManager<T> createManager() {
        return new DefaultDelegateManager<>();
    }
    
    
    public final <V extends RecyclerView.ViewHolder> MultipleRecycleAdapter<T> addTypeDelegateItem(IDelegateItem<T, V>[] items) {
        delegateManager.addTypeDelegateItem(items);
        return this;
    }
    
    
    public final <V extends RecyclerView.ViewHolder> MultipleRecycleAdapter<T> addTypeDelegateItem(IDelegateItem<T, V> item) {
        delegateManager.addTypeDelegateItem(item);
        return this;
    }
    
    
    @Override
    public int getItemViewType(int position) {
        return delegateManager.getItemViewType(mData, position);
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateManager.onCreateViewHolder(parent, viewType);
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegateManager.onBindViewHolder(holder, mData, position);
    }
    
    public static abstract class SimpleDelegateItem<T> implements IDelegateItem<T, SimpleRecycleViewHolder> {
        
        
        private int viewType;
        private int layoutId;
        
        public SimpleDelegateItem(int viewType, int layoutId) {
            this.viewType = viewType;
            this.layoutId = layoutId;
        }
        
        @Override
        public abstract boolean isForType(List<T> datas, int position);
        
        @Override
        public int getItemViewType() {
            return viewType;
        }
        
        @Override
        public SimpleRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return SimpleRecycleViewHolder.getViewHolder(parent, layoutId);
        }
        
        @Override
        public abstract void onBindViewHolder(List<T> datas, SimpleRecycleViewHolder holder, int position);
    }
    
    
    public static class DefaultDelegateManager<T> implements IDelegateManager<T> {
        
        private List<IDelegateItem> items = new ArrayList<>();
        
        @Override
        public int getItemViewType(List<T> datas, int position) {
            for(IDelegateItem item : items) {
                if(item.isForType(datas, position)) {
                    return item.getItemViewType();
                }
            }
            
            return 0;
        }
        
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            for(IDelegateItem item : items) {
                if(item.getItemViewType() == viewType) {
                    return item.onCreateViewHolder(parent, viewType);
                }
            }
            return null;
        }
        
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, List<T> datas, int position) {
            for(IDelegateItem item : items) {
                if(item.isForType(datas, position)) {
                    item.onBindViewHolder(datas, holder, position);
                    break;
                }
            }
        }
        
        @Override
        public <V extends RecyclerView.ViewHolder> IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T, V>[] items) {
            for(IDelegateItem<T, V> item : items) {
                addTypeDelegateItem(item);
            }
            return this;
        }
        
        @Override
        public <V extends RecyclerView.ViewHolder> IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T, V> item) {
            items.add(item);
            return this;
        }
    }
    
    
    public interface IDelegateItem<T, K extends RecyclerView.ViewHolder> {
        
        
        boolean isForType(List<T> datas, int position);
        
        int getItemViewType();
        
        K onCreateViewHolder(ViewGroup parent, int viewType);
        
        void onBindViewHolder(List<T> datas, K holder, int position);
        
    }
    
    
    public interface IDelegateManager<T> {
        
        
        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
        
        void onBindViewHolder(RecyclerView.ViewHolder holder, List<T> datas, int position);
        
        
        int getItemViewType(List<T> datas, int position);
        
        <V extends RecyclerView.ViewHolder> IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T, V>... items);
        
        <V extends RecyclerView.ViewHolder> IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T, V> item);
    }
    
}
