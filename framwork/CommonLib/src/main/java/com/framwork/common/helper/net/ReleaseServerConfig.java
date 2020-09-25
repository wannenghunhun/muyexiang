package com.framwork.common.helper.net;

import android.content.Context;

import com.framwork.common.helper.ServerHelper;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 */
public class ReleaseServerConfig implements IServerConfig {
    
    
    @Override
    public void configServer(String serverType) {
        // Nothing to do
        ServerHelper.currentServerType = serverType;
        initServer();
    }
    
    @Override
    public void startConfig(Context context) {
        // Nothing to do
    }
    
    
    @Override
    public Map<String, String> headers(String serverType, String url, String interfaceName) {
        switch(serverType) {
            case ServerHelper.ServerReleaseType:
                return new IdentityHashMap<>();  // empty
            case ServerHelper.ServerPreType:
                return new IdentityHashMap<>();  // empty
            case ServerHelper.ServerTestType:
                return new IdentityHashMap<>();  // empty
        }
        return new IdentityHashMap<>();  // empty
    }
    
    public static String SERVER_API;
    public static String AES_KEY;
    
    public static void initServer() {
        switch(ServerHelper.currentServerType) {
            case ServerHelper.ServerTestType:
                SERVER_API = "http://10.100.19.167/csc-api";
                AES_KEY = "testKey123456789";
                break;
            case ServerHelper.ServerPreType:
                SERVER_API = "https://api-pre.duolerong.com/csc-api";
                AES_KEY = "PreSx38cAi%^2^*b";
                break;
            
            case ServerHelper.ServerReleaseType:
                SERVER_API = "https://api.hengchangcaifu.com/csc-api";
                AES_KEY = "ProdS3x8sB%X7s1@";
                break;
        }
    }
    
}
