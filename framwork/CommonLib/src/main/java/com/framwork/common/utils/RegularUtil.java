package com.framwork.common.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
public final class RegularUtil {
    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return 是否合法
     */
    public static boolean isEmail(String email) {
        String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return isEmail(email, pattern1);
    }

    /**
     * 验证是否是邮箱
     *
     * @param email
     * @param regex
     * @return
     */
    public static boolean isEmail(String email, String regex) {
        return isMatch(email, regex);
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String regularStr = "[0-9]+";
        return isMatch(str, regularStr);
    }


    public static boolean isInteger(String num) {
        if (StringUtil.isEmpty(num)) {
            return false;
        }
        if (num.indexOf(".") < 0) { //没有小数点
            return true;
        }
        for (int i = num.indexOf(".") + 1; i < num.length(); i++) {
            if (num.charAt(i) != '0') { //小数点后面是非0
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否是字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        String regularStr = "[a-zA-Z]+";
        return isMatch(str, regularStr);
    }


    /**
     * 现在号码段分配如下：
     * <p/>
     * 移动： 139 138 137 136 135 134 147 150 151 152 157 158 159 182 183 187 188
     * <p/>
     * 联通： 130 131 132 155 156 185 186 145
     * <p/>
     * 电信： 133 153 180 181 189 177
     * <p/>
     * 参考：http://www.jihaoba.com/tools/?com=haoduan
     * 验证手机号,考虑+86
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        String p = "(^(\\+86)?(0?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8})$";

        return isPhone(str, p);
    }

    /**
     * 验证手机号
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str, String regularStr) {
        return isMatch(str, regularStr);
    }

    /**
     * @param c
     * @return 是否为中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 正则表达式:验证汉字(2-6个汉字)  {2,6} 自定义区间
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{2,6}$";

    /**
     *      * 校验汉字
     *      * @param chinese
     *      * @return 校验通过返回true，否则返回false
     *      
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * @param str
     * @return 是否仅仅包含数字和字母
     */
    public static boolean isNumericAndLetter(String str) {
        String regularStr = "([0-9]+[a-zA-Z]+)|([a-zA-Z]+[0-9]+)[0-9a-zA-Z]*";
        return isMatch(str, regularStr);
    }

    /**
     * Is match boolean.
     *
     * @param str        the str
     * @param regularStr the regular str
     * @return the boolean
     */
    public static boolean isMatch(String str, String regularStr) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(regularStr)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regularStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String replaceAll(String originStr, String regex, String replacement) {
        return originStr.replaceAll(regex, replacement);
    }


    public static void main(String[] args) {

        boolean isTrue = isPhone("+8618715218142");


        System.out.println(isTrue);


    }
    /**
     * 正则表达式：验证身份证
     */
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_ID_CARD18     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    public static boolean isIDCard18(String input) {
        return isMatch( input,REGEX_ID_CARD18);
    }
}
