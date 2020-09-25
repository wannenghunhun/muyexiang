package com.framwork.main.bean;


import java.util.ArrayList;

public class ProjectInfoBean extends BaseBean {
    public Project project;
    
    public class Project extends BaseBean {
        public String id;
        public String name;
        public String generalUnit;
        public String address;
        public String manager;
        public String managerTel;
        public String totalArea;
        public String status;
    }
    
}
