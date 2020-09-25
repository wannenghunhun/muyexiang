package com.framwork.common.adapter.recycleview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framwork.common.adapter.IViewHolder;


/**
 */
@SuppressWarnings({"unchecked",
        "unused",
        "WeakerAccess",
})
public final class SimpleRecycleParentViewHolder extends RecyclerView.ViewHolder implements IViewHolder<SimpleRecycleParentViewHolder> {


    public static SimpleRecycleParentViewHolder getViewHolder(ViewGroup parent, int layoutId) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(layoutId, null, false);
        return new SimpleRecycleParentViewHolder(itemView);
    }

    public SimpleRecycleParentViewHolder(View itemView) {
        super(itemView);
    }


    private final SparseArrayCompat<View> views = new SparseArrayCompat<>();

    @Override
    public final View getConvertView() {
        return itemView;
    }


    @Override
    public final <T extends View> T findViewById(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            return putViewById(viewId);
        } else {
            return (T) views.get(viewId);
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public final <T extends View> T putViewById(@IdRes int viewId) {
        T tView = (T) itemView.findViewById(viewId);
        views.put(viewId, tView);

        return tView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param value
     * @return
     */
    @Override
    public SimpleRecycleParentViewHolder setText(int viewId, String value) {
        TextView textView = findViewById(viewId);
        textView.setText(value);
        return this;
    }
}
