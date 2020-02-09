package com.ponray.entity;

import java.util.ArrayList;
import java.util.List;

public enum FormulaParamType {
    USERPARAM("用户参数",0),resultParam("结果参数",1);

    private String name;

    private int index;

    private FormulaParamType(String name,int index){
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (FormulaParamType c : FormulaParamType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static List<String> listType(){
        List<String> list = new ArrayList<>();
        for (FormulaParamType c : FormulaParamType.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
