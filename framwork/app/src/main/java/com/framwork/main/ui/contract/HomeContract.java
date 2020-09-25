package com.framwork.main.ui.contract;


import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.main.bean.ProjectInfoBean;
import com.framwork.main.bean.UserInfoBean;

public interface HomeContract {
    
    interface View extends IBaseView {
        void setUserInfo(UserInfoBean userInfo);
        
        void setProjectInfo(ProjectInfoBean projectInfo);
    }
    
    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        
        public abstract void getUserInfo();
        
        public abstract void getProjectInfo(String id);
    }
}
