package com.framwork.main.router.util;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.router.executor.CallPhoneRouter;
import com.framwork.main.router.executor.IAppRouter;
import com.framwork.main.router.executor.MainTabRouter;
import com.framwork.common.utils.LogUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * 格式必须固定
 * {"route":["oneproDetails","{\"productCode\":\"DLT181106008601\"}}  ---- url 字符串
 * 或者   {"route":["oneproDetails",{"productCode":"DLT181106008601"}} ----url 对象
 */
public class RouterParseUtil {
    
    
    private static Map<String, IAppRouter> routers = new HashMap<>();
    
    static {
//        routers.put(RouterConstants.ROUTER_CALLSERVICE_KEY, new CallPhoneRouter());
//        routers.put(RouterConstants.ROUTER_HOMEPAGE_KEY, new MainTabRouter());
//        routers.put(RouterConstants.ROUTER_WORKPAGE_KEY, new MainTabRouter());
//        routers.put(RouterConstants.ROUTER_MINEPAGE_KEY, new MainTabRouter());
//        routers.put(RouterConstants.ROUTER_RECOMMENDPRJ_KEY, new MainTabRouter());
    }
    
    public static void navigation(JsonArray jsonElements) {
        navigation(null, jsonElements);
    }
    
    /**
     * Navigation to the route with path in postcard.
     *
     * @param context Activity and so on.
     */
    public static void navigation(Context context, JsonArray jsonElements) {
        navigation(context, jsonElements, null);
    }
    
    /**
     * Navigation to the route with path in postcard.
     *
     * @param context Activity and so on.
     */
    public static void navigation(Context context, JsonArray jsonElements, NavigationCallback callback) {
        if(jsonElements == null) {
            return;
        }
        if(!isRouteValid(jsonElements)) {
            LogUtil.e("router is invalid %s", jsonElements.toString());
            return;
        }
        try {
            String url = getUrl(jsonElements);
            Map<String, String> params = getParams(jsonElements);
            LogUtil.d("router url : %s\nrouter params: %s", url, params.toString());
            if(isIntercepted(context, url, params)) { // 拦截自行处理，不依靠 ARouter 处理
                return;
            }
            Postcard postcard = buildPostCard(url, params);
            if(context != null && callback != null) {
                postcard.navigation(context, callback);
                return;
            }
            if(context != null) {
                postcard.navigation(context);
                return;
            }
            postcard.navigation();
        } catch(Exception e) {
            RouterHandler.printException(e);
        }
        
    }
    
    public static boolean isRouteValid(JsonArray jsonArray) {
        if(jsonArray == null) {
            return false;
        }
        if(jsonArray.size() == 0) {
            return false;
        }
        JsonElement firstElement = jsonArray.get(0);
        return isString(firstElement);
    }
    
    public static boolean isString(JsonElement element) {
        if(element == null) {
            return false;
        }
        boolean isJsonPrimitive = element.isJsonPrimitive();// 是否是基本类型
        if(isJsonPrimitive) {
            JsonPrimitive jsonPrimitive = (JsonPrimitive) element;
            if(jsonPrimitive.isString()) {   // 是否是字符串
                String string = jsonPrimitive.getAsString();
                return !TextUtils.isEmpty(string);
            }
        }
        return false;
    }
    
    
    public static boolean isObject(JsonElement element) {
        return element.isJsonObject();
    }
    
    public static boolean isIntercepted(Context context, String url, Map<String, String> params) {
        IAppRouter router = routers.get(url);
        if(router != null) {
            if(context == null) {
                router.navigation(url, params);
            }
            else {
                router.navigation(context, url, params);
            }
            
            return true;
        }
        return false;
    }
    
    
    public static String getUrl(JsonArray jsonElements) {
        return jsonElements.get(0).getAsString();
    }
    
    public static Map<String, String> getParams(JsonArray jsonElements) {
        Map<String, String> result = new HashMap<>();
        if(jsonElements.size() == 1) {
            return result;
        }
        JsonElement paramsElement = jsonElements.get(1);
        if(isObject(paramsElement)) {
            JsonObject object = paramsElement.getAsJsonObject();
            Set<String> paramsSet = object.keySet();
            Iterator<String> paramsIt = paramsSet.iterator();
            while(paramsIt.hasNext()) {
                String key = paramsIt.next();
                String value = object.get(key).getAsString();
                result.put(key, value);
            }
        }
        else if(isString(paramsElement)) {
            String str = paramsElement.getAsString();
            if(str != null && str.startsWith("\"")) {
                str = str.replaceAll("\\\\\"", "\"");
                str = str.substring(1, str.length() - 1);
            }
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(str);
                Iterator<String> paramsSet = jsonObject.keys();
                while(paramsSet.hasNext()) {
                    String key = paramsSet.next();
                    String value = jsonObject.getString(key);
                    result.put(key, value);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    
    public static Postcard buildPostCard(JsonArray jsonElements) {
        return buildPostCard(getUrl(jsonElements), getParams(jsonElements));
    }
    
    
    public static Postcard buildPostCard(String url, Map<String, String> params) {
        Postcard postcard = ARouter.getInstance()
                .build(url);
        Set<String> keySet = params.keySet();
        Iterator<String> it = keySet.iterator();
        while(it.hasNext()) {
            String key = it.next();
            String value = params.get(key);
            postcard.withString(key, value);
        }
        return postcard;
    }
    
    
}
