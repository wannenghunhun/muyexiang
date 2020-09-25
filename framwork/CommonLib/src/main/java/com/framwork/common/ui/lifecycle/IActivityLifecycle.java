package com.framwork.common.ui.lifecycle;

import android.app.Activity;

/**
 *
 */
public interface IActivityLifecycle {
    
    
    default void onCreate(Activity activity) {
    
    }
    
    default void onResume(Activity activity) {
    }
    
    default void onStart(Activity activity) {
    }
    
    default void onRestart(Activity activity) {
    }
    
    default void onPause(Activity activity) {
    }
    
    default void onStop(Activity activity) {
    }
    
    default void onDestroy(Activity activity) {
    }
    
    default void onNewIntent(Activity activity) {
    }
    
    
    default void onWindowFocusChanged(Activity activity, boolean hasFocus) {
    
    }
}
