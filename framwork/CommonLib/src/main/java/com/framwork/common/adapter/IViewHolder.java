package com.framwork.common.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.framwork.common.utils.ResUtil;

/**
 *
 */
public interface IViewHolder<T> {


    View getConvertView();


    <V extends View> V findViewById(int viewId);

    @SuppressWarnings("unchecked")
    <V extends View> V putViewById(@IdRes int viewId);


    T setText(@IdRes int viewId, String value);

    default T setText(@IdRes int viewId, @StringRes int value) {
        return setText(viewId, ResUtil.getString(value));
    }

}
