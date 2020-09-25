package com.framwork.main.ui.contract;


import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.main.bean.LoginBean;
import com.framwork.main.http.ResultBean;

public interface LoginContract {
    
    interface View extends IBaseView {
        void logSuccess(LoginBean loginBean);
    }
    
    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        
        public abstract void goLogin(String account, String password);
    }
}
