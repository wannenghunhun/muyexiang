package com.framwork.common.ui.fragment;


import android.view.View;


import com.framwork.common.presenter.BasePresenter;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liyong
 * @date 2018/11/16
 * @des 懒加载 ，所见即所得
 */
public abstract class LazyBaseRefreshFragment<T extends BasePresenter> extends BaseRefreshFragment<T> {


    private AtomicBoolean mIsVisibleToUser = new AtomicBoolean(false);
    private AtomicBoolean mIsLoaded = new AtomicBoolean(false);
    private AtomicBoolean mIsViewCreated = new AtomicBoolean(false);

    public abstract void getBundleData();

    @Override
    protected final void loadData() {
        getBundleData();
        loadAgain();
    }


    protected abstract void lazyLoadData();

    @Override
    protected final void loadAgain() {
        if (mIsViewCreated.get() && mIsVisibleToUser.get()) {
            mIsLoaded.set(true);
            lazyLoadData();
        }
    }

    @Override
    protected void setUpViews(View rootView) {
        super.setUpViews(rootView);
        mIsViewCreated.set(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser.set(isVisibleToUser);
        if (mIsVisibleToUser.get()) {
            if (mIsViewCreated.get() && !mIsLoaded.get()) {
                loadAgain();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoaded.set(false);
        mIsViewCreated.set(false);
        mIsVisibleToUser.set(false);
    }
}
