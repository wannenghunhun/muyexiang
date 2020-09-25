package com.framwork.main.bean;


import java.util.ArrayList;

public class UserInfoBean extends BaseBean {
    public String date;
    public String week;
    public String name;
    public ArrayList<project> projects;
    
    public class project extends BaseBean {
        public String id;
        public String name;
    }
    
}
