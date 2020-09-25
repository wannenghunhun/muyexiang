package com.framwork.common.widget.excpetion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.framwork.common.widget.excpetion.base.IExceptionLoadCovers;
import com.framwork.common.widget.excpetion.base.ILoadCovers;
import com.framwork.common.widget.excpetion.base.ILoadEmpty;
import com.framwork.common.widget.excpetion.base.ILoadFailure;
import com.framwork.common.widget.excpetion.base.INetException;


/**
 *
 * 错误页展示布局
 */
public abstract class BaseExceptionLayout extends FrameLayout implements ILoadEmpty, ILoadFailure, INetException, IExceptionLoadCovers {


    protected Context context;
    protected ILoadEmpty loadEmpty;
    protected ILoadFailure loadFailure;
    protected INetException netException;
    protected ILoadCovers loadCovers;

    protected OnClickListener emptyClickListener;
    protected OnClickListener failureClickListener;
    protected OnClickListener netExceptionClickListener;

    public BaseExceptionLayout(@NonNull Context context) {
        this(context, null);
    }

    public BaseExceptionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseExceptionLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    protected abstract ILoadCovers createCoversView();

    protected abstract ILoadEmpty createEmptyView();

    protected abstract ILoadFailure createFailureView();

    protected abstract INetException createNetExceptionView();

    private void addEmptyView() {
        if (loadEmpty != null) {
            return;
        }
        loadEmpty = createEmptyView();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(loadEmpty.getEmptyView(), params);
        if (emptyClickListener != null) {
            loadEmpty.setLoadEmptyOnClickListener(emptyClickListener);
        }
    }


    private void addLoadFailureView() {
        if (loadFailure != null) {
            return;
        }
        loadFailure = createFailureView();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(loadFailure.getLoadFailureView(), params);
        if (failureClickListener != null) {
            loadFailure.setLoadFailureOnClickListener(failureClickListener);
        }
    }


    private void addNetExceptionView() {
        if (netException != null) {
            return;
        }
        netException = createNetExceptionView();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(netException.getNetExceptionView(), params);
        if (netExceptionClickListener != null) {
            netException.setNetExceptionOnClickListener(netExceptionClickListener);
        }
    }


    @Override
    public final View getEmptyView() {
        return loadEmpty.getEmptyView();
    }

    @Override
    public final void showEmpty(String tv) {
        showEmptyView();
        loadEmpty.showEmpty(tv);
    }

    @Override
    public final void showEmpty(String tv, String btn) {
        showEmptyView();
        loadEmpty.showEmpty(tv, btn);
    }


    @Override
    public final View getLoadFailureView() {
        return loadFailure.getLoadFailureView();
    }


    @Override
    public final void showLoadFailure(String tv, String btn) {
        showLoadFailureView();
        loadFailure.showLoadFailure(tv, btn);
    }

    @Override
    public final View getNetExceptionView() {
        return netException.getNetExceptionView();
    }

    @Override
    public final void showNetException(String tv, String btn) {
        showNetExceptionView();
        netException.showNetException(tv, btn);
    }


    @Override
    public final void setLoadEmptyOnClickListener(OnClickListener onClickListener) {
        emptyClickListener = onClickListener;
        if (loadEmpty == null) {
            return;
        }
        loadEmpty.setLoadEmptyOnClickListener(onClickListener);
    }

    @Override
    public final void setLoadFailureOnClickListener(OnClickListener onClickListener) {
        failureClickListener = onClickListener;
        if (loadFailure == null) {
            return;
        }
        loadFailure.setLoadFailureOnClickListener(onClickListener);
    }

    @Override
    public final void setNetExceptionOnClickListener(OnClickListener onClickListener) {
        netExceptionClickListener = onClickListener;
        if (netException == null) {
            return;
        }
        netException.setNetExceptionOnClickListener(onClickListener);
    }

    @Override
    public final void hideEmptyView() {
        if (loadEmpty != null) {
            loadEmpty.hideEmptyView();
        }
    }

    @Override
    public final void showEmptyView() {
        addEmptyView();
        hideNetExceptionView();
        hideLoadFailureView();
        hideCovers();
        setVisibility(VISIBLE);
    }

    @Override
    public final void hideLoadFailureView() {
        if (loadFailure != null) {
            loadFailure.hideLoadFailureView();
        }
    }

    @Override
    public final void showLoadFailureView() {
        addLoadFailureView();
        hideEmptyView();
        hideNetExceptionView();
        hideCovers();
        setVisibility(VISIBLE);
    }

    @Override
    public final void hideNetExceptionView() {
        if (netException != null) {
            netException.hideNetExceptionView();
        }
    }

    @Override
    public final void showNetExceptionView() {
        addNetExceptionView();
        hideEmptyView();
        hideLoadFailureView();
        hideCovers();
        setVisibility(VISIBLE);
    }


    @Override
    public final void showLoadCovers(int leftMargin, int topMargin, int rightMargin, int bottomMargin, int color) {
        if (loadCovers == null) {
            loadCovers = createCoversView();
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            addView(loadCovers.getCoversView(), params);
        } else {
            LayoutParams params = (LayoutParams) loadCovers.getCoversView().getLayoutParams();
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            loadCovers.getCoversView().setLayoutParams(params);
        }
        loadCovers.showLoadCovers(color);
        hideLoadFailureView();
        hideNetExceptionView();
        hideEmptyView();
        setVisibility(VISIBLE);
    }

    @Override
    public final View getCoversView() {
        return loadCovers.getCoversView();
    }

    @Override
    public final void hideCovers() {
        if (loadCovers != null) {
            loadCovers.hideCovers();
            // 移除掉背景遮罩，一般只会使用一次，可以减少内存
            removeView(loadCovers.getCoversView());
            loadCovers = null;
        }
    }
}
