package com.framwork.main.router;

import com.framwork.common.utils.StringUtil;

import java.util.HashMap;

/**
 *
 */
public class RouterConstants {
    
    public static final String ROUTER_PATH_KEY = "path";
    
    //Router_Key
    public static final String ROUTER_INVALID_KEY = "nonTransition"; // 无效url 不处理
    public static final String ROUTER_LOGIN_KEY = "login";//登录
    
    //Router_Target
    public static final String ROUTER_INVALID = ""; // 无效url 不处理
    public static final String ROUTER_LOGIN = "/app/LoginActivity";//登录
    
    
    private static final HashMap<String, String> pageMap = new HashMap<String, String>() {{
        put(ROUTER_INVALID_KEY, ROUTER_INVALID); // 无效url 不处理
        put(ROUTER_LOGIN_KEY, ROUTER_LOGIN);
    }};
    
    public static String getRealPath(String originPath) {
        String realPath = pageMap.get(originPath);
        if(StringUtil.isEmpty(realPath)) { // 未找到，
            realPath = originPath;// 原样返回
        }
        return realPath;
    }
    
    public static class Extras {
        public static final int LOGIN_NEEDED = 1;
    }
    
}
