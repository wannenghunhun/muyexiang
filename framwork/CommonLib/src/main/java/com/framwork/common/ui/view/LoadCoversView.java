package com.framwork.common.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.framwork.common.widget.excpetion.base.ILoadCovers;


/**
 *
 */
public class LoadCoversView extends View implements ILoadCovers {


    public LoadCoversView(Context context) {
        super(context);
    }

    public LoadCoversView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadCoversView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public LoadCoversView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showLoadCovers(int color) {
        setBackgroundColor(color);
    }

    @Override
    public View getCoversView() {
        return this;
    }

    @Override
    public void hideCovers() {
        setVisibility(GONE);
    }
}

