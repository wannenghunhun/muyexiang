package com.framwork.main.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.router.util.RouterHandler;

/**
 *
 * 全局降级策略，当无 patch 匹配时
 */
public class DegradeServiceImpl implements DegradeService {
    
    @Override
    public void onLost(Context context, Postcard postcard) {
        RouterHandler.onLost(context, postcard);
    }
    
    @Override
    public void init(Context context) {
        RouterHandler.debug("降级策略初始化");
    }
}
