package com.framwork.common.widget.excpetion.base;

import android.view.View;

/**
 *
 */
public interface ILoadFailure {


    View getLoadFailureView();


    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    default void showFailure() {
        showLoadFailure("哎呀，服务器出了点小问题...", "点击屏幕刷新");
    }

    // 显示重试按钮
    void showLoadFailure(String tv, String btn);

    // 重试按钮点击事件
    void setLoadFailureOnClickListener(View.OnClickListener onClickListener);


    void hideLoadFailureView();

    void showLoadFailureView();

}
