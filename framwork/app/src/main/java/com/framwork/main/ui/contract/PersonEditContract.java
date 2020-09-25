package com.framwork.main.ui.contract;


import com.framwork.common.presenter.BasePresenter;
import com.framwork.common.ui.view.IBaseView;
import com.framwork.main.bean.BaseBean;
import com.framwork.main.bean.ConditionsBean;
import com.framwork.main.bean.EmployeeBean;
import com.framwork.main.bean.UserInfoBean;

public interface PersonEditContract {
    
    interface View extends IBaseView {
        void setConditionsInfo(ConditionsBean projectInfo);
        
        void setProjectInfo(UserInfoBean userInfo);
        
        void setEmployeeInfo(EmployeeBean employeeBean);
        
        void editResult(String s);
    }
    
    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        
        public abstract void getUserInfo();
        
        public abstract void getConditionsInfo(String projectId);
        
        public abstract void getEmployeeInfo(String id);
        
        public abstract void employeeBack(String id);
        
        public abstract void editEmployeesInfo(String id,
                                               String idPhotoPath,
                                               String photoPath,
                                               String name,
                                               String idCard,
                                               String sex,
                                               String birthday,
                                               String nation,
                                               String address,
                                               String tel,
                                               String politicalStatus,
                                               String nativePlace,
                                               String education,
                                               String ifForeman,
                                               String ifTrain,
                                               String ifCertificate,
                                               String projectId,
                                               String unitId,
                                               String teamId,
                                               String roleType,
                                               String workType,
                                               String age,
                                               String certificateSn,
                                               String contractSn);
    }
}
