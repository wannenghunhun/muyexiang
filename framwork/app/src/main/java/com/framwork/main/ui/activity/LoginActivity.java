package com.framwork.main.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.AppUtil;
import com.framwork.common.utils.LogUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.utils.ToastUtil;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
    
    @Override
    protected void initData(@NonNull Bundle bundle) {
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
                if(TextUtils.isEmpty(account)) {
                    ToastUtil.showToast("账号不能为空");
                }
                else if(TextUtils.isEmpty(password)) {
                    ToastUtil.showToast("密码不能为空");
                }
                else {
                    presenter.goLogin(account, password);
                }
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
