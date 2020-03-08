package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 终控方式
 */
public enum  ControlEnd {
    DISPLACEMENT("位移","mm/min"),N("力控制","N/s"),TRANSFORM("变形控制","mm/min"),
    WYYBKZ("位移应变控制","%/s"),YSJYBKZ("引伸计应变控制","%/s"),YLKZ("应力控制","MPa/s"),
    ZDLJF("最大力降幅","");
    private String name;
    private String unit;

    private  ControlEnd(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static String getUnit(String name){
        for (ControlEnd c : ControlEnd.values()) {
            if (c.getName() == name) {
                return c.unit;
            }
        }
        return null;
    }

    public static List<String> listName(){
        List<String> list = new ArrayList<>();
        for (ControlEnd c : ControlEnd.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
