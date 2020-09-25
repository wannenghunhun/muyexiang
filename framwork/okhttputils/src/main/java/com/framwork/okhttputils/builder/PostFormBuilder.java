package com.framwork.okhttputils.builder;




import com.framwork.okhttputils.request.PostFormRequest;
import com.framwork.okhttputils.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;

public class PostFormBuilder extends OkHttpRequestBuilder {
    
    @Override
    public RequestCall build() {
        return new PostFormRequest(url, tag, params, headers).build();
    }
    
    //
    @Override
    public PostFormBuilder url(String url) {
        this.url = url;
        return this;
    }
    
    @Override
    public PostFormBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }
    
    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }
    
    @Override
    public PostFormBuilder addParams(String key, String val) {
        if(this.params == null) {
            params = new IdentityHashMap<>();
        }
        params.put(key, val);
        return this;
    }
    
    @Override
    public PostFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    
    @Override
    public PostFormBuilder addHeader(String key, String val) {
        if(this.headers == null) {
            headers = new IdentityHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
