package com.framwork.common.ui.fragment;

import android.support.v4.app.Fragment;

import com.framwork.common.ui.lifecycle.IFragmentLifecycle;


/**
 *
 * 网络请求取消
 */
public class DLRFragmentLifecycle implements IFragmentLifecycle {

    @Override
    public void onDestroy(Fragment fragment) {
        // fix 在极端情况下，fragment 销毁的时候，LoadingDialog 无法消失
        //        LoadingUtil.pop(fragment);
//        RestClient.cancelRequest(fragment);
    }
}
