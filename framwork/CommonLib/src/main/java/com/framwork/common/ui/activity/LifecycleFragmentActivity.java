package com.framwork.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.framwork.common.ui.lifecycle.ActivityLifecycle;


/**
 *
 */

public abstract class LifecycleFragmentActivity extends FragmentActivity implements ActivityLifecycle<FragmentActivity> {
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLifecycle.super.onCreate(this);
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        ActivityLifecycle.super.onRestart(this);
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
        ActivityLifecycle.super.onResume(this);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        ActivityLifecycle.super.onStart(this);
    }
    
    
    @Override
    protected void onPause() {
        super.onPause();
        ActivityLifecycle.super.onPause(this);
    }
    
    
    @Override
    protected void onStop() {
        super.onStop();
        ActivityLifecycle.super.onStop(this);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityLifecycle.super.onDestroy(this);
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ActivityLifecycle.super.onNewIntent(this);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ActivityLifecycle.super.onWindowFocusChanged(this, hasFocus);
    }
}