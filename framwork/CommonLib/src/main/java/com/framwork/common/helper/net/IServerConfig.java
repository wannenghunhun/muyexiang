package com.framwork.common.helper.net;

import android.content.Context;

import com.framwork.common.helper.ServerHelper;

import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 *
 */

public interface IServerConfig {
    
    void configServer(@ServerHelper.ServerType String serverType);
    
    void startConfig(Context context);
    
    
    // 解密响应
    default String decryptResp(String url, String respStr) {
//        if(ServerHelper.ServerReleaseType.equals(ServerHelper.currentServerType) // 生产环境解密响应
//
//                || ServerHelper.ServerPreType.equals(ServerHelper.currentServerType) // 预发布环境解密响应
//            //                || ServerHelper.ServerTestType.equals(ServerHelper.currentServerType)//测试环境解密响应
//                ) {
//            return GlobalTools.decryptHttp(respStr);
//        }
        return respStr;
    }
    
    // 加密请求
    default String encryptReq(String url, String reqStr) {
//        if(ServerHelper.ServerReleaseType.equals(ServerHelper.currentServerType) // 生产环境加密请求
//                || ServerHelper.ServerPreType.equals(ServerHelper.currentServerType) // 预发布环境加密请求
//            //                || ServerHelper.ServerTestType.equals(ServerHelper.currentServerType)//测试环境加密请求
//                ) {
//            return GlobalTools.encryptHttp(reqStr);
//        }
        return reqStr;
    }
    
    
    default Map<String, String> headers(String serverType, String url, String interfaceName) {
        return new IdentityHashMap<>();  // empty
    }
    
    
    default void printLog(Request request, String result) {
    }
}