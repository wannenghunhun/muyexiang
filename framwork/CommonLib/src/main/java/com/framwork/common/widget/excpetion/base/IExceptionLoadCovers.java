package com.framwork.common.widget.excpetion.base;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 *
 */
public interface IExceptionLoadCovers {


    View getCoversView();

    // 添加遮罩
    default void showLoadCovers() {
        showLoadCovers(0, 0, 0, 0, Color.WHITE);
    }

    void showLoadCovers(int leftMargin, int topMargin, int rightMargin, int bottomMargin, @ColorInt int color);


    void hideCovers();

}
