package com.framwork.common.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.framwork.common.widget.excpetion.BaseExceptionLayout;
import com.framwork.common.widget.excpetion.base.ILoadCovers;
import com.framwork.common.widget.excpetion.base.ILoadEmpty;
import com.framwork.common.widget.excpetion.base.ILoadFailure;
import com.framwork.common.widget.excpetion.base.INetException;

/**
 * 错误页展示布局
 */
public class ExceptionLayout extends BaseExceptionLayout {


    public ExceptionLayout(@NonNull Context context) {
        this(context, null);
    }

    public ExceptionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExceptionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected ILoadCovers createCoversView() {
        return new LoadCoversView(context);
    }

    @Override
    protected ILoadEmpty createEmptyView() {
        return new LoadEmptyView(context);
    }

    @Override
    protected ILoadFailure createFailureView() {
        return new LoadFailureView(context);
    }

    @Override
    protected INetException createNetExceptionView() {
        return new LoadNetExceptionView(context);
    }


}
