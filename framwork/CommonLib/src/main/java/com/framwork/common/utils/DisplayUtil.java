package com.framwork.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * dp、sp 转换为 px 的工具类
 * <p>
 * Created by sunfusheng on 16/5/31.
 */
public class DisplayUtil {
    private static DisplayMetrics dm = null;
    
    
    public static int getScreenWidth() {
        return getScreenWidthInPx();
    }
    
    public static int getScreenHeight() {
        return getScreenHeightInPx();
    }
    
    
    static {
        dm = ResUtil.getResources().getDisplayMetrics();
    }
    
    
    /**
     * 获得设备屏幕密度
     */
    public static float getScreenDensity() {
        DisplayMetrics metrics = ResUtil.getResources()
                .getDisplayMetrics();
        return metrics.density;
    }
    
    
    public static DisplayMetrics getDisplayMetrics() {
        return dm;
    }
    
    public static int px2dp(int px) {
        
        final float density = dm.density;
        return (int) (px / density + 0.5f);
    }
    
    public static int dp2px(float dp) {
        
        final float density = dm.density;
        return (int) (dp * density + 0.5f);
    }
    
    public static int px2sp(float px) {
        
        
        return (int) (px / dm.scaledDensity + 0.5f);
    }
    
    public static int sp2px(float sp) {
        
        return (int) (sp * dm.scaledDensity + 0.5f);
    }
    
    
    public static int getScreenWidthInPx() {
        
        return dm.widthPixels;
    }
    
    public static int getScreenHeightInPx() {
        
        return dm.heightPixels;
    }
    
    public static int getScreenWidthInDp() {
        
        return (int) ((float) dm.widthPixels * (160 / dm.xdpi));
    }
    
    public static int getScreenHeightInDp() {
        
        int screenHeight = dm.heightPixels;
        return (int) ((float) screenHeight / dm.density);
    }
    
    public static float getDensity() {
        
        return dm.density;
    }
    
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = ResUtil.getDimenResId("status_bar_height", "android");
        if(resourceId > 0) {
            result = ResUtil.getDimen(resourceId);
        }
        return result;
    }
    
    /**
     * 存储屏幕高宽的数据
     */
    private static int[] screenSize = null;
    
    /**
     * 获取屏幕高宽
     *
     * @param context
     * @return 屏幕宽高的数据[0]宽， [1]高
     * @Description:
     */
    public static int[] getScreenSize(Context context) {
        if(screenSize == null) {
            //            Display display = activity.getWindowManager().getDefaultDisplay();
            
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            screenSize = new int[2];
            screenSize[0] = display.getWidth();
            screenSize[1] = display.getHeight();
        }
        return screenSize;
    }
}