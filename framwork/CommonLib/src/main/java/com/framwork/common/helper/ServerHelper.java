package com.framwork.common.helper;

import android.content.Context;
import android.support.annotation.StringDef;

import com.framwork.common.helper.net.IServerConfig;
import com.framwork.common.helper.net.ReleaseServerConfig;
import com.framwork.common.utils.ExceptionUtil;

import java.lang.annotation.Retention;
import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.Request;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 *
 */
public class ServerHelper {
    
    public static final String ServerTestType = "测试"; //测试环境
    public static final String ServerPreType = "预发布";  // 预发布环境
    public static final String ServerReleaseType = "生产"; // 生产环境
    
    public static String currentServerType;
    
    public ServerHelper() {
        throw new UnsupportedOperationException("ServerHelper类不支持创建实例！");
    }
    
    @Retention(SOURCE)
    @StringDef({ServerTestType, ServerPreType, ServerReleaseType})
    public @interface ServerType {
    
    }
    
    private static IServerConfig mServerConfig = new ReleaseServerConfig();
    
    public static void init(@ServerType String serverType, IServerConfig serverConfig) {
        ExceptionUtil.checkNotNUll(serverConfig, "serverConfig can't be NULL!!");
        mServerConfig = serverConfig;
        config(serverType);
    }
    
    
    public static void initRelease() {
        init(ServerHelper.ServerReleaseType, new ReleaseServerConfig());
    }
    
    
    public static void config(@ServerType String serverType) {
        if(mServerConfig != null) {
            mServerConfig.configServer(serverType);
        }
    }
    
    
    public static void startConfig(Context context) {
        if(mServerConfig != null) {
            mServerConfig.startConfig(context);
        }
    }
    
    public static String decryptResp(String url, String respStr) {
        return mServerConfig.decryptResp(url, respStr);
    }
    
    public static String encryptReq(String url, String reqStr) {
        return mServerConfig.encryptReq(url, reqStr);
    }
    
    
    private static Map<String, String> commonHeaders() {
        Map<String, String> headers = new IdentityHashMap<>();
        //        headers.put("Content-Type", "text/plain");
        //        headers.put("device", "android");
//                headers.put("token", LoginUtil.getToken());
        //        headers.put("deviceVer", DeviceUtil.getDeviceType() + "[" + DeviceUtil.getSystemVersion() + "]");
        //        headers.put("appName", "zw");
        //        headers.put("platform", "app");
        //        headers.put("appMarket", ChannelUtils.getChannel());
        //        headers.put("appVer", AppUtil.getVersionName());
        return headers;
    }
    
    
    public static Map<String, String> headers(String url, String interfaceName) {
        Map<String, String> commonHeaders = commonHeaders();
        //        Map<String, String> configHeaders = mServerConfig.headers(ServerHelper.currentServerType, url, interfaceName);
        //        commonHeaders.putAll(configHeaders);
        return commonHeaders;
    }
    
    
    public static void printLog(Request request, String result) {
        mServerConfig.printLog(request, result);
    }
}
