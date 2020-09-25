package com.framwork.common.utils;

import android.text.TextUtils;

public class MobileEncrypt {
    public static String mobileEncrypt(String mobile){
        if(TextUtils.isEmpty(mobile) || (mobile.length() != 11)){
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
