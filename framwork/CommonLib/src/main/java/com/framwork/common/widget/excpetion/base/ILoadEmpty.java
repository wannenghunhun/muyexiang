package com.framwork.common.widget.excpetion.base;

import android.view.View;

/**
 *
 */
public interface ILoadEmpty {


    View getEmptyView();


    /**
     * showEmpty 方法用于请求的数据为空的状态
     */
    default void showEmpty() {
        showEmpty("暂时没有数据");
    }

    // 不显示重试按钮
    void showEmpty(String tv);

    // 显示重试按钮
    void showEmpty(String tv, String btn);

    // 重试按钮点击事件
    void setLoadEmptyOnClickListener(View.OnClickListener onClickListener);


    void hideEmptyView();

    void showEmptyView();

}
