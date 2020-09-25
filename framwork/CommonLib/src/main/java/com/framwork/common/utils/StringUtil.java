package com.framwork.common.utils;

import android.text.TextUtils;

import java.nio.charset.Charset;
import java.util.StringJoiner;

public class StringUtil {
    
    /**
     * 字符超过最大长度替换所超字符
     *
     * @param str    待判定字符串
     * @param length 限定长度
     * @param rep    替换
     * @return target str
     */
    public static String replaceMaxChar(String str, int length, String rep) {
        if(isEmpty(str)) {
            return str;
        }
        if(str.length() > length) {
            return str.substring(0, length) + rep;
        }
        else {
            return str;
        }
    }
    
    /**
     * 字符超过最大长度使用(...)替换所超字符
     *
     * @param str    待判定字符串
     * @param length 限定长度
     * @return target str
     */
    public static String replaceMaxChar(String str, int length) {
        return replaceMaxChar(str, length, "...");
    }
    
    
    public static String defaultIfEmpty(String str, String defaultStr) {
        if(StringUtil.isEmpty(str)) {
            return defaultStr;
        }
        return str;
    }
    
    public static String defaultIfEmpty(String str) {
        return defaultIfEmpty(str, "");
    }
    
    
    /**
     * 判断是否为空
     *
     * @param str
     * @return
     * @Description:
     */
    public static boolean isNull(String str) {
        if(str == null || str.equals("") || str.equals("null")) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * 是否不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim());
    }
    
    /**
     * 是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim()) || s.trim().length() == 0;
    }
    
    
    public static byte[] str2Bytes(String str) {
        return str2Bytes(str, "utf-8");
    }
    
    public static byte[] str2Bytes(String str, String charSetName) {
        return str2Bytes(str, Charset.forName(charSetName));
    }
    
    public static byte[] str2Bytes(String str, Charset charset) {
        byte[] result;
        if(isEmpty(str)) {
            result = new byte[]{};
        }
        else {
            result = str.getBytes(charset);
        }
        return result;
    }

    /**
     * @param res
     * @param args
     * @return
     */
    public static String getFormatStr(String res, Object... args) {
        return String.format(res, args);
    }
    
    /**
     * 拼接字符串
     *
     * @param delimiter 分割符
     * @param tokens    数组数据
     * @return String
     */
    public static String join(CharSequence delimiter, Object... tokens) {
        if(OSUtils.hasN_24()) {
            StringJoiner joiner = new StringJoiner(delimiter);
            for(Object token : tokens) {
                joiner.add(token.toString());
            }
            return joiner.toString();
        }
        return TextUtils.join(delimiter, tokens);
    }

}
