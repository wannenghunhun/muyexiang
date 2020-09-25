package com.framwork.common.ui.lifecycle;

import android.support.v4.app.Fragment;

/**
 *
 */

public interface IFragmentLifecycle {
    
    default void onAttach(Fragment fragment) {
    }
    
    default void onCreate(Fragment fragment) {
    }
    
    default void onCreateView(Fragment fragment) {
    }
    
    default void onActivityCreated(Fragment fragment) {
    }
    
    default void onStart(Fragment fragment) {
    }
    
    default void onResume(Fragment fragment) {
    }
    
    default void onPause(Fragment fragment) {
    }
    
    default void onStop(Fragment fragment) {
    }
    
    default void onDestroyView(Fragment fragment) {
    }
    
    default void onDestroy(Fragment fragment) {
    }
    
    default void onDetach(Fragment fragment) {
    }
    
    default void setUserVisibleHint(boolean isVisibleToUser, Fragment fragment) {
    }
    
    default void onHiddenChanged(boolean hidden, Fragment fragment) {
    }
    
}
