package com.framwork.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 */
public class FormatUtil {

    public static int str2Int(String str) {
        int l = 0;
        try {
            l = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            LogUtil.e("%s str2Integer %s", str, "error");
        }
        return l;
    }

    public static float str2Float(String str) {
        float l = 0f;
        try {
            l = Float.valueOf(str);
        } catch (NumberFormatException e) {
            LogUtil.e("%s str2Float %s", str,"error");
        }
        return l;
    }

    public static long str2Long(String str) {
        long l = 0L;
        try {
            l = Long.valueOf(str);
        } catch (NumberFormatException e) {
            LogUtil.e("%s str2Long %s", str,"error");
        }
        return l;
    }


    public static double str2Double(String str) {
        double l = 0d;
        try {
            l = Double.valueOf(str);
        } catch (NumberFormatException e) {
            LogUtil.e("%s str2Double %s", str,"error");
        }
        return l;
    }


    public static String getNumberFormat(double money, String formatStr) {
        NumberFormat nf = new DecimalFormat(formatStr);
        return nf.format(money);
    }

    public static String getNumberFormat(String money, String formatStr) {
        NumberFormat nf = new DecimalFormat(formatStr);
        return nf.format(str2Float(money));
    }

    public static String format(String str, Object... args) {
        return String.format(str, args);
    }

    public static <T> T defaultIfNull(T str, T defaultValue) {
        return str == null ? defaultValue : str;
    }

    /**
     * 保留两位小数
     * 描 述： 四舍五入
     * 作 者：qin
     *
     * @param d
     * @return
     */
    public static double decimalUp(double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 加密手机号
     *
     * @param phone 手机号
     * @return 加密后结果
     */
    public static String encryptPhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return phone;
        }
        String result = RegularUtil.replaceAll(phone, "(.{2})(.{0,6})(.*)", "$1******$3");
        return result.substring(0, phone.length());
    }

    /**
     * 加密银行储蓄卡号
     * 19位卡号   末三位明文，其他暗文*展示，每四位加一空格
     * 16位卡号   末四位明文，其他暗文*展示，每四位两个空格
     *
     * @param bankCard 银行储蓄卡号
     * @return 加密后结果
     */
    public static String encryptBankCard(String bankCard) {

        int length = bankCard.length();
        String result = bankCard;
        if (length == 19) {
            result = RegularUtil.replaceAll(bankCard, "(.{4})(.{4})(.{4})(.{4})(.{3})", " **** **** **** **** $5");
        } else if (length == 16) { // 16位卡号
            result = RegularUtil.replaceAll(bankCard, "(.{4})(.{4})(.{4})(.{4})", "  ****  ****  ****  $4");
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(encryptPhone("18515618152"));
        System.out.println(str2Int("185.112"));
        System.out.println(str2Int("185."));
        System.out.println(str2Int(".11"));
        System.out.println(str2Int("0.11"));
    }

}
