package com.framwork.main.router.navigation;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.router.util.RouterHandler;

/**
 *
 * 跳转回调，主要用于当拦截以后的操作
 */
public class DLRNavigationCallback implements NavigationCallback {


    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {
        RouterHandler.onLost(null, postcard);
    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        RouterHandler.debug("拦截--" + postcard.getPath() + "");
        int extra = postcard.getExtra();
        switch (extra) {
            case RouterConstants.Extras.LOGIN_NEEDED:
                login(postcard);
                break;
            default:
                RouterHandler.error("noting to do ? %s", postcard.getPath());
                break;
        }

    }

    // 登录
    private void login(Postcard postcard) {
        RouterHandler.warning("拦截--" + postcard.getPath() + "");
        String path = postcard.getPath();
        Bundle bundle = postcard.getExtras();
//        ARouter.getInstance().build(RouterConstants.ROUTER_LOGIN)
//                .with(bundle)// 将原来需要传递的参数通过登录界面传递下去
//                .withString(RouterConstants.ROUTER_PATH_KEY, path).navigation()
//        ;//去登陆
    }

    // 手势密码设置
    private void setGesturesPwd(Postcard postcard) {

    }
}
