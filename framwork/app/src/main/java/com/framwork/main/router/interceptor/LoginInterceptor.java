package com.framwork.main.router.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.framwork.main.router.util.RouterHandler;

/**
 * 路由登录拦截器
 */
public class LoginInterceptor implements IInterceptor {
    
    
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        //        if(LoginUtil.isLogin()) { // 已登录，全部通过
        //            callback.onContinue(postcard);
        //        }
        //        else {
        //            if(postcard.getExtra() == RouterConstants.Extras.LOGIN_NEEDED) { // 需要登录
        //
        //                RouterHandler.warning("登录校验未通过，需要登录");
        //                callback.onInterrupt(null);
        //            }
        //            else {
        //                callback.onContinue(postcard);
        //            }
        //        }
    }
    
    @Override
    public void init(Context context) {
        RouterHandler.debug("路由登录拦截器初始化");
    }
}
