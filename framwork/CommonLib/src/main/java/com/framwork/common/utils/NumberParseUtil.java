package com.framwork.common.utils;

import android.text.TextUtils;

/**
 * Created by zhongxionghui on 2016/7/23.
 */
public class NumberParseUtil {
    public static int parseInt(String s) {
        int ans = 0;
        if(TextUtils.isEmpty(s)) {
            return ans;
        }
        if(s.indexOf(".") > 0) {
            return (int) parseDouble(s); //小数强转
        }
        try {
            ans = Integer.parseInt(s);
        } catch(Exception e) {
            ans = 0;
        }
        return ans;
    }
    
    public static long parseLong(String s) {
        long ans = 0;
        if(TextUtils.isEmpty(s)) {
            return ans;
        }
        try {
            ans = Long.parseLong(s);
        } catch(Exception e) {
            ans = 0;
        }
        return ans;
    }
    
    public static double parseDouble(String s) { //保留两位小数返回double
        double ans = 0.00;
        if(TextUtils.isEmpty(s)) {
            return ans;
        }
        try {
            ans = Double.parseDouble(s);
        } catch(Exception e) {
            ans = 0.00;
        }
        return ans;
    }
    
}
