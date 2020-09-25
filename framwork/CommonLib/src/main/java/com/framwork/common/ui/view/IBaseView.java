package com.framwork.common.ui.view;


public interface IBaseView {
    /**
     * showLoading 方法主要用于页面请求数据时显示加载状态
     */
    void showLoading();
    /**
     * showLoading 方法主要用于页面请求数据时显示加载状态
     */
    void showLoading(boolean b);

    /**
     * dissLoading 方法主要用于页面请求数据完成关闭
     */
    void dissLoading();

    /**
     * showEmpty 方法用于请求的数据为空的状态,不显示重试按钮
     */
    void showEmpty(String msg1);

    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    void showEmpty(String msg1, String msg2);

    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    default void showEmpty() {
        showEmpty("暂时没有数据");
    }


    /**
     * showError 方法用于请求数据出错
     */
    void showFailure(String msg1, String msg2);

    /**
     * showError 方法用于请求数据出错
     */
    default void showFailure() {
        showFailure("哎呀，服务器出了点小问题...", "点击屏幕刷新");
    }

    /**
     * showError 方法用于请求数据网络异常
     */
    void showNetException(String msg1, String msg2);

    /**
     * showError 方法用于请求数据网络异常
     */
    default void showNetException() {
        showNetException("网络异常,请稍后重试", "点击屏幕刷新");
    }

    /**
     * 加载完成隐藏失败页面
     */
    void loadComplete();


}
