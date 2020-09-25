package com.framwork.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class MoneyUtil {


    // 判断是否是格式化  严格模式，必须是带逗号的小数或者整数
    public static boolean isStrictFormatMoney(String string) {
        Pattern pattern = Pattern.compile("(([1-9][0-9]{0,2}(,[0-9]{3})*)|0)(\\.[0-9]{1,2})?");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }


    // 判断是否是格式化   非严格模式，用户输入状态下的字符串
    public static boolean isFormatMoney(String string) {
        Pattern pattern = Pattern.compile("(([1-9][0-9]{0,2}(,[0-9]{3})*)|0)(\\.[0-9]{0,2})?");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 格式化金额输入
     * 金额是整数
     *
     * @return
     */
    public static String formatMoney(String money) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = money.length();
        for (int i = length - 1; i >= 0; i--) {
            if ((length - i - 1) > 0 && (length - i - 1) % 3 == 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(money.charAt(i));
        }
        return stringBuilder.reverse().toString();
    }


    public static String formatIntStr(String srcStr) {
        String srtInt = srcStr.replaceAll(",", "");
        StringBuilder resultSb = new StringBuilder();
        int length = srtInt.length();
        for (int i = length - 1; i >= 0; i--) {
            int offset = (length - i - 1);
            if (offset > 0 && offset % 3 == 0) {
                resultSb.append(",");
            }
            resultSb.append(srtInt.charAt(i));
        }
        return resultSb.reverse().toString();
    }


    /**
     * 格式化金额输入
     * 金额是整数
     *
     * @return
     */
    public static String formatIntStr(int money) {
        return formatIntStr(String.valueOf(money));
    }

    // 带小数点
    public static String formatMoney(double money) {
        return formatMoney(money + "");
    }

    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String getFormatMoney(double money) {
        return FormatUtil.getNumberFormat(money, "#.##");
    }


    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String getFormatDotMoney(double money) {
        return FormatUtil.getNumberFormat(money + "", "#,###.##");
    }


    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String getFormatMoney(String money) {
        return FormatUtil.getNumberFormat(money, "#.##");
    }


    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String getFormatDotMoney(String money) {
        return FormatUtil.getNumberFormat(money + "", "#,###.##");
    }

    /**
     * 将金额格式化为0,000.00元,末尾不保留0，如2000.00格式化为2000
     *
     * @return
     */
    public static String moneyFormatNoZero(String money) {
        try {
            double up = FormatUtil.decimalUp(Double.valueOf(money));
            DecimalFormat a = new DecimalFormat("##,###,###,##0.00");
            String tmp = a.format(up);
            tmp = tmp.replaceAll("0+?$", "");//去掉多余的0
            tmp = tmp.replaceAll("[.]$", "");//如最后一位是.则去掉
            return tmp + "元";
        } catch (NumberFormatException e) {
            //如果出现异常,直接返回原字段
            return money + "元";
        }

    }


    /**
     * 金额格式化
     *
     * @param s   要格式化的金额
     * @param len 保留的小数位 四舍五入
     * @return
     */
    public static String spiltAmt(String s, int len) {
        if (s == null || s.equals("") || s.equals("null")) {
            s = "0";
        }
        NumberFormat formater = null;
        double num = NumberParseUtil.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }
        formater.setRoundingMode(RoundingMode.HALF_UP);

        return formater.format(num);

    }

    /**
     * 金额格式化
     *
     * @param s   要格式化的金额,不带逗号
     * @param len 保留的小数位 四舍五入
     * @return
     */
    public static String spiltAmt2(String s, int len) {
        if (s == null || s.equals("") || s.equals("null")) {
            s = "0";
        }
        NumberFormat formater = null;
        double num = NumberParseUtil.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###.00");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }
        formater.setRoundingMode(RoundingMode.HALF_UP);

        return formater.format(num);

    }

    /**
     * 金额格式化
     *
     * @param num 要格式化的金额
     * @param len 保留的小数位 四舍五入
     * @return
     */
    public static String spiltAmt(double num, int len) {
        NumberFormat formater = null;
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,##0.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            formater = new DecimalFormat(buff.toString());
        }
        formater.setRoundingMode(RoundingMode.HALF_UP);

        return formater.format(num);

    }


    /**
     * 金额去格式化
     *
     * @param s
     * @return
     */
    public static String delComma(String s) {
        String formatString = "";
        if (s != null && s.length() >= 1) {
            formatString = s.replaceAll(",", "");
        }

        return formatString;
    }


    /**
     * 利息格式化,末尾不保留0,如6.50%格式化为6.5%
     */
    public static String rateFormatNoZero(Double rate, int len) {
        double up = FormatUtil.decimalUp(rate);
        StringBuilder stringBuilder = new StringBuilder("#0.");
        for (int i = 0; i < len; i++) {
            stringBuilder.append("0");
        }
        try {
            DecimalFormat a = new DecimalFormat(stringBuilder.toString());
            String tmp = a.format(up);
            return replaceDotZero(tmp) + "%";
        } catch (Exception e) {
            return "0%";
        }
    }


    public static String replaceDotZero(String string) {
        string = string.replaceAll("0+?$", "");//去掉多余的0
        string = string.replaceAll("[.]$", "");//如最后一位是.则去掉
        return string;
    }


    /**
     * 利息格式化,末尾不保留0,如6.50%格式化为6.5%
     */
    public static String rateFormatNoZero(String rate, int len) {
        return rateFormatNoZero(Double.valueOf(rate), len);
    }


    //利率显示格式化
    public static String rateFormat(String money) {
        String ans = "0";
        if (TextUtils.isEmpty(money) || TextUtils.equals(money, ".")) {
            return ans;
        }
        if (money.startsWith(".")) { // 以小数点开头,开头补 0
            money = "0" + money;
        }
        if (money.endsWith(".")) { // 以小数点结尾,抹除小数点返回
            return money.substring(0, money.length() - 1);
        }
        if (money.contains(".")) {
            return replaceDotZero(money);
        }
        return money;
    }


    public static long getIntMoney(String money) {
        if (money == null) {
            return 0L;
        }
        money = money.replaceAll(",", "");
        if (money.length() == 0) {
            return 0L;
        }
        int point = money.indexOf(".");
        return Long.parseLong(money.substring(0, point < 0 ? money.length() : point));
    }

    public static void main(String[] args) {

        System.out.println(isFormatMoney("11111"));
        System.out.println(isFormatMoney("11,111"));
        System.out.println(isFormatMoney("1,234.56"));
        System.out.println(isFormatMoney("0.12345"));


        System.out.println(isFormatMoney("1111"));
        System.out.println(isFormatMoney("111,1"));
        System.out.println(isFormatMoney("111,111.11"));
        System.out.println(isFormatMoney("111,111."));
        System.out.println(isFormatMoney("0."));
        System.out.println(isFormatMoney("0.1"));


    }


    /**
     * 公式： term * rate * money /100/365
     *
     * @param term  锁定期 单位为天
     * @param rate  年化利率  4.00  不带百分号
     * @param money 金额
     * @return
     */
    public static String calculateProfit(int term, float rate, float money) {
        float profit = term * rate * money / 100f / 365f;
        // 保留两位小数，四舍五入法
        return new BigDecimal(profit).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

    }

}
