package com.framwork.main.router.executor;

import android.content.Context;

import com.framwork.common.utils.ActivityManager;
import com.framwork.common.utils.SysIntentUtil;

import java.util.Map;

/**
 *
 */
public class CallPhoneRouter implements IAppRouter {

    @Override
    public void navigation(String url, Map<String, String> params) {
        navigation(ActivityManager.current(), url, params);
    }

    @Override
    public void navigation(Context context, String url, Map<String, String> params) {
        String number = params.get("number");
//        SysIntentUtil.callTel(context, number);
        SysIntentUtil.openTel(context, number);
    }
}
