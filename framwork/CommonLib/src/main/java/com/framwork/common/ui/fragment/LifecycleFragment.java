package com.framwork.common.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framwork.common.ui.lifecycle.FragmentLifecycle;


/**
 *
 */

public abstract class LifecycleFragment extends Fragment implements FragmentLifecycle {


    protected boolean isHide = false;
    protected boolean isInit = false;
    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentLifecycle.super.onAttach(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentLifecycle.super.onCreate(this);
        mActivity = getActivity();
    }


    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLifecycle.super.onCreateView(this);

        return createView(inflater, container, savedInstanceState);
    }


    public abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentLifecycle.super.onActivityCreated(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        FragmentLifecycle.super.onStart(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
        }
        FragmentLifecycle.super.onResume(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        FragmentLifecycle.super.onPause(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        FragmentLifecycle.super.onStop(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentLifecycle.super.onDestroyView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentLifecycle.super.onDestroy(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        FragmentLifecycle.super.onDetach(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        FragmentLifecycle.super.setUserVisibleHint(isVisibleToUser, this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        FragmentLifecycle.super.onHiddenChanged(hidden, this);
        isHide = hidden;
    }


}