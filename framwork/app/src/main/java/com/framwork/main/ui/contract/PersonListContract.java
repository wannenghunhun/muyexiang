package com.framwork.main.ui.contract;


import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.main.bean.EmployeesBean;

public interface PersonListContract {
    
    interface View extends IBaseView {
        void setViews(EmployeesBean employeesBean, boolean isRefresh);
    }
    
    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        
        public abstract void getEmployeesInfo(String key, int keyType, String projectId, int status, int pageNum, int pageSize, boolean isRefresh);
    }
}
