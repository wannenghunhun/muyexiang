package com.framwork.main.bean;


import java.util.ArrayList;

public class ConditionsBean extends BaseBean {
    public ArrayList<item_i_v> provinces;
    public ArrayList<item_i_v> education;
    public ArrayList<item_i_v> politicalStatus;
    
    public ArrayList<item_i_n> teams;
    public ArrayList<item_i_n> roles;
    public ArrayList<item_i_n> units;
    
    public ArrayList<type> types;
    
    public class type extends BaseBean {
        public String id;
        public ArrayList<item_i_n> value;
    }
    
    public class item_i_n extends BaseBean {
        public String id;
        public String name;
    }
    
    public class item_i_v extends BaseBean {
        public String id;
        public String value;
    }
}
