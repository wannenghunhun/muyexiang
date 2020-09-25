package com.framwork.common.helper;

import android.app.Application;

import com.framwork.common.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


/**
 *
 */
public class HotFixHelper {
    private static final String BUGLY_ID = "0ef9d5f714";
    
    public static void init(Application application) {
//        Bugly.setAppChannel(application, ChannelUtils.getChannel(application));
        Bugly.init(application, BUGLY_ID, false);
        Bugly.setIsDevelopmentDevice(application, isDevelopmentDevice());
    }
    
    
    /**
     * 是否标记为开发测试设备
     *
     * @return true 标记
     */
    private static boolean isDevelopmentDevice() {
        return BuildConfig.DEBUG;
    }
    
    
    public static void install(Application application) {
        if(BuildConfig.DEBUG) {
            // Debug 包不使用热修复
            return;
        }
        Beta.installTinker();
    }
    
}
