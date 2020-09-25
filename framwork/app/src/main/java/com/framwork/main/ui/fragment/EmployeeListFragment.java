package com.framwork.main.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.framwork.common.ui.fragment.BaseRefreshFragment;
import com.framwork.common.utils.ToastUtil;
import com.framwork.main.R;
import com.framwork.main.bean.EmployeesBean;
import com.framwork.main.ui.adapter.EmployeesAdapter;
import com.framwork.main.ui.contract.PersonListContract;
import com.framwork.main.ui.presenter.PersonListPresenter;

public class EmployeeListFragment extends BaseRefreshFragment<PersonListContract.Presenter> implements PersonListContract.View {
    private RecyclerView employee_list;
    private EmployeesAdapter mAdapter;
    private int page = 1, pageSize = 20, total;
    private String key = "", projectId;
    private int keyType=0, status=1, pageNum;
    private boolean isRefresh;
    
    public static EmployeeListFragment newInstance(String projectId) {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        args.putString("projectId", projectId);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected void initView(View view) {
        setEnableRefresh(true);
        setEnableLoadMore(true);
        employee_list = findViewById(R.id.employee_list);
        employee_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EmployeesAdapter(getActivity(), projectId);
        employee_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        employee_list.setAdapter(mAdapter);
    }
    
    @Override
    protected int layoutContentId() {
        return R.layout.fragment_employee_list;
    }
    
    @Override
    protected PersonListContract.Presenter createPresenter() {
        return new PersonListPresenter(this);
    }
    
    @Override
    protected void initData() {
        projectId = getArguments().getString("projectId");
    }
    
    @Override
    protected void loadData() {
        presenter.getEmployeesInfo("", 0, projectId, 1, 1, pageSize, true);
    }
    
    @Override
    protected void loadAgain() {
        //        presenter.getEmployeesInfo("", 0, projectId, 1, 1, pageSize, true);
    }
    
    @Override
    protected void pullDownRefresh() {
        super.pullDownRefresh();
        showLoading();
        page = 1;
        presenter.getEmployeesInfo(key, keyType, projectId, status, page, pageSize, true);
    }
    
    @Override
    protected void pullUpLoadMore() {
        super.pullUpLoadMore();
        showLoading();
        int t = mAdapter.getItemCount();
        if(t < total) {
            page = page + 1;
            presenter.getEmployeesInfo(key, keyType, projectId, status, page, pageSize, false);
        }
        else {
            dissLoading();
            loadComplete();
            ToastUtil.showToast("没有更多数据了");
        }
    }
    
    public void reqData(String key, int keyType, String projectId, int status) {
        this.key = key;
        this.keyType = keyType;
        this.status = status;
        presenter.getEmployeesInfo(key, keyType, projectId, status, 1, pageSize, true);
    }
    
    @Override
    public void setViews(EmployeesBean employeesBean, boolean isRefresh) {
        total = employeesBean.total;
        if(isRefresh) {
            if(total < 1) {
                showEmpty("暂时没有数据");
            }
            else {
                mAdapter.setData(employeesBean.employees);
                loadComplete();
            }
        }
        else {
            if(total < 1) {
                dissLoading();
                loadComplete();
                ToastUtil.showToast("没有更多数据了");
            }
            else {
                mAdapter.addItems2Last(employeesBean.employees);
            }
            loadComplete();
        }
    }
    
    
}