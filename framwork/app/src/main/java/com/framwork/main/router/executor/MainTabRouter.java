package com.framwork.main.router.executor;

import android.content.Context;

import com.framwork.common.utils.ActivityManager;

import java.util.Map;

/**
 *
 */
public class MainTabRouter implements IAppRouter {
    
    @Override
    public void navigation(String url, Map<String, String> params) {
        navigation(ActivityManager.current(), url, params);
    }
    
    @Override
    public void navigation(Context context, String url, Map<String, String> params) {
//        switch(url) {
//            case RouterConstants.ROUTER_HOMEPAGE_KEY:
//                MainActivity.start(MainActivity.MAIN_HOME);
//                break;
//            case RouterConstants.ROUTER_WORKPAGE_KEY:
//                if(LoginUtil.isLogin()){
//
//                    MainActivity.start(MainActivity.MAIN_WORK);
//                }
//                else{
//                    ARouter.getInstance().build(RouterConstants.ROUTER_LOGIN_KEY).navigation();
//                }
//                break;
//            case RouterConstants.ROUTER_MINEPAGE_KEY:
//                if(LoginUtil.isLogin()){
//
//                    MainActivity.start(MainActivity.MAIN_MINE);
//                }
//                else{
//                    ARouter.getInstance().build(RouterConstants.ROUTER_LOGIN_KEY).navigation();
//                }
//                break;
//            case RouterConstants.ROUTER_RECOMMENDPRJ_KEY:
//                if(LoginUtil.isLogin()){
//
//                    MainActivity.start(MainActivity.MAIN_PROJECT);
//                }
//                else{
//                    ARouter.getInstance().build(RouterConstants.ROUTER_LOGIN_KEY).navigation();
//                }
//                break;
//
//        }
    }
}
