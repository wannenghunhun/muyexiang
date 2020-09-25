package com.framwork.main;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.framwork.common.helper.ServerHelper;
import com.framwork.common.utils.WebViewUtil;
import com.framwork.main.helper.ACRAHelper;
import com.framwork.main.helper.DebugServerConfig;
import com.framwork.main.helper.RouterDebugHelper;
import com.framwork.main.helper.StethoHelper;
import com.framwork.main.http.interceptor.RequestEncryptInterceptor;
import com.framwork.main.http.interceptor.ResponseDecryptInterceptor;
import com.framwork.main.http.interceptor.ResponseGzipInterceptor;
import com.framwork.main.lifecycle.ActivityLogLifecycle;
import com.framwork.main.lifecycle.ActivityServerConfigLifecycle;
import com.framwork.main.lifecycle.FragmentLogLifecycle;
import com.framwork.okhttputils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 *
 */
public class DebugApplication extends MyApplication {
    
    
    @Override
    protected void initInstall() {
//        RouterDebugHelper.install(this);
        super.initInstall();
//        ACRAHelper.install(this);
        //        LeakCanaryHelper.install(this);
        //        BlockCanaryHelper.install(this);
//        StethoHelper.install(this);
        addActivityLifeCycle(new ActivityLogLifecycle());
//        addActivityLifeCycle(new ActivityServerConfigLifecycle());
        addFragmentLifeCycle(new FragmentLogLifecycle());
        //        addFragmentLifeCycle(new FragmentLeakCanaryLifecycle());
//        WebViewUtil.setWebContentsDebuggingEnabled();
//        ServerHelper.init(ServerHelper.ServerTestType, new DebugServerConfig());
    }
    
    @Override
    protected void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new RequestEncryptInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new ResponseDecryptInterceptor())
                .addNetworkInterceptor(new ResponseGzipInterceptor());
        OkHttpUtils.init(builder, true);
        
    }
}
