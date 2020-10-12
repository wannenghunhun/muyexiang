package com.framwork.common.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by liuwenjie on 16/3/9.
 *
 */
public final  class FragmentUtil {

    /**
     * @param activity   androidx.fragment.app.FragmentActivity
     * @param fragment   androidx.fragment.app.Fragment
     * @param fragmentId 布局 id
     */
    public static void addFragmentIntoActivity(FragmentActivity activity, Fragment fragment, int fragmentId) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(fragmentId, fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * @param baseFragment androidx.fragment.app.Fragment
     * @param fragment     androidx.fragment.app.Fragment
     * @param fragmentId   布局 id
     */
    public static void addFragmentIntoFragment(Fragment baseFragment, Fragment fragment, int fragmentId) {
        FragmentTransaction ft = baseFragment.getChildFragmentManager().beginTransaction();
        ft.replace(fragmentId, fragment);
        ft.commitAllowingStateLoss();
    }
}
