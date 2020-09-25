package com.framwork.common.adapter.adapterview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framwork.common.adapter.IViewHolder;


/**
 */
@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public final class SimpleViewHolder implements IViewHolder<SimpleViewHolder> {


    private SparseArrayCompat<View> views = new SparseArrayCompat<>();

    private View convertView;

    public static SimpleViewHolder getViewHolder(View convertView, ViewGroup parent, int layoutId) {
        SimpleViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new SimpleViewHolder();
            Context context = parent.getContext();
            View itemView = View.inflate(context, layoutId, null);
            viewHolder.putConvertView(itemView);
            itemView.setTag(viewHolder);

        } else {
            viewHolder = (SimpleViewHolder) convertView.getTag();
        }

        return viewHolder;
    }

    @Override
    public final View getConvertView() {
        return convertView;
    }

    private void putConvertView(View convertView) {
        this.convertView = convertView;
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
        T tView = (T) convertView.findViewById(viewId);
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
    public SimpleViewHolder setText(int viewId, String value) {
        TextView textView = findViewById(viewId);
        textView.setText(value);
        return this;
    }
}
