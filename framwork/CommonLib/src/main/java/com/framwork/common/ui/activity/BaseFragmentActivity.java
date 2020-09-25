package com.framwork.common.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.framwork.common.R;
import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.ExceptionLayout;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.common.utils.LoadingUtil;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.ViewUtil;
import com.framwork.common.widget.TitleBar;
import com.framwork.common.widget.excpetion.BaseExceptionLayout;

public abstract class BaseFragmentActivity<P extends BasePresenter> extends LifecycleFragmentActivity implements
        IBaseView {
    protected P presenter;
    protected TitleBar mTitleBar;
    protected FrameLayout mContent;//具体内容页
    protected Context mActivity;
    protected BaseExceptionLayout exceptionLayout;
    protected View.OnClickListener DefaultOnClickListener = v -> loadAgain();
    protected View.OnClickListener emptyOnClickListener = DefaultOnClickListener;  // 空白点击重试
    protected View.OnClickListener loadFailOnClickListener = DefaultOnClickListener; // 错误点击重试
    protected View.OnClickListener netExceptionOnClickListener = DefaultOnClickListener; // 网络异常点击重试
    protected int errorColorBg = ResUtil.getColor(R.color.white); // 错误页背景
    protected int defaultErrorMargin = ResUtil.getDimen(R.dimen.title_height);  // 默认的偏移高度
    protected int errorTopMargin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutId());
//        if (StatusAndNavigationBarUtil.isNavigationBarShow(this)) {
//            StatusAndNavigationBarUtil.setNavigationBarColor(this, ResUtil.getColor(R.color.white));
//        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initData(bundle);
        }
        mActivity = this;
        initRootView();
        presenter = createPresenter();
        setUpView();
        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void fixExceptionLayoutMargin() {
        createExceptionLayout(0, errorTopMargin, 0, 0);
    }

    protected void fixLoadCoverMargin() {
        createExceptionLayout(0, 0, 0, 0);
    }

    // 覆写该方法，创建自己的 ExceptionLayout
    protected BaseExceptionLayout createExceptionLayout() {
        return new ExceptionLayout(this);
    }

    protected void createExceptionLayout(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (exceptionLayout == null) {
            exceptionLayout = createExceptionLayout();
            exceptionLayout.setBackgroundColor(errorColorBg);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
            FrameLayout content = (FrameLayout) viewGroup.findViewById(android.R.id.content);
            int count = content.getChildCount();
            View view = content.getChildAt(count - 1);
            int index = -1;
            if (view != null && view.getClass().getSimpleName().equals("ServerConfigView")) {
                index = count - 1;
            }
            content.addView(exceptionLayout, index, params);
        } else {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) exceptionLayout.getLayoutParams();
            params.leftMargin = leftMargin;
            params.topMargin = topMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            exceptionLayout.setLayoutParams(params);
        }
    }

    protected int getActivityLayoutId() {
        return R.layout.activity_bases;
    }

    // 添加遮罩
    protected void showLoadCovers() {
        showLoadCovers(0, 0, 0, 0, Color.WHITE);
    }

    protected void showLoadCovers(int leftMargin, int topMargin, int rightMargin, int bottomMargin, @ColorInt int color) {
        fixLoadCoverMargin();
        exceptionLayout.showLoadCovers(leftMargin, topMargin, rightMargin, bottomMargin, color);
    }


    protected void initRootView() {
        mTitleBar = ViewUtil.findViewById(this, R.id.titleBar);
        mContent = ViewUtil.findViewById(this, R.id.base_activity_content);
    }


    protected void setUpView() {
        setTitleBarGone();
        mContent.removeAllViews();
        mContent.addView(View.inflate(this, layoutContentId(), null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView(false);
        }
        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    protected abstract int layoutContentId();

    protected abstract P createPresenter();

    protected abstract void initView();

    protected void initData(@NonNull Bundle bundle) {
    }

    /**
     * 首次请求
     */
    protected abstract void loadData();

    /**
     * 再次请求
     */
    protected abstract void loadAgain();

    protected void setTitleBarGone() {
        ViewUtil.setGone(mTitleBar);
        errorTopMargin = 0;
    }

    protected void setTitleBarVisible() {
        ViewUtil.setVisible(mTitleBar);
        fixErrorMargin();
    }

    protected void fixErrorMargin() {
        errorTopMargin = defaultErrorMargin;
        if (exceptionLayout != null && ViewUtil.isVisible(exceptionLayout)) {
            fixExceptionLayoutMargin();
        }
    }


    protected void onCoversHiddenChanged(boolean isHidden) {
        if (isHidden) {
            setTitleBarVisible();
        }
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

    // 显示有数据的View
    protected void showLoadDataView() {
        ViewUtil.setVisible(mContent);
        onCoversHiddenChanged(true);
        dissLoading();
    }

    // 显示错误的View
    protected void showExceptionView() {
        ViewUtil.setGone(mContent);
        fixExceptionLayoutMargin();
        dissLoading();
        onCoversHiddenChanged(true);
    }


    /**
     * 加载无数据,不显示重试按钮
     */
    public void showEmpty(String tv) {
        showExceptionView();
        exceptionLayout.showEmpty(tv);
    }

    /**
     * 加载无数据
     */
    @Override
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
    @Override
    public void showFailure(String tv, String btn) {
        showExceptionView();
        exceptionLayout.showLoadFailure(tv, btn);
        exceptionLayout.setLoadFailureOnClickListener(loadFailOnClickListener);
    }

    /**
     * 显示进度条
     * 全透明，提示语：正在加载，返回键可点击
     */
    @Override
    public void showLoading() {
        showLoading(false);
    }


    public void showLoading(boolean cancelable) {
        showLoading(ResUtil.getString(R.string.dialog_title), cancelable, R.style.confirm_dialog);
    }

    /**
     * 显示进度条
     *
     * @param title      提示语
     * @param cancelable 返回键是否可点击
     * @param style      样式 全透明-R.style.dialog，半透明-R.style.dialog2
     */
    public void showLoading(String title, boolean cancelable, int style) {
        LoadingUtil.showLoading(this, title, cancelable, style);
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void dissLoading() {
        LoadingUtil.dismissLoading(this);
    }

}