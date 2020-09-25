package com.framwork.common.utils;

import android.text.TextUtils;

/**
 * Duchong
 * 2018.11.13
 * 校验类
 */
public class VerifyUtil {


    public static boolean phoneVerify(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.matches("^[1]\\d{10}$")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean pwdVerify(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
//        if(pwd.matches("^((?=.*[0-9].*)(?=.*[a-z].*)){6,12}$")){
        if (pwd.length() >= 6 && pwd.length() <= 18) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean smsCodeVerify(String smsCode) {
        if (TextUtils.isEmpty(smsCode)) {
            return false;
        }
        if (smsCode.matches("^\\d{6}$")) {
            return true;
        } else {
            return false;
        }
    }


}
