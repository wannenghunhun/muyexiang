package com.framwork.main.bean;


import java.util.ArrayList;

public class EmployeesBean extends BaseBean {
    //关键字类型 0-名称 1-单位 2-班组
    //订单状态：0-离岗 1-在岗 默认在岗
    public int total;
    public int pages;
    public int pageNum;
    public ArrayList<employe> employees;
    
    public class employe extends BaseBean {
        public String id;
        public String name;
        public String photoPath;
        public String tel;
        public int status;
        public String unit;
        public String team;
        public String workType;
    }
    
}
