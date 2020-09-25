package com.framwork.main.lifecycle;

import android.app.Activity;
import android.util.Log;

import com.framwork.common.ui.lifecycle.IActivityLifecycle;


/**
 *
 */

public class ActivityLogLifecycle implements IActivityLifecycle {

    private String seg = "  ";

    @Override
    public void onCreate(Activity activity) {
        log(activity, "onCreate");
    }

    @Override
    public void onResume(Activity activity) {
        log(activity, "onResume");
    }

    @Override
    public void onStart(Activity activity) {
        log(activity, "onStart");
    }

    @Override
    public void onRestart(Activity activity) {
        log(activity, "onRestart");
    }

    @Override
    public void onPause(Activity activity) {
        log(activity, "onPause");
    }

    @Override
    public void onStop(Activity activity) {
        log(activity, "onStop");
    }

    @Override
    public void onDestroy(Activity activity) {
        log(activity, "onDestroy");
    }

    @Override
    public void onNewIntent(Activity activity) {
        log(activity, "onNewIntent");
    }


    private String getClassName(Activity activity) {
        return activity.getClass().getSimpleName() + seg + "--->";
    }

    @SuppressWarnings("all")
    private void log(Activity activity, String... strings) {
        StringBuilder sb = new StringBuilder(getClassName(activity));
        if(strings != null && strings.length >= 1) {
            int count = strings.length;
            for(int i = 0; i < count; i++) {
                sb.append(seg).append("%s");
            }
        }
        Log.d("Activity", String.format(sb.toString(), (Object[]) strings));
    }
}
