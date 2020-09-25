package com.framwork.common.widget.excpetion.base;

import android.view.View;

/**
 *
 */
public interface INetException {

    View getNetExceptionView();


    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    default void showNetExcepion() {
        showNetException("网络异常,请稍后重试", "点击屏幕刷新");
    }


    // 显示重试按钮
    void showNetException(String tv, String btn);

    // 重试按钮点击事件
    void setNetExceptionOnClickListener(View.OnClickListener onClickListener);


    void hideNetExceptionView();

    void showNetExceptionView();
}
