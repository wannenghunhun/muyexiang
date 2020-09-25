package com.framwork.common.utils;

import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;


public class LogUtil {


    private static boolean isShowLog = false;

    public static void setShowLog(boolean showLog) {
        isShowLog = showLog;
    }

    public static void v(String mess, @Nullable Object... args) {
        if (isShowLog) {
            Logger.v(mess, args);
        }
    }

    public static void d(String mess, @Nullable Object... args) {
        if (isShowLog) {
            Logger.d(mess, args);
        }
    }

    public static void i(String mess, @Nullable Object... args) {
        if (isShowLog) {
            Logger.i(mess, args);
        }
    }

    public static void w(String mess, @Nullable Object... args) {
        if (isShowLog) {
            Logger.w(mess, args);
        }
    }

    public static void e(String mess, @Nullable Object... args) {
        if (isShowLog) {
            Logger.e(mess, args);
        }
    }

    public static void v(String mess) {
        if (isShowLog) {
            Logger.v(mess);
        }
    }

    public static void d(String mess) {
        if (isShowLog) {
            Logger.d(mess);
        }
    }

    public static void i(String mess) {
        if (isShowLog) {
            Logger.i(mess);
        }
    }

    public static void w(String mess) {
        if (isShowLog) {
            Logger.w(mess);
        }
    }

    public static void e(String mess) {
        if (isShowLog) {
            Logger.e(mess);
        }
    }

    public static void json(String url, String res) {
        if (isShowLog) {
            Logger.d(url);
            Logger.json(res);
        }
    }
}
