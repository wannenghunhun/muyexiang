package com.framwork.common.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.framwork.common.ui.lifecycle.IActivityLifecycle;
import com.framwork.common.utils.ActivityManager;
import com.framwork.common.utils.AppUtil;
import com.framwork.common.utils.LoadingUtil;


/**
 *
 * Activity 设置竖屏
 * Activity 堆栈管理
 * 网络请求取消
 * 防劫持检查--梆梆加固建议
 */
public class DLRActivityLifecycle implements IActivityLifecycle {

    @Override
    public void onCreate(Activity activity) {
        ActivityManager.push(activity);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStop(Activity activity) {
        LoadingUtil.dismissLoading(activity);
    }

    @Override
    public void onDestroy(Activity activity) {
        LoadingUtil.pop(activity);
//        RestClient.cancelRequest(activity);
        ActivityManager.pop(activity);
    }

    @Override
    public void onWindowFocusChanged(Activity activity, boolean hasFocus) {
        if (!hasFocus) {
            AppUtil.checkHijack();
        }
    }
}
