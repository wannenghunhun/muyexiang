package com.framwork.common.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.framwork.common.R;
import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.utils.ViewUtil;
import com.framwork.common.widget.DefaultRefreshFooter;
import com.framwork.common.widget.DefaultRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 *
 * 带刷新的view
 */
public abstract class BaseRefreshFragment<K extends BasePresenter> extends BaseFragment<K> {


    protected SmartRefreshLayout smartRefreshLayout;


    protected int getFragmentLayoutId() {
        return R.layout.fragment_bases;
    }


    @Override
    protected View createRootView() {
        return super.createRootView();
    }

    @Override
    protected void initRootView(View rootView) {
        rootContentView = ViewUtil.findFLById(rootView, R.id.fl_root);
        View refreshLayout = ViewUtil.inflate(getFragmentLayoutId());
        rootContentView.addView(refreshLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        mContent = ViewUtil.findViewById(refreshLayout, R.id.base_fragment_content);
        smartRefreshLayout = ViewUtil.findViewById(refreshLayout, R.id.smartRefreshLayout);
    }

    @Override
    protected void setUpViews(View rootView) {
        ViewGroup viewGroup = (ViewGroup) mContent;
        viewGroup.removeAllViews();
        LayoutInflater.from(mActivity).inflate(layoutContentId(), viewGroup);
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
    private void setDefaultRefreshHeaderFooter() {
        smartRefreshLayout.setRefreshHeader(new DefaultRefreshHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new DefaultRefreshFooter(getActivity()));
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

    protected void pullDownRefresh() {
    }

    public void onRefreshComplete(int delayTime) {
        if (null!= smartRefreshLayout && smartRefreshLayout.getState().isOpening) {

            smartRefreshLayout.finishRefresh(delayTime);
        }
    }

    public void onLoadMoreComplete(int delayTime) {
        if (null!= smartRefreshLayout && smartRefreshLayout.getState().isOpening) {

            smartRefreshLayout.finishLoadMore(delayTime);
        }
    }

    protected void pullUpLoadMore() {
    }


    @Override
    protected void showLoadDataView() {
        super.showLoadDataView();
        ViewUtil.setVisible(smartRefreshLayout);
    }

    @Override
    protected void showExceptionView() {
        super.showExceptionView();
        ViewUtil.setGone(smartRefreshLayout);
    }

    @Override
    public void dissLoading() {
        super.dissLoading();
        onRefreshComplete(200);
        onLoadMoreComplete(200);
    }

    public void setNoModerData() {
        smartRefreshLayout.setNoMoreData(true);
        smartRefreshLayout.finishLoadMore(300, true, true);
    }
}
