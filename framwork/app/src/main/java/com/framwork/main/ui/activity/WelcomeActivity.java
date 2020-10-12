package com.framwork.main.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.SPManager;
import com.framwork.common.widget.ClearAbleEditText;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.bean.LoginBean;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.ui.contract.LoginContract;
import com.framwork.main.ui.contract.WelcomeContract;
import com.framwork.main.ui.presenter.LoginPresenter;
import com.framwork.main.ui.presenter.WelcomePresenter;
import com.framwork.main.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends BaseFragmentActivity<WelcomeContract.Presenter> implements WelcomeContract.View {
    private Handler handler;
    private Runnable runnable;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected WelcomeContract.Presenter createPresenter() {
        return new WelcomePresenter(this);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.activity_welcome;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    
    @Override
    protected void loadAgain() {
    
    }
    
    @Override
    protected void loadData() {
    }
    
    @Override
    protected void initView() {
        setParams();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(LoginUtil.isLogin()) {
                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    
    private void setParams() {
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.APP_ID, GlobalConstants.ParamConstants.APP_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SDK_KEY, GlobalConstants.ParamConstants.SDK_KEY);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SOFT_ID, GlobalConstants.ParamConstants.SOFT_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.LOG_ID, GlobalConstants.ParamConstants.LOG_ID);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.FACE_SERVER, GlobalConstants.ParamConstants.FACE_SERVER);
        SPManager.getManager("param").commitString(GlobalConstants.SPConstants.PRO_NUM, GlobalConstants.ParamConstants.PRO_NUM);
    }
    
    @Override
    protected void initData(@NonNull Bundle bundle) {
    }
    
}
