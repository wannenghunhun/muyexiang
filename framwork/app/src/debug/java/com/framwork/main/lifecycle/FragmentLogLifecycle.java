package com.framwork.main.lifecycle;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.framwork.common.ui.lifecycle.IFragmentLifecycle;


/**
 *
 */
public class FragmentLogLifecycle implements IFragmentLifecycle {

    private String seg = "  ";

    @Override
    public void onAttach(Fragment fragment) {
        log(fragment, "onAttach");
    }

    @Override
    public void onCreate(Fragment fragment) {
        log(fragment, "onCreate");
    }

    @Override
    public void onCreateView(Fragment fragment) {
        log(fragment, "onCreateView");
    }

    @Override
    public void onActivityCreated(Fragment fragment) {
        log(fragment, "onActivityCreated");
    }

    @Override
    public void onStart(Fragment fragment) {
        log(fragment, "onStart");
    }

    @Override
    public void onResume(Fragment fragment) {
        log(fragment, "onResume");
    }

    @Override
    public void onPause(Fragment fragment) {
        log(fragment, "onPause");
    }

    @Override
    public void onStop(Fragment fragment) {
        log(fragment, "onStop");
    }

    @Override
    public void onDestroyView(Fragment fragment) {
        log(fragment, "onDestroyView");
    }

    @Override
    public void onDestroy(Fragment fragment) {
        log(fragment, "onDestroy");
    }

    @Override
    public void onDetach(Fragment fragment) {
        log(fragment, "onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser, Fragment fragment) {
        log(fragment, "setUserVisibleHint", isVisibleToUser + "");
    }


    @Override
    public void onHiddenChanged(boolean hidden, Fragment fragment) {
        log(fragment, "onHiddenChanged", hidden + "");
    }


    private String getClassName(Fragment fragment) {
        return fragment.getClass().getSimpleName() + seg + "--->";
    }


    @SuppressWarnings("all")
    private void log(Fragment fragment, String... strings) {
        StringBuilder sb = new StringBuilder(getClassName(fragment));
        if(strings != null && strings.length >= 1) {
            int count = strings.length;
            for(int i = 0; i < count; i++) {
                sb.append(seg).append("%s");
            }
        }

        Log.d("Fragment", String.format(sb.toString(), (Object[]) strings));
    }

}
