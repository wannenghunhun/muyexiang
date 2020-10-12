package com.framwork.main.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.framwork.common.ui.activity.BaseFragmentActivity;
import com.framwork.common.utils.SPManager;
import com.framwork.common.widget.ClearAbleEditText;
import com.framwork.main.GlobalConstants;
import com.framwork.main.R;
import com.framwork.main.ui.contract.SettingContract;
import com.framwork.main.ui.presenter.SettingPresenter;

public class SettingActivity extends BaseFragmentActivity<SettingContract.Presenter> implements SettingContract.View {
    private ClearAbleEditText settingEtAppid;
    private ClearAbleEditText settingEtAppkey;
    private ClearAbleEditText settingEtSoftId;
    private ClearAbleEditText settingEtLogKey;
    private ClearAbleEditText settingEtFaceServer;
    private ClearAbleEditText mSettingEtProNum;
    private Button settingBtnCancel;
    private Button settingBtnConfirm;
    private String appId, appKey, softId, logKey, faceServer, pro_num;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.activity_setting;
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
        settingEtAppid = findViewById(R.id.setting_et_appid);
        settingEtAppkey = findViewById(R.id.setting_et_appkey);
        settingEtSoftId = findViewById(R.id.setting_et_soft_id);
        settingEtLogKey = findViewById(R.id.setting_et_log_key);
        settingEtFaceServer = findViewById(R.id.setting_et_face_server);
        settingBtnCancel = findViewById(R.id.setting_btn_cancel);
        settingBtnConfirm = findViewById(R.id.setting_btn_confirm);
        mSettingEtProNum = findViewById(R.id.setting_et_pro_num);
        setEdit();
        setOnClick();
    }
    
    @Override
    protected void initData(@NonNull Bundle bundle) {
    }
    
    private void setEdit() {
        appId = GlobalConstants.ParamConstants.APP_ID;
        appKey = GlobalConstants.ParamConstants.SDK_KEY;
        softId = GlobalConstants.ParamConstants.SOFT_ID;
        logKey = GlobalConstants.ParamConstants.LOG_ID;
        faceServer = GlobalConstants.ParamConstants.FACE_SERVER;
        pro_num = GlobalConstants.ParamConstants.PRO_NUM;
        settingEtAppid.setText(GlobalConstants.ParamConstants.APP_ID);
        settingEtAppkey.setText(GlobalConstants.ParamConstants.SDK_KEY);
        settingEtSoftId.setText(GlobalConstants.ParamConstants.SOFT_ID);
        settingEtLogKey.setText(GlobalConstants.ParamConstants.LOG_ID);
        settingEtFaceServer.setText(GlobalConstants.ParamConstants.FACE_SERVER);
        mSettingEtProNum.setText(GlobalConstants.ParamConstants.PRO_NUM);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                appId = settingEtAppid.getText().toString();
                appKey = settingEtAppkey.getText().toString();
                softId = settingEtSoftId.getText().toString();
                logKey = settingEtLogKey.getText().toString();
                faceServer = settingEtFaceServer.getText().toString();
                pro_num = mSettingEtProNum.getText().toString();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        settingEtAppid.addTextChangedListener(tw);
        settingEtAppkey.addTextChangedListener(tw);
        settingEtSoftId.addTextChangedListener(tw);
        settingEtLogKey.addTextChangedListener(tw);
        settingEtFaceServer.addTextChangedListener(tw);
        mSettingEtProNum.addTextChangedListener(tw);
        
    }
    
    private void setOnClick() {
        settingBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.APP_ID, appId);
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SDK_KEY, appKey);
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.SOFT_ID, softId);
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.LOG_ID, logKey);
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.FACE_SERVER, faceServer);
                SPManager.getManager("param").commitString(GlobalConstants.SPConstants.PRO_NUM, pro_num);
                finish();
            }
        });
        settingBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
}
