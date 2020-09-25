package com.framwork.common.utils;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.annotation.FontRes;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.framwork.common.GlobalContext;

import java.util.HashMap;

/**
 *
 * 字体工具类
 */
public class TypefaceUtil {
    private static HashMap<Integer, Typeface> typefaceCache = new HashMap<>();

    @SuppressLint("RestrictedApi")
    public static Typeface getTypeface(@FontRes int resId) {
        Typeface cacheTypeface = typefaceCache.get(resId);
        if (cacheTypeface == null) {
            cacheTypeface = ResourcesCompat.getFont(GlobalContext.getContext(), resId);
            typefaceCache.put(resId, cacheTypeface);

        }
        return cacheTypeface;
    }
    public static <T extends TextView> void applyTypeface(T view, @FontRes int resId) {
        Typeface typeface = null;
        try {
            typeface = getTypeface(resId);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("Create Typeface by Res error %s", e.getMessage());
        }

        applyTypeface(view, typeface);
    }


    public static <T extends TextView> void applyTypeface(T view, Typeface typeface) {
        if (typeface == null) {
            return;
        }
        view.setTypeface(typeface);
    }


}
