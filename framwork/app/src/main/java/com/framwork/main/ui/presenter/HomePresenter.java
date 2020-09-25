package com.framwork.main.ui.presenter;

import com.framwork.common.utils.ToastUtil;
import com.framwork.main.GlobalConstants;
import com.framwork.main.bean.EmployeesBean;
import com.framwork.main.bean.ProjectInfoBean;
import com.framwork.main.bean.UserInfoBean;
import com.framwork.main.http.GsonHttpCallback;
import com.framwork.main.http.RestClient;
import com.framwork.main.http.ResultBean;
import com.framwork.main.ui.contract.HomeContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 */
public class HomePresenter extends HomeContract.Presenter {
    public HomePresenter(HomeContract.View view) {
        super(view);
    }
    
    @Override
    public void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        RestClient.postWithForm(GlobalConstants.InterfaceNameConstants.USER_INFO, params, new GsonHttpCallback<UserInfoBean>() {
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
            protected void error(ResultBean<UserInfoBean> t) {
                ToastUtil.showToast(t.msg);
            }
            
            @Override
            protected void response(ResultBean<UserInfoBean> t) {
                if(isViewAttached()) {
                    getView().setUserInfo(t.data);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<UserInfoBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
    
    @Override
    public void getProjectInfo(String id) {
        Map<String, String> params = new HashMap<>();
        RestClient.postWithParam(GlobalConstants.InterfaceNameConstants.PROJECT, params, id, new GsonHttpCallback<ProjectInfoBean>() {
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
            protected void error(ResultBean<ProjectInfoBean> t) {
                ToastUtil.showToast(t.msg);
            }
            
            @Override
            protected void response(ResultBean<ProjectInfoBean> t) {
                if(isViewAttached()) {
                    getView().setProjectInfo(t.data);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<ProjectInfoBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
}
