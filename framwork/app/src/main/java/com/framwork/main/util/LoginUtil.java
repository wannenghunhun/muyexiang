package com.framwork.main.util;

import com.alibaba.android.arouter.launcher.ARouter;
import com.framwork.common.utils.ActivityManager;
import com.framwork.common.utils.SPManager;
import com.framwork.common.utils.StringUtil;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.ui.activity.LoginActivity;

/**
 */
public class LoginUtil {
    
    public static final String TOKEN = "token";
    
    public static boolean isLogin() {
        return StringUtil.isNotEmpty(getToken());
    }
    
    /**
     * 退出登录，清空当前用户数据
     */
    public static void gologOut() {
        clearUserData();
        goLogin();
//        ActivityManager.retain(LoginActivity.class);
        // DialogManager.getInstance().clearDialog();
        // EventBus.getDefault().post(new LoginEvent.LoginOff());
    }
    
    
    public static void goLogin() {
        // 发起登录
        ARouter.getInstance().build(RouterConstants.ROUTER_LOGIN_KEY).navigation();
    }
    
    /**
     * 获取用户token
     *
     * @return
     */
    public static String getToken() {
        //获取token
        return SPManager.getDefaultManager().getString(TOKEN, "");
    }
    
    public static void saveToken(String token) {
        SPManager.getDefaultManager().commitString(TOKEN, token);
        //        EventBus.getDefault().post(new LoginEvent.LoginOn());
    }
    
    
    public static void clearUserData() {
        SPManager.getDefaultManager().clear();
    }
}
