package com.framwork.common.presenter;


import com.framwork.common.ui.view.IBaseView;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IBaseView> {
    
    private WeakReference<V> viewRef;
    
    public BasePresenter(V view) {
        attachView(view);
    }
    
    /**
     * 与界面关联
     */
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }
    
    
    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }
    
    
    /**
     * 判断界面是否已经销毁
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }
    
    /**
     * 与界取消关联
     */
    public void detachView(boolean retainInstance) {
        if(viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
    
}