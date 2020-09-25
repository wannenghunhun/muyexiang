package com.framwork.common.widget.excpetion.base;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 *
 */
public interface ILoadCovers {


    View getCoversView();

    // 添加遮罩
    default void showLoadCovers() {
        showLoadCovers(Color.WHITE);
    }

    void showLoadCovers(@ColorInt int color);


    void hideCovers();
}

