package com.framwork.main.http;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 描 述: 网络请求统一bean类
 **/

public class ResultBean<T> implements Serializable {
    public String code;
    public String msg;
    public T data;

    public static final String PARSE_ERROR = "-1"; // 数据解析错误
    public static final String NET_ERROR = "-2"; // 网络访问错误

    public boolean isSuccess() {
        return TextUtils.equals("0", code);
    }

    public boolean isEmpty() {
        return TextUtils.equals("0", code) && data == null;
    }

    public boolean isParseError() {
        return TextUtils.equals(PARSE_ERROR, code);
    }

    public boolean isNetError() {
        return TextUtils.equals(NET_ERROR, code);
    }

    public boolean shouldReLogin() { //判断是否应该重新登录
        return TextUtils.equals("403", code);
    }

    public boolean isFailure() {
        return !(TextUtils.equals("0", code));
    }


    @Override
    public String toString() {
        return "ResultBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
