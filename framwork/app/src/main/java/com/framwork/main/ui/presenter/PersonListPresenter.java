package com.framwork.main.ui.presenter;

import com.framwork.common.utils.ToastUtil;
import com.framwork.main.GlobalConstants;
import com.framwork.main.bean.EmployeesBean;
import com.framwork.main.http.GsonHttpCallback;
import com.framwork.main.http.RestClient;
import com.framwork.main.http.ResultBean;
import com.framwork.main.ui.contract.PersonListContract;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

/**
 */
public class PersonListPresenter extends PersonListContract.Presenter {
    public PersonListPresenter(PersonListContract.View view) {
        super(view);
    }
    
    @Override
    public void getEmployeesInfo(String key, int keyType, String projectId, int status, int pageNum, int pageSize, boolean isRefresh) {
        //关键字类型 0-名称 1-单位 2-班组
        //订单状态：0-离岗 1-在岗 默认在岗
        JSONObject params = new JSONObject();
        try {
            params.put("key", key);
            params.put("keyType", keyType);
            params.put("pageNum", pageNum);
            params.put("pageSize", pageSize);
            params.put("projectId", projectId);
            params.put("status", status);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        
        RestClient.postWithTag(GlobalConstants.InterfaceNameConstants.PROJECT_EMPLOYEES, params, new GsonHttpCallback<EmployeesBean>() {
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
            protected void error(ResultBean<EmployeesBean> t) {
                ToastUtil.showToast(t.msg);
            }
            
            @Override
            protected void response(ResultBean<EmployeesBean> t) {
                if(isViewAttached()) {
                    getView().setViews(t.data, isRefresh);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<EmployeesBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
}
