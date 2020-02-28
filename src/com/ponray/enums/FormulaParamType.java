package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

public enum FormulaParamType {
    TEMP("temp",0),CONSTANT("常量",1),BASE_PARAM("基本参数",2),EXTEND_PARAM("扩展参数",3);

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
