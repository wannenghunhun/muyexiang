package com.framwork.main.ui.presenter;

import com.framwork.common.utils.ToastUtil;
import com.framwork.main.GlobalConstants;
import com.framwork.main.bean.LoginBean;
import com.framwork.main.http.GsonHttpCallback;
import com.framwork.main.http.RestClient;
import com.framwork.main.http.ResultBean;
import com.framwork.main.ui.contract.LoginContract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 */
public class LoginPresenter extends LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }
    
    @Override
    public void goLogin(String account, String password) {
        
        Map<String, String> params = new HashMap<>();
        params.put("name", account);
        params.put("password", password);
        RestClient.postWithForm(GlobalConstants.InterfaceNameConstants.LOGIN, params, new GsonHttpCallback<LoginBean>() {
            @Override
            public void onBefore(Request request) {
                if(isViewAttached()) {
                    getView().showLoading();
                }
            }
            
            @Override
            public void onAfter() {
                if(isViewAttached()) {
                    getView().dissLoading();
                }
            }
            
            @Override
            protected void error(ResultBean<LoginBean> t) {
                ToastUtil.showToast(t.msg);
            }
            
            @Override
            protected void response(ResultBean<LoginBean> t) {
                if(isViewAttached()) {
                    getView().logSuccess(t.data);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<LoginBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
}
