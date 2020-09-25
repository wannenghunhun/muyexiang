package com.framwork.common.utils;

import android.support.annotation.Nullable;

/**
 *
 */
public final class ExceptionUtil {

    public static <T> T checkNotNUll(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }


    public static void illegalArgument(String message) {
        throw new IllegalArgumentException(message);
    }


    public static void printStackTrace(@Nullable Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
            LogUtil.e(throwable.getMessage());
        }
    }

}
