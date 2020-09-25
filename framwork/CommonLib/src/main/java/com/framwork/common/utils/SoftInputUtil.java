package com.framwork.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 *
 */
public class SoftInputUtil {


    /**
     * 隐藏软键盘
     *
     * @param context
     * @Description:
     */
    public static void hideSoftInput(Activity context) {
        try {
            InputMethodManager inputMethodManager = OSUtils.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromInputMethod(context.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 切换输入法显示隐藏状态
     *
     * @param context
     * @Description:
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = OSUtils.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param context
     * @param binder  输入法所在控件的token
     * @Description:
     */
    public static void hideSoftInput(Context context, IBinder binder) {
        InputMethodManager imm = OSUtils.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }



    /**
     * @param context
     * @param view    onCreate()  中不能马上弹出，需要稍微延时
     */
    public static void showInputMethod(final Context context, final View view) {
        showInputMethod(context, view, 100);
    }


    public static void showInputMethod(final Context context, final View view, long delayMillis) {
        if (context != null) {
            new Handler() {
                public void handleMessage(Message msg) {
                    InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.showSoftInput(view, 0);
                }
            }.sendEmptyMessageDelayed(1, delayMillis);
        }
    }


}
