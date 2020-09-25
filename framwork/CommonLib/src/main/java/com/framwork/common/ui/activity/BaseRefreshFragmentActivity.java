package com.framwork.common.ui.activity;

import android.os.Handler;

import com.framwork.common.R;
import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.utils.ViewUtil;
import com.framwork.common.widget.DefaultRefreshFooter;
import com.framwork.common.widget.DefaultRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 *
 * 可以刷新的 BaseFragmentActivity
 */
public abstract class BaseRefreshFragmentActivity<P extends BasePresenter> extends BaseFragmentActivity<P> {

    protected SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void initRootView() {
        super.initRootView();
        smartRefreshLayout = ViewUtil.findViewById(this, R.id.smartRefreshLayout);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_refresh_base;
    }


    @Override
    protected void setUpView() {
        super.setUpView();
        //设置刷新相关
        setEnableRefresh(true);
        setEnableLoadMore(false);
        setDefaultRefreshHeaderFooter();
        setOnRefreshLoadMoreListener();
    }

    /**
     * 设置下拉刷新是否可用
     *
     * @param enable
     */
    protected void setEnableRefresh(boolean enable) {
        smartRefreshLayout.setEnableRefresh(enable);
    }

    /**
     * 设置上拉加载是否可用
     *
     * @param enable
     */
    protected void setEnableLoadMore(boolean enable) {
        smartRefreshLayout.setEnableLoadMore(enable);
    }

    /**
     * 设置通用的刷新header和footer
     */
    protected void setDefaultRefreshHeaderFooter() {
        smartRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(this));
        smartRefreshLayout.setRefreshFooter(new DefaultRefreshFooter(this));
    }

    /**
     * 设置下拉刷新和上拉加载监听
     */
    private void setOnRefreshLoadMoreListener() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout ->
                pullDownRefresh()
        );

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout ->
                pullUpLoadMore()
        );
    }

    /**
     * 刷新
     */
    protected void pullDownRefresh() {
    }

    /**
     * 更多
     */
    protected void pullUpLoadMore() {
    }

    /**
     * 刷新完成
     */
    public void onRefreshComplete(int delayTime) {
        if (null != smartRefreshLayout && smartRefreshLayout.getState().isOpening) {

            smartRefreshLayout.finishRefresh(delayTime);
        }
    }

    /**
     * 更多完成
     */
    public void onLoadMoreComplete(int delayTime) {
        if (null != smartRefreshLayout && smartRefreshLayout.getState().isOpening) {

            smartRefreshLayout.finishLoadMore(delayTime);
        }
    }

    @Override
    protected void showLoadDataView() {
        super.showLoadDataView();
        ViewUtil.setVisible(smartRefreshLayout);
    }

    @Override
    protected void showExceptionView() {
        super.showExceptionView();
        ViewUtil.setGone(smartRefreshLayout, mContent);
    }

    @Override
    public void dissLoading() {
        super.dissLoading();
        onRefreshComplete(200);
        onLoadMoreComplete(200);
    }


    public void setNoMoreData(boolean isHaveMoreData) {
        smartRefreshLayout.finishLoadMore(300, true, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setNoMoreData(isHaveMoreData);
                }
            }
        }, 800L);
    }
}
