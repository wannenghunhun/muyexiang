package com.framwork.common.utils;

/**
 *
 */
public class DeviceUtil {
    
    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }
    
    
    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    
    /**
     * 获取当前手机系统名称
     *
     * @return 系统版本号
     */
    public static String getSystemName() {
        return "Android";
    }
    
}
