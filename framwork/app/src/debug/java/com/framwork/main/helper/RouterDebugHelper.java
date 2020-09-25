package com.framwork.main.helper;

import android.util.Log;

import com.alibaba.android.arouter.facade.template.ILogger;
import com.alibaba.android.arouter.launcher.ARouter;
import com.framwork.main.MyApplication;

/**
 *
 */
public class RouterDebugHelper {


    public static void install(MyApplication myApplication) {

        ARouter.setLogger(new ILogger() {
            @Override
            public void showLog(boolean isShowLog) {

            }

            @Override
            public void showStackTrace(boolean isShowStackTrace) {

            }

            @Override
            public void debug(String tag, String message) {
                Log.d(tag, message);
            }

            @Override
            public void info(String tag, String message) {
                Log.d(tag, message);
            }

            @Override
            public void warning(String tag, String message) {
                Log.w(tag, message);
            }

            @Override
            public void error(String tag, String message) {
                Log.e(tag, message);
            }

            @Override
            public void monitor(String message) {

            }

            @Override
            public boolean isMonitorMode() {
                return false;
            }

            @Override
            public String getDefaultTag() {
                return null;
            }
        });
        ARouter.openDebug();
        ARouter.openLog();

    }

}
