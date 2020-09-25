package com.framwork.main.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.router.util.RouterHandler;
import com.framwork.okhttputils.utils.GsonUtil;

import java.lang.reflect.Type;

/**
 *
 * 后台参数--json 解析
 */
public class JsonParseServiceImpl implements SerializationService {
    
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtil.jsonStr2Obj(input, clazz);
    }
    
    @Override
    public String object2Json(Object instance) {
        return GsonUtil.obj2JsonStr(instance);
    }
    
    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtil.jsonStr2Obj(input, clazz);
    }
    
    @Override
    public void init(Context context) {
        RouterHandler.debug("ARouter json 解析服务初始化");
    }
}
