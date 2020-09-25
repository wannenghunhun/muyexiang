package com.framwork.main.http.interceptor;


import com.framwork.common.helper.ServerHelper;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * 响应解密拦截器
 */
public class ResponseDecryptInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String contentType = response.header("Content-Type");
        if (contentType == null) {
            // impossible but fix it
            return response;
        }
        if (!(contentType.contains("text") || contentType.contains("json"))) { // 非文本的不考虑
            return response;
        }

        if (response.code() != 200) { // 响应码不是200 不考虑
            return response;
        }
        ResponseBody responseBody = response.body();
        String url = request.url().toString();
        String originRespData = responseBody.string();
        String respData = ServerHelper.decryptResp(url, originRespData);

        Headers.Builder headersBuilder = response.headers().newBuilder()
                .removeAll("Content-Encoding")
                .removeAll("Content-Length")
                .removeAll("Content-Type");

        if (respData == null || respData.length() == 0) {
            // 解密失败，一般是服务器响应数据错误导致，不处理,返回原来的数据
            respData = originRespData;
            headersBuilder.add("Content-Type", "text/html");
        } else {
            headersBuilder.add("Content-Type", "application/json;charset=UTF-8");
        }
        return response.newBuilder()
                .body(ResponseBody.create(null, respData))
                .headers(headersBuilder.build())
                .build();
    }

}
