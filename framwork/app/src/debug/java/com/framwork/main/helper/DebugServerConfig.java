package com.framwork.main.helper;

import android.content.Context;

import com.framwork.common.helper.ServerHelper;
import com.framwork.common.helper.net.IServerConfig;
import com.framwork.common.helper.net.ReleaseServerConfig;
import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.utils.StringUtil;

import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 *
 */
public class DebugServerConfig implements IServerConfig {


    public static final String INTERFACE_NAME = "INTERFACE_NAME";

    public static final String SERVER_TYPE = "ServerType";

    @Override
    public void configServer(@ServerHelper.ServerType String serverType) {
        String preServerType = SPManager.getManager(DebugServerConfig.SERVER_TYPE).getString(SERVER_TYPE);
        if (StringUtil.isEmpty(preServerType)) {
            ServerHelper.currentServerType = serverType;
            SPManager.getManager(DebugServerConfig.SERVER_TYPE).commitString(SERVER_TYPE, serverType);
        } else {
            ServerHelper.currentServerType = preServerType;
        }
        ReleaseServerConfig.initServer();
    }

    @Override
    public void startConfig(Context context) {
//        ServerSwitchActivity.launch(context);
    }


    @Override
    public void printLog(Request request, String result) {
        try {
            if ("post".equalsIgnoreCase(request.method())) {
                String interfaceName = request.header(INTERFACE_NAME);
                LogUtil.e("--->请求URL: %s \n--->请求接口名: %s \n--->返回数据:-- %s", request.url().toString(), interfaceName, result);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public Map<String, String> headers(String serverType, String url, String interfaceName) {
        Map<String, String> headers = new IdentityHashMap<>();
        switch (serverType) {
            case ServerHelper.ServerReleaseType:
                headers.put(INTERFACE_NAME, interfaceName);
                break;
            case ServerHelper.ServerPreType:
                headers.put(INTERFACE_NAME, interfaceName);
                break;
            case ServerHelper.ServerTestType:
                headers.put(INTERFACE_NAME, interfaceName);
                break;
        }
        return headers;  // empty
    }
}
