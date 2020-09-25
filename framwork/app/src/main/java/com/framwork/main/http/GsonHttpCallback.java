package com.framwork.main.http;

import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.ToastUtil;
import com.framwork.okhttputils.adapter.ParameterizedTypeImpl;
import com.framwork.okhttputils.callback.Callback;
import com.framwork.okhttputils.utils.GsonUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;


/**
 * 描 述: Gson解析网络
 **/
public abstract class GsonHttpCallback<T> extends Callback<ResultBean<T>> {
    private Class<? super T> c;
    
    
    public GsonHttpCallback() {
        c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Override
    public ResultBean<T> parseNetworkResponse(Response response) throws Exception {
        String json;
        json = response.body().string();
        StringBuilder result = new StringBuilder("");
        result.append(json);
        LogUtil.e("返回请求=" + response.request());
        LogUtil.e("返回数据=" + result.toString());
        ResultBean<T> t = null;
        if(c != null) {
            try {
                Type type = new ParameterizedTypeImpl(ResultBean.class, new Class[]{c});
                t = GsonUtil.getGson().fromJson(result.toString(), type);
            } catch(Exception e) {
                t = new ResultBean<>();
                t.code = ResultBean.PARSE_ERROR;
                t.msg = "加载失败,请稍后再试!";
            }
        }
        return t;
    }
    
    @Override
    public void onFailure(Request request, Response response, Exception e) {
        ResultBean t = new ResultBean<>();
        t.msg = "网络异常，请稍后重试";
        t.code = ResultBean.NET_ERROR;
        onNetFail(t);
    }
    
    @Override
    public void onResponse(ResultBean<T> t) {
        if(t == null) {
            t = new ResultBean<>();
            t.msg = "网络异常，请稍后重试";
            t.code = ResultBean.NET_ERROR;
            error(t);
        }
        else {
            if(t.isFailure()) {
                if(t.shouldReLogin()) {
                    //用户强制登出操作
                    onShouldReLogin(t);
                }
                else {
                    error(t);
                }
            }
            else {
                response(t);
            }
        }
    }
    
    protected abstract void error(ResultBean<T> t);
    
    protected void onNetFail(ResultBean<T> t) {
        error(t);
    }
    
    protected void onShouldReLogin(ResultBean<T> t) {
        ToastUtil.showToast(t.msg);
        //        LoginUtil.forceLogOut();
    }
    
    protected abstract void response(ResultBean<T> t);
}
