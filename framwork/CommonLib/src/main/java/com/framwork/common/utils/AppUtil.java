package com.framwork.common.utils;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.framwork.common.GlobalContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 */


public final class AppUtil {
    
    public final static String SHA1 = "SHA1";
    
    
    public static PackageInfo getPackageInfo() {
        return getPackageInfo(getPackageName());
    }
    
    public static PackageInfo getPackageInfo(String _packageName) {
        try {
            return getPackageManager().getPackageInfo(_packageName, 0);
        } catch(PackageManager.NameNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }
    
    
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }
    
    public static int getVersionCode() {
        
        return getPackageInfo().versionCode;
    }
    
    public static String getPackageName() {
        return GlobalContext.getContext().getPackageName();
    }
    
    public static ApplicationInfo getApplicationInfo() {
        return GlobalContext.getContext().getApplicationInfo();
    }
    
    
    public static String getAppName() {
        return getApplicationInfo().name;
    }
    
    public static boolean isInstall(String _packageName) {
        return getPackageInfo(_packageName) != null;
    }
    
    public static List<ApplicationInfo> getAllApps() {
        PackageManager pm = getPackageManager();
        if(pm == null) {
            return null;
        }
        else {
            return pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        }
    }
    
    public static void openApp(Context context, String packageName) throws Exception {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }
    
    
    public boolean isRunning(String packageName) {
        
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) GlobalContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if(am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(100);
        for(ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            
            if(runningTaskInfo.topActivity.getPackageName().equals(packageName) || runningTaskInfo.baseActivity.getPackageName().equals(packageName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
    
    
    /**
     * 返回对应包的签名信息
     *
     * @param packageName
     * @return
     */
    @Nullable
    public static Signature[] getSignatures(String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = GlobalContext.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Nullable
    public static Signature[] getSignatures() {
        return getSignatures(getPackageName());
    }
    
    
    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param packageName
     * @param type
     * @return
     */
    @Nullable
    public static String getSingInfo(String packageName, String type) {
        String tmp = null;
        Signature[] signs = getSignatures(packageName);
        if(signs == null) {
            return null;
        }
        for(Signature sig : signs) {
            if(SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
                break;
            }
        }
        return tmp;
    }
    
    
    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param type
     * @return
     */
    @Nullable
    public static String getSingInfo(String type) {
        return getSingInfo(getPackageName(), type);
    }
    
    /**
     * 返回一个签名的对应类型的字符串
     *
     * @return
     */
    @Nullable
    public static String getSingInfo() {
        return getSingInfo(getPackageName(), SHA1);
    }
    
    
    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if(digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for(byte digestByte : digestBytes) {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fingerprint;
    }
    
    /**
     * 多进程环境下，防止多次初始化
     *
     * @return true 初始化， false 不初始化
     */
    public static boolean isInitAble() {
        
        String packageName = getPackageName();
        if(packageName == null) { // impossible
            return true;
        }
        return packageName.equalsIgnoreCase(OSUtils.getProcessName());
    }
    
    
    public static Resources getResources() {
        return GlobalContext.getContext().getResources();
    }
    
    
    public static AssetManager getAssetManager() {
        return GlobalContext.getContext().getAssets();
    }
    
    
    public static PackageManager getPackageManager() {
        return GlobalContext.getContext().getPackageManager();
    }
    
    
    public static ContentResolver getContentResolver() {
        return GlobalContext.getContext().getContentResolver();
    }
    
    public static Looper getMainLooper() {
        return GlobalContext.getContext().getMainLooper();
    }
    
    
    /**
     * 梆梆加固，防劫持检查
     * Android 5.0及以下可用
     */
    public static void checkHijack() {
        try {
            ActivityManager activityManager = OSUtils.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(2);
            if(tasks == null || tasks.isEmpty()) {
                return;
            }
            ActivityManager.RunningTaskInfo info = tasks.get(0);
            if(info == null) {
                return;
            }
            String topPackage = info.topActivity.getPackageName();
            if(!topPackage.equals(AppUtil.getPackageName())) {
                LogUtil.i("ui hijack show...");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // 重启
    public static void restartMySelf() {
        // 重启app
        Intent i = GlobalContext.getContext().getPackageManager().getLaunchIntentForPackage(GlobalContext.getContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        GlobalContext.getContext().startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public static void shutDownApp() {
        DialogManager.getInstance().clearDialog();
        com.framwork.common.utils.ActivityManager.popAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    
}

