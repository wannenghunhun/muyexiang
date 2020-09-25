package com.framwork.common.utils;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * <p>
 * tv_span1.setText(SpanBuilder.newInstance()
 * .append("蓝色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_blue_dark)))
 * .append("红色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_red_dark)))
 * .append("hah")
 * .buildSpan());
 * <p>
 * <p>
 * tv_span2.setText(SpanBuilder.newInstance("蓝色，红色，绿色 base")
 * .setSpan("蓝色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_blue_dark)))
 * .setSpan("红色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_red_dark)))
 * .setSpan("绿色", new ForegroundColorSpan(ResUtil.getColor(android.R.color.holo_green_dark)))
 * .buildSpan());
 */

public final class SpanBuilder {


    private StringBuilder allStr = new StringBuilder();

    private ArrayList<SpanModel> spanModels = new ArrayList<>();

    public static SpanBuilder newInstance() {
        return new SpanBuilder();
    }

    public static SpanBuilder newInstance(String allStr) {
        return new SpanBuilder(allStr);
    }


    private SpanBuilder() {

    }

    private SpanBuilder(String allStr) {
        this.allStr = new StringBuilder(allStr);

    }


    private class SpanModel {
        public int start; // 开始位置
        public int end;// 结束位置
        int flags = Integer.MIN_VALUE;
        public Object span;
    }


    public SpanBuilder setSpan(String string, Object... spans) {
        return setSpan(string, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }

    public SpanBuilder setSpan(String string, int flags, Object... spans) {
        int start = allStr.indexOf(string);
        if (start < 0) {
            return this;
        }
        int end = start + string.length();
        addSpanModel(start, end, flags, spans);
        return this;
    }

    // 正则匹配
    public SpanBuilder setRegexSpan(String regex, Object... spans) {
        return setRegexSpan(regex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }

    // 正则匹配
    public SpanBuilder setRegexSpan(String regex, int flags, Object... spans) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(allStr);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            addSpanModel(start, end, flags, spans);
        }
        return this;
    }


    public SpanBuilder setSpan(String[] strings, Object... spans) {

        return setSpan(strings, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanBuilder setSpan(String[] strings, int flags, Object... spans) {
        for (String s : strings) {
            setSpan(s, flags, spans);
        }
        return this;
    }


    public SpanBuilder append(String string, Object... spans) {
        return append(string, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanBuilder append(String string, int flags, Object... spans) {
        int start = allStr.length();
        allStr.append(string);
        int end = allStr.length();
        addSpanModel(start, end, flags, spans);
        return this;
    }

    public SpanBuilder append(String[] strings, Object... spans) {
        return append(strings, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, spans);
    }


    public SpanBuilder append(String[] strings, int flags, Object... spans) {
        for (String string : strings) {
            append(string, flags, spans);
        }
        return this;
    }

    public SpanBuilder append(String string) {
        allStr.append(string);
        return this;
    }


    public SpanBuilder append(String[] string) {
        for (String s : string) {
            append(s);
        }
        return this;
    }


    private void addSpanModel(int start, int end, int flags, Object... spans) {
        for (Object span : spans) {
            SpanModel spanModel = new SpanModel();
            spanModel.start = start;
            spanModel.end = end;
            spanModel.span = span;

            spanModel.flags = flags;
            spanModels.add(spanModel);
        }
    }


    public SpannableString buildSpan() {
        SpannableString spannableString = new SpannableString(allStr.toString());
        for (SpanModel spanModel : spanModels) {
            spannableString.setSpan(spanModel.span, spanModel.start, spanModel.end, spanModel.flags);
        }
        return spannableString;
    }

    public <T extends TextView> void apply(T view) {
        view.setText(buildSpan());
    }

    /**
     * @param view
     * @param movementMethod 设置不同位置文字的点击事件需要,{@link LinkMovementMethod#getInstance()}
     * @param <T>
     */
    public <T extends TextView> void apply(T view, MovementMethod movementMethod) {
        view.setText(buildSpan());
        view.setMovementMethod(movementMethod);
    }
}
