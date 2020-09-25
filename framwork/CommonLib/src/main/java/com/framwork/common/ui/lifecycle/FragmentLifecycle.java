package com.framwork.common.ui.lifecycle;

import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 *
 */
public interface FragmentLifecycle {
    
    ArrayList<IFragmentLifecycle> mSubscribers = new ArrayList<>();
    
    static void addFragmentLife(IFragmentLifecycle life) {
        synchronized(mSubscribers) {
            mSubscribers.add(life);
        }
    }
    
    static void removeFragmentLife(IFragmentLifecycle life) {
        synchronized(mSubscribers) {
            mSubscribers.remove(life);
        }
    }
    
    static void clearLife() {
        synchronized(mSubscribers) {
            mSubscribers.clear();
        }
    }
    
    @MainThread
    default void onAttach(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onAttach(fragment);
        }
    }
    
    default void onCreate(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onCreate(fragment);
        }
    }
    
    default void onCreateView(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onCreateView(fragment);
        }
    }
    
    default void onActivityCreated(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onActivityCreated(fragment);
        }
    }
    
    default void onStart(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onStart(fragment);
        }
    }
    
    default void onResume(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onResume(fragment);
        }
    }
    
    default void onPause(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onPause(fragment);
        }
    }
    
    default void onStop(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onStop(fragment);
        }
    }
    
    default void onDestroyView(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onDestroyView(fragment);
        }
    }
    
    default void onDestroy(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onDestroy(fragment);
        }
    }
    
    default void onDetach(Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onDetach(fragment);
        }
    }
    
    default void setUserVisibleHint(boolean isVisibleToUser, Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.setUserVisibleHint(isVisibleToUser, fragment);
        }
    }
    
    default void onHiddenChanged(boolean hidden, Fragment fragment) {
        for(IFragmentLifecycle IFragmentLifecycle : mSubscribers) {
            IFragmentLifecycle.onHiddenChanged(hidden, fragment);
        }
    }
}
