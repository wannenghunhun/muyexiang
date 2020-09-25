package com.framwork.common.utils;

import android.os.Handler;

import com.framwork.common.R;
import com.framwork.common.helper.net.ReleaseServerConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class GlobalTools {
    
    private static String IV = "csc-api-iv-param";
    private static String WAY = "AES/CBC/PKCS5Padding";
    private static String KEY = null;
    
    
    // 日志打印
    public static void installLog(boolean showLog) {
        LogUtil.setShowLog(showLog);
        if(showLog) {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(3)         // (Optional) How many method line to show. Default 2
                    .methodOffset(1)        // (Optional) Hides internal method calls up to offset. Default 5
                    .tag("ZW--->")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build();
            
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return true;
                }
            });
        }
        
    }
    
    
    // 校验apk签名
    public static void checkApkSign() {
        boolean right = CheckApk.checkSign(ResUtil.getString(R.string.zwlcs_info_key));
        if(right) {
            return;
        }
        ToastUtil.showToast("安装包签名异常，程序即将退出");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }, 3000);
    }
    
    public synchronized static String encryptHttp(String content) { //加密接口数据
        return AES.getHttpAes(ReleaseServerConfig.AES_KEY, IV, WAY).encryptAES(content);
    }
    
    public synchronized static String decryptHttp(String content) { //解密接口数据
        return AES.getHttpAes(ReleaseServerConfig.AES_KEY, IV, WAY).decryptAES(content);
    }
    
    public static String encryptWeb(String content) { //加密H5数据
        return AES.getWebAes(ReleaseServerConfig.AES_KEY, IV, WAY).encryptAES(content);
    }
    
    public static String decryptWeb(String content) { //解密H5数据
        return AES.getWebAes(ReleaseServerConfig.AES_KEY, IV, WAY).decryptAES(content);
    }
    
}
