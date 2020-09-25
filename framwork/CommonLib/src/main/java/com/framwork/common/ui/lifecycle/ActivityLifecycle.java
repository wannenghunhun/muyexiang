package com.framwork.common.ui.lifecycle;

import android.app.Activity;

import java.util.ArrayList;

/**
 *
 */
public interface ActivityLifecycle<T extends Activity> {
    
    ArrayList<IActivityLifecycle> mSubscribers = new ArrayList<>();
    
    static void addActivityLife(IActivityLifecycle life) {
        synchronized(mSubscribers) {
            mSubscribers.add(life);
        }
        
    }
    
    static void removeActivityLife(IActivityLifecycle life) {
        synchronized(mSubscribers) {
            mSubscribers.remove(life);
        }
    }
    
    static void clearLife() {
        synchronized(mSubscribers) {
            mSubscribers.clear();
        }
        
    }
    
    default void onCreate(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onCreate(activity);
        }
    }
    
    default void onResume(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onResume(activity);
        }
    }
    
    default void onStart(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onStart(activity);
        }
    }
    
    default void onRestart(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onRestart(activity);
        }
    }
    
    default void onPause(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onPause(activity);
        }
    }
    
    default void onStop(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onStop(activity);
        }
    }
    
    default void onDestroy(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onDestroy(activity);
        }
    }
    
    default void onNewIntent(T activity) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onNewIntent(activity);
        }
    }
    
    
    default void onWindowFocusChanged(Activity activity, boolean hasFocus) {
        for(IActivityLifecycle IActivityLifecycle : mSubscribers) {
            IActivityLifecycle.onWindowFocusChanged(activity, hasFocus);
        }
    }
}
