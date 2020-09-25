package com.framwork.common.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framwork.common.R;
import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.ExceptionLayout;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.common.utils.LoadingUtil;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.ViewUtil;
import com.framwork.common.widget.excpetion.BaseExceptionLayout;

public abstract class BaseFragment<P extends BasePresenter> extends LifecycleFragment implements IBaseView {
    protected P presenter;
    protected View mContent;//具体内容页
    protected View rootView;// 根View
    protected FrameLayout rootContentView; // 具体内容页包裹View  异常View 父布局
    protected BaseExceptionLayout exceptionLayout;

    protected View.OnClickListener DefaultOnClickListener = v -> loadAgain();
    protected View.OnClickListener emptyOnClickListener = DefaultOnClickListener;  // 空白点击重试
    protected View.OnClickListener loadFailOnClickListener = DefaultOnClickListener; // 错误点击重试
    protected View.OnClickListener netExceptionOnClickListener = DefaultOnClickListener; // 网络异常点击重试
    protected int errorColorBg = ResUtil.getColor(R.color.white); // 错误页背景
    protected int errorTopMargin = 0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    @Override
    public final View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        rootView = createRootView();
        setUpViews(mContent);
        if (presenter == null) {
            presenter = createPresenter();
        }
        return rootView;
    }

    // fix 重新创建
    protected View createRootView() {
        if (rootView != null) {
            return rootView;
        }
        View rootView = ViewUtil.inflate(layoutRootViewId());
        initRootView(rootView);
        return rootView;
    }

    protected View initRootContentView(View rootView) {
        return ViewUtil.inflate(layoutContentId());
    }

    protected int layoutRootViewId() {
        return R.layout.fragment_root;
    }



    protected void initRootView(View rootView) {
        rootContentView = ViewUtil.findFLById(rootView, R.id.fl_root);
        mContent = initRootContentView(rootView);
        rootContentView.addView(mContent, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
    }


    protected void fixExceptionLayoutMargin() {
        createExceptionLayout(0, errorTopMargin, 0, 0);
    }

    protected void fixLoadCoverMargin() {
        createExceptionLayout(0, 0, 0, 0);
    }

    // 覆写该方法，创建自己的 ExceptionLayout
    protected BaseExceptionLayout createExceptionLayout() {
        return new ExceptionLayout(mActivity);
    }

    protected final void createExceptionLayout(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (exceptionLayout == null) {
            exceptionLayout = createExceptionLayout();
            exceptionLayout.setBackgroundColor(errorColorBg);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            rootContentView.addView(exceptionLayout, params);
        } else {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) exceptionLayout.getLayoutParams();
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            exceptionLayout.setLayoutParams(params);
        }
    }


    // 添加遮罩
    protected void showLoadCovers() {
        showLoadCovers(0, 0, 0, 0, Color.WHITE);
    }

    protected void showLoadCovers(int leftMargin, int topMargin, int rightMargin, int bottomMargin, @ColorInt int
            color) {
        fixLoadCoverMargin();
        exceptionLayout.showLoadCovers(leftMargin, topMargin, rightMargin, bottomMargin, color);
    }

    protected void setUpViews(View mContent) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView(getRetainInstance());
        }
        presenter = null;
    }

    protected abstract void initView(View view);

    protected abstract int layoutContentId();

    protected abstract P createPresenter();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 再次请求
     */
    protected abstract void loadAgain();


    // 显示有数据的View
    protected void showLoadDataView() {
        ViewUtil.setVisible(mContent);
        dissLoading();
    }

    // 显示错误的View
    protected void showExceptionView() {
        ViewUtil.setGone(mContent);
        fixExceptionLayoutMargin();
        dissLoading();
    }


    /**
     * 加载完成
     */
    @Override
    public void loadComplete() {
        if (exceptionLayout != null) {
            exceptionLayout.setVisibility(View.GONE);
        }
        showLoadDataView();
    }

    public void showLoading() {
        showLoading(false);
    }


    public void showLoading(boolean cancelable) {
        showLoading(ResUtil.getString(R.string.dialog_title), cancelable, R.style.confirm_dialog);
    }


    public void showLoading(String title, boolean cancelable, int style) {
        LoadingUtil.showLoading(this, title, cancelable, style);
    }

    @Override
    public void dissLoading() {
        LoadingUtil.dismissLoading(this);
    }

    /**
     * 加载无数据,不显示重试按钮
     */
    @Override
    public void showEmpty(String tv) {
        showExceptionView();
        exceptionLayout.showEmpty(tv);
    }

    /**
     * 加载无数据
     */
    public void showEmpty(String tv, String btn) {
        showExceptionView();
        exceptionLayout.showEmpty(tv, btn);
        exceptionLayout.setLoadEmptyOnClickListener(emptyOnClickListener);
    }

    /**
     * 加载网络异常
     */
    public void showNetException(String tv, String btn) {
        showExceptionView();
        exceptionLayout.showNetException(tv, btn);
        exceptionLayout.setNetExceptionOnClickListener(netExceptionOnClickListener);
    }

    /**
     * 加载失败
     */
    public void showFailure(String tv, String btn) {
        showExceptionView();
        exceptionLayout.showLoadFailure(tv, btn);
        exceptionLayout.setLoadFailureOnClickListener(loadFailOnClickListener);
    }

    /**
     * Find view by id v.
     *
     * @param <V> the type parameter
     * @param id  the id
     * @return the v
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(@IdRes int id) {
        return (V) mContent.findViewById(id);
    }

    /**
     * fragment可见的时候操作，且在可见状态切换到可见的时候调用，解决重复调用
     */
    protected void onVisible() {

    }

    /**
     * fragment不可见的时候操作调用
     */
    protected void onHidden() {
        dissLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAdded() && !isHidden()) {
            onVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible()) {
            onHidden();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onHidden();
        }
    }


}