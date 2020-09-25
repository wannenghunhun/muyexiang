package com.framwork.main.ui.presenter;

import com.framwork.common.utils.ToastUtil;
import com.framwork.main.GlobalConstants;
import com.framwork.main.bean.BaseBean;
import com.framwork.main.bean.ConditionsBean;
import com.framwork.main.bean.UserInfoBean;
import com.framwork.main.http.GsonHttpCallback;
import com.framwork.main.http.RestClient;
import com.framwork.main.http.ResultBean;
import com.framwork.main.ui.contract.PersonAddContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 */
public class PersonAddPresenter extends PersonAddContract.Presenter {
    public PersonAddPresenter(PersonAddContract.View view) {
        super(view);
    }
    
    @Override
    public void getConditionsInfo(String projectId) {
        Map<String, String> params = new HashMap<>();
        RestClient.postWithParam(GlobalConstants.InterfaceNameConstants.PROJECT, params,
                projectId + GlobalConstants.InterfaceNameConstants.CONDITIONS, new GsonHttpCallback<ConditionsBean>() {
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
                    protected void error(ResultBean<ConditionsBean> t) {
                        ToastUtil.showToast(t.msg);
                    }
                    
                    @Override
                    protected void response(ResultBean<ConditionsBean> t) {
                        if(isViewAttached()) {
                            getView().setConditionsInfo(t.data);
                        }
                    }
                    
                    @Override
                    protected void onNetFail(ResultBean<ConditionsBean> t) {
                        ToastUtil.showNetError();
                    }
                });
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
                    getView().setProjectInfo(t.data);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<UserInfoBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
    
    
    @Override
    public void addEmployeesInfo(String id,
                                 String idPhotoPath,
                                 String photoPath,
                                 String name,
                                 String idCard,
                                 String sex,
                                 String birthday,
                                 String nation,
                                 String address,
                                 String tel,
                                 String politicalStatus,
                                 String nativePlace,
                                 String education,
                                 String ifForeman,
                                 String ifTrain,
                                 String ifCertificate,
                                 String projectId,
                                 String unitId,
                                 String teamId,
                                 String roleType,
                                 String workType,
                                 String age,
                                 String certificateSn,
                                 String contractSn) {
        JSONObject params = new JSONObject();
        try {
            params.put("id", id);
            params.put("idPhoto", idPhotoPath);
            params.put("photo", photoPath);
            params.put("idCard", idCard);
            params.put("name", name);
            params.put("sex", sex);
            params.put("tel", tel);
            params.put("birthday", birthday);
            params.put("nation", nation);
            params.put("address", address);
            params.put("politicalStatus", politicalStatus);
            params.put("nativePlace", nativePlace);
            params.put("education", education);
            params.put("ifForeman", ifForeman);
            params.put("ifTrain", ifTrain);
            params.put("ifCertificate", ifCertificate);
            params.put("projectId", projectId);
            params.put("unitId", unitId);
            params.put("teamId", teamId);
            params.put("roleType", roleType);
            params.put("workType", workType);
            params.put("age", age);
            params.put("certificateSn", certificateSn);
            params.put("contractSn", contractSn);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        
        RestClient.postWithTag(GlobalConstants.InterfaceNameConstants.PROJECT_EMPLOYEE_ADD, params, new GsonHttpCallback<BaseBean>() {
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
            protected void error(ResultBean<BaseBean> t) {
                ToastUtil.showToast(t.msg);
            }
            
            @Override
            protected void response(ResultBean<BaseBean> t) {
                if(isViewAttached()) {
                    getView().editResult(t.msg);
                }
            }
            
            @Override
            protected void onNetFail(ResultBean<BaseBean> t) {
                ToastUtil.showNetError();
            }
        });
    }
    
}
