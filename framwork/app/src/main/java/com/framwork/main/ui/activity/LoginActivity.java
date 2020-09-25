package com.framwork.main.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.AppUtil;
import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.widget.ClearAbleEditText;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.bean.LoginBean;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.ui.contract.LoginContract;
import com.framwork.main.ui.presenter.LoginPresenter;
import com.framwork.main.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

@Route(path = RouterConstants.ROUTER_LOGIN, name = RouterConstants.ROUTER_LOGIN_KEY)
public class LoginActivity extends BaseFragmentActivity<LoginContract.Presenter> implements LoginContract.View {
    private TextView login_tv_login, login_tv_setting;
    private ClearAbleEditText loginEtAccount;
    private ClearAbleEditText loginEtPassword;
    private String account, password;
    private static final int PERMISSON_REQUESTCODE = 200;
    private static final int PERMISSON_DYNAMIC_REQUESTCODE = 300;
    
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_LOGS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
    protected String[] dynamicPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appName = AppUtil.getPackageName();
        LogUtil.e("appName="+appName);
        
    }
    
    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.activity_login;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void loadAgain() {
    
    }
    
    @Override
    protected void loadData() {
    }
    
    @Override
    protected void initView() {
        login_tv_login = findViewById(R.id.login_tv_login);
        login_tv_setting = findViewById(R.id.login_tv_setting);
        loginEtAccount = findViewById(R.id.login_et_account);
        loginEtPassword = findViewById(R.id.login_et_password);
        setEdit();
        setOnclick();
        doCheck();
        setParams();
    }
    
    @Override
    protected void initData(@NonNull Bundle bundle) {
    }
    
    private void doCheck() {
        /*检查权限*/
        checkPermissions(needPermissions);
        /*获取权限*/
        getPermissions();
        
    }
    
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if(null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }
    
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for(String perm : permissions) {
            if(ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }
    
    private void getPermissions() {
        //检查是否已经赋予动态申请的权限
        if(EasyPermissions.hasPermissions(this, dynamicPermission)) {
            // 成功获取权限
        }
        else {
            //有权限没有被赋予去请求权限
            //            if(SPManager.getDefaultManager().getObject(GlobalConstants.SPConstants.SP_HASREJECT_READCONTACT) == null) {
            EasyPermissions.requestPermissions(this, "重要权限", PERMISSON_DYNAMIC_REQUESTCODE, dynamicPermission);
            //            }
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == PERMISSON_DYNAMIC_REQUESTCODE) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }
    
    private void setParams() {
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.APP_ID, GlobalConstants.ParamConstants.APP_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SDK_KEY, GlobalConstants.ParamConstants.SDK_KEY);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SOFT_ID, GlobalConstants.ParamConstants.SOFT_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.LOG_ID, GlobalConstants.ParamConstants.LOG_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.FACE_SERVER, GlobalConstants.ParamConstants.FACE_SERVER);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.PRO_NUM, GlobalConstants.ParamConstants.PRO_NUM);
    }
    
    private void setEdit() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                account = loginEtAccount.getText().toString();
                password = loginEtPassword.getText().toString();
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                account = loginEtAccount.getText().toString();
                password = loginEtPassword.getText().toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        };
        loginEtAccount.addTextChangedListener(textWatcher);
        loginEtPassword.addTextChangedListener(textWatcher);
    }
    
    private void setOnclick() {
        login_tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                if(TextUtils.isEmpty(account)) {
                //                    ToastUtil.showToast("账号不能为空");
                //                }
                //                else if(TextUtils.isEmpty(password)) {
                //                    ToastUtil.showToast("密码不能为空");
                //                }
                //                else {
                presenter.goLogin("admin", "123456");
                //                }
            }
        });
        login_tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });
    }
    
    @Override
    public void logSuccess(LoginBean loginBean) {
        LoginUtil.saveToken(loginBean.token);
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
    
}
