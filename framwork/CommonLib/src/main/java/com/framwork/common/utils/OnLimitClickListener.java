package com.framwork.common.utils;

import android.view.View;

/**
 * 限制一定时间内按钮的点击
 */

public abstract class OnLimitClickListener implements View.OnClickListener {
    
    private static final int MIN_CLICK_DELAY_TIME = 3000;//间隔时间，1秒
    
    private static long lastClickTime;
    
    public abstract void onLimitClick(View v);
    
    @Override
    public void onClick(View v) {
        
        long curClickTime = System.currentTimeMillis();
        
        if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onLimitClick(v);
        }
    }
}

