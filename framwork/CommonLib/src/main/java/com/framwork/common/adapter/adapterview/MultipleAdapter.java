package com.framwork.common.adapter.adapterview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressWarnings({"unchecked",
        "unused",
        "WeakerAccess",
        "SameParameterValue",
        "UnusedReturnValue"
})
public class MultipleAdapter<T> extends ArrayListAdapter<T> {
    
    private IDelegateManager delegateManager;
    
    protected IDelegateManager createManager() {
        return new DefaultListDelegateManager();
    }
    
    
    public MultipleAdapter() {
        super();
        delegateManager = createManager();
    }
    
    public MultipleAdapter(List<T> mData) {
        super(mData);
        delegateManager = createManager();
    }
    
    public MultipleAdapter<T> addTypeDelegateItem(IDelegateItem<T>[] items) {
        delegateManager.addTypeDelegateItem(items);
        return this;
    }
    
    public MultipleAdapter<T> addTypeDelegateItem(IDelegateItem<T> item) {
        delegateManager.addTypeDelegateItem(item);
        return this;
    }
    
    
    @Override
    public int getItemViewType(int position) {
        return delegateManager.getItemViewType(position, getData());
    }
    
    @Override
    public int getViewTypeCount() {
        return delegateManager.getTypeCount();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return delegateManager.getView(position, convertView, parent, getData());
    }
    
    
    public static abstract class SimpleDelegateItem<T> implements IDelegateItem<T> {
        
        private int layoutId;
        
        public SimpleDelegateItem(int layoutId) {
            this.layoutId = layoutId;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent, List<T> data) {
            SimpleViewHolder viewHolder = SimpleViewHolder.getViewHolder(convertView, parent, layoutId);
            fillData(data.get(position), viewHolder, position, data);
            return viewHolder.getConvertView();
        }
        
        protected abstract void fillData(T item, SimpleViewHolder viewHolder, int position, List<T> data);
    }
    
    public static class DefaultListDelegateManager<T> implements IDelegateManager<T> {
        
        private ArrayList<IDelegateItem<T>> items = new ArrayList<>();
        
        @Override
        public IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T>[] items) {
            for(IDelegateItem<T> item : items) {
                addTypeDelegateItem(item);
            }
            return this;
        }
        
        @Override
        public IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T> item) {
            items.add(item);
            return this;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent, List<T> data) {
            for(IDelegateItem item : items) {
                if(item.isForType(data.get(position), position, data)) {
                    return item.getView(position, convertView, parent, data);
                }
            }
            throw new IllegalArgumentException();
        }
        
        @Override
        public int getTypeCount() {
            if(items == null || items.size() <= 0) {
                return 1;
            }
            return items.size();
        }
        
        
        @Override
        public int getItemViewType(int position, List<T> data) {
            int count = items.size();
            for(int i = 0; i < count; i++) {
                IDelegateItem item = items.get(i);
                if(item.isForType(data.get(position), position, data)) {
                    return i;
                }
            }
            throw new IllegalArgumentException();
        }
    }
    
    
    public interface IDelegateItem<T> extends IMultipleType<T> {
        
        
        boolean isForType(T item, int position, List<T> data);
        
    }
    
    
    public interface IDelegateManager<T> extends IMultipleType<T> {
        
        
        int getTypeCount();
        
        
        int getItemViewType(int position, List<T> data);
        
        
        IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T>... items);
        
        IDelegateManager<T> addTypeDelegateItem(IDelegateItem<T> item);
        
    }
    
    public interface IMultipleType<T> {
        View getView(int position, View convertView, ViewGroup parent, List<T> data);
    }
    
}
