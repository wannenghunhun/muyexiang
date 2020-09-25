package com.framwork.main.http.interceptor;


import com.framwork.common.helper.ServerHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.ByteString;

/**
 *
 * 对请求参数进行加密
 */
public class RequestEncryptInterceptor implements Interceptor {
    
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        
        Request req = chain.request();
        RequestBody reqBody = req.body();
        String reqMethod = req.method();
        String url = req.url().toString();
        Charset charset = Charset.forName("UTF-8");//字符集
        if("post".equalsIgnoreCase(reqMethod)) { // post 请求
            //            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //            BufferedSink sink = Okio.buffer(Okio.sink(byteArrayOutputStream));
            //            Buffer buffer = sink.getBuffer();
            Buffer buffer = new Buffer();
            reqBody.writeTo(buffer);
            ByteString byteString = buffer.readByteString();
            String string = byteString.string(charset);
            
            try {
                JSONObject jsonObject = new JSONObject(string);
                String encryptStr = ServerHelper.encryptReq(url, jsonObject.toString());
                req = req.newBuilder().post(RequestBody.create(reqBody.contentType(), encryptStr)).build();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return chain.proceed(req);
    }
    
    
}
