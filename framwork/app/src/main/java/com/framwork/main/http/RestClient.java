package com.framwork.main.http;

import com.framwork.common.utils.LogUtil;
import com.framwork.main.GlobalConstants;
import com.framwork.main.util.LoginUtil;
import com.framwork.okhttputils.OkHttpUtils;
import com.framwork.okhttputils.builder.GetBuilder;
import com.framwork.okhttputils.builder.PostFormBuilder;
import com.framwork.okhttputils.builder.PostStringBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.MediaType;


public class RestClient {
    public static final String DATA_KEY = "data";
    
    /**
     * 带tag的post请求
     *
     * @param params
     * @param callback
     */
    public static void postWithTag(String interfaceName, JSONObject params, GsonHttpCallback callback) {
        basePost(GlobalConstants.URLConstants.BASE_URL + interfaceName, params, interfaceName, callback);
    }
    
    /**
     * 表单的post请求
     *
     * @param params
     * @param callback
     */
    public static void postWithForm(String interfaceName, Map<String, String> params, GsonHttpCallback callback) {
        String url = GlobalConstants.URLConstants.BASE_URL + interfaceName;
        PostFormBuilder postFormBuilder = OkHttpUtils.postForm().url(url).params(params).headers(commonHeaders());
        postFormBuilder.build().execute(callback);
        LogUtil.e("--->请求URL: %s \n--->请求接口名: %s \n--->请求数据:-- %s", url, interfaceName, params);
    }
    
    /**
     * 拼接参数的post请求
     *
     * @param params
     * @param callback
     */
    public static void postWithParam(String interfaceName, Map<String, String> params, String param, GsonHttpCallback callback) {
        String url = GlobalConstants.URLConstants.BASE_URL + interfaceName + param;
        PostFormBuilder postFormBuilder = OkHttpUtils.postForm().url(url).params(params).headers(commonHeaders());
        postFormBuilder.build().execute(callback);
        LogUtil.e("--->请求URL: %s \n--->请求接口名: %s \n--->请求数据:-- %s", url, interfaceName, params);
    }
    /**
     * 拼接参数的get请求
     *
     * @param params
     * @param callback
     */
    public static void getWithParam(String interfaceName, Map<String, String> params, String param, GsonHttpCallback callback) {
        String url = GlobalConstants.URLConstants.BASE_URL + interfaceName + param;
        GetBuilder getBuilder =OkHttpUtils.get().url(url).params(params).headers(commonHeaders());
        getBuilder.build().execute(callback);
        LogUtil.e("--->请求URL: %s \n--->请求接口名: %s \n--->请求数据:-- %s", url, interfaceName, params);
    }
    
    
    private static void basePost(String url, JSONObject
            params, String interfaceName, GsonHttpCallback callback) {
        PostStringBuilder postStringBuilder = OkHttpUtils.postString()
                .url(url)
                .mediaType(MediaType.parse("application/json"))
                .headers(commonHeaders())
                .content(params.toString());
        LogUtil.e("--->请求URL: %s \n--->请求接口名: %s \n--->请求数据:-- %s", url, interfaceName, params.toString());
        postStringBuilder.build().execute(callback);
        
    }
    
    
    private static Map<String, String> commonHeaders() {
        Map<String, String> headers = new IdentityHashMap<>();
        headers.put("token", LoginUtil.getToken());
        return headers;
    }
    
    
    /**
     * 根据tag取消请求
     *
     * @param activity
     */
    public static void cancelRequest(Object activity) {
        OkHttpUtils.getInstance().cancelTag(activity);
    }
    
    /**
     * post参数
     *
     * @param jsonObject
     * @return
     */
    public static String getParams(String url, JSONObject jsonObject, String interfaceName) {
        JSONObject param = new JSONObject();
        try {
            if(jsonObject == null) {
                jsonObject = new JSONObject();
            }
            param.put(DATA_KEY, jsonObject);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        //        setCommonParams(param, interfaceName);
        return param.toString();
    }
    
    
}
