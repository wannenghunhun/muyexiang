package com.framwork.okhttputils.request;

import com.framwork.okhttputils.OkHttpUtils;
import com.framwork.okhttputils.callback.Callback;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFormRequest extends OkHttpRequest {
    
    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        super(url, tag, params, headers);
    }
    
    @Override
    protected RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        for(String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        return builder.build();
    }
    
    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if(callback == null)
            return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {
                
                OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten, contentLength);
                    }
                });
                
            }
        });
        return countingRequestBody;
    }
    
    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
    
}
