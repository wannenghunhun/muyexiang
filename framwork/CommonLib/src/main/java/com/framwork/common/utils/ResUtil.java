package com.framwork.common.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnimRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.framwork.common.GlobalContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 *
 */
public class ResUtil {


    public static Resources getResources() {
        return GlobalContext.getContext().getResources();
    }


    /**
     * 从strings.xml中读取String
     *
     * @param resId String res id
     * @return string
     */
    public static String getString(@StringRes int resId) {
        return GlobalContext.getContext().getResources().getString(resId);
    }


    /**
     * 从colors.xml中读取color
     *
     * @param resId color id
     * @return color
     */
    @ColorInt
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(GlobalContext.getContext(), resId);
    }

    /**
     * create Drawable by color
     *
     * @param resId color id
     * @return Drawable
     */
    public static Drawable getColorDrawable(@ColorRes int resId) {
        return new ColorDrawable(getColor(resId));
    }


    /**
     * 从colors.xml中读取color
     *
     * @param resId color id
     * @return color
     */
    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return ContextCompat.getColorStateList(GlobalContext.getContext(), resId);
    }


    /**
     * 从arrays.xml中读取array[]
     *
     * @param resId String array id
     * @return String[]
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return GlobalContext.getContext().getResources().getStringArray(resId);
    }


    /**
     * 从dimens.xml里读取dimen
     *
     * @param resId dimensid
     * @return dimen
     */
    public static int getDimen(int resId) {
        return GlobalContext.getContext().getResources().getDimensionPixelSize(resId);
    }


    public static Drawable getDrawable(@DrawableRes int drawableResId) {
        try {
            return ContextCompat.getDrawable(GlobalContext.getContext(), drawableResId);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取 int 数组
     *
     * @param resId IntegerArray id
     * @return IntegerArray
     */
    public static int[] getIntegerArray(@ArrayRes int resId) {

        return GlobalContext.getContext().getResources().getIntArray(resId);
    }


    /**
     * @param resId
     * @param args
     * @return
     */
    public static String getFormatStr(@StringRes int resId, Object... args) {
        return StringUtil.getFormatStr(ResUtil.getString(resId), args);
    }

    /**
     * 获取动画
     *
     * @param animationID
     * @return
     */
    public static Animation getAnimation(@AnimRes int animationID) {
        return AnimationUtils.loadAnimation(GlobalContext.getContext(), animationID);
    }


    /**
     * @param filename
     * @return 从 assets 里读文件
     */
    public static String getStrFromAssets(String filename) {
        InputStream is;

        Writer writer = new StringWriter();
        char[] buffer = new char[8 * 1024];
        try {
            is = GlobalContext.getContext().getResources().getAssets().open(filename);
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n = 0;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }


    /**
     * @param filename
     * @return 从 assets 里读文件
     */
    public static String getPathFromAssets(String filename) {

        return "file:///android_asset/" + filename;
    }


    public static String readRawStrById(@RawRes int id) {
        return readRawStrById(id, "UTF-8");
    }

    public static String readRawStrById(@RawRes int id, String encoding) {
        Charset charset = Charset.forName(encoding);
        return readRawStrById(id, charset);
    }


    public static String readRawStrById(@RawRes int id, Charset charset) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;

        try {
            inputReader = new InputStreamReader(GlobalContext.getContext().getResources().openRawResource(id));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();

            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }

            text = new String(buffer.toString().getBytes(), charset);
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }

                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

        }
        return text;
    }


    public static InputStream readRasById(@RawRes int id) {
        return GlobalContext.getContext().getResources().openRawResource(id);
    }


    public static int getResourceId(String name, String defType, String defPackage) {
        return GlobalContext.getContext().getResources().getIdentifier(name, defType, defPackage);
    }


    public static int getResourceId(String name, String defType) {
        return getResourceId(name, defType, AppUtil.getPackageName());
    }


    public static int getLayoutResId(String paramString, String defPackage) {

        return getResourceId(paramString, "layout", defPackage);
    }

    public static int getStringResId(String paramString, String defPackage) {

        return getResourceId(paramString, "string", defPackage);
    }

    public static int getDrawableResId(String paramString, String defPackage) {

        return getResourceId(paramString, "drawable", defPackage);
    }

    public static int getStyleResId(String paramString, String defPackage) {

        return getResourceId(paramString, "style", defPackage);
    }

    public static int getResId(String paramString, String defPackage) {

        return getResourceId(paramString, "id", defPackage);
    }

    public static int getColorResId(String paramString, String defPackage) {

        return getResourceId(paramString, "color", defPackage);
    }

    public static int getDimenResId(String paramString, String defPackage) {

        return getResourceId(paramString, "dimen", defPackage);
    }

    public static int getAnimResId(String paramString, String defPackage) {

        return getResourceId(paramString, "anim", defPackage);
    }


    public static int getArrayResId(String paramString, String defPackage) {

        return getResourceId(paramString, "array", defPackage);
    }


    public static int getLayoutResId(String paramString) {

        return getLayoutResId(paramString, AppUtil.getPackageName());
    }

    public static int getStringResId(String paramString) {

        return getStringResId(paramString, AppUtil.getPackageName());
    }

    public static int getDrawableResId(String paramString) {

        return getDrawableResId(paramString, AppUtil.getPackageName());
    }

    public static int getStyleResId(String paramString) {

        return getStyleResId(paramString, AppUtil.getPackageName());
    }

    public static int getResId(String paramString) {

        return getResId(paramString, AppUtil.getPackageName());
    }

    public static int getColorResId(String paramString) {

        return getColorResId(paramString, AppUtil.getPackageName());
    }

    public static int getDimenResId(String paramString) {

        return getDimenResId(paramString, AppUtil.getPackageName());
    }

    public static int getAnimResId(String paramString) {

        return getAnimResId(paramString, AppUtil.getPackageName());
    }


    public static int getArrayResId(String paramString) {
        return getArrayResId(paramString, AppUtil.getPackageName());
    }


}
