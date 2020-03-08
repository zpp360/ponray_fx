package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 始控方式
 */
public enum ControlStart {
    DISPLACEMENT("位移","mm/min"),N("力控制","N/s"),TRANSFORM("变形控制","mm/min"),
    WYYBKZ("位移应变控制","%/s"),YSJYBKZ("引伸计应变控制","%/s"),YLKZ("应力控制","MPa/s"),
    WYBC("位移保持","s"),LBZ("力保载","s"),BXBC("变形保持","s"),XHKS("循环开始","次")
    ,XHJS("循环结束","次"),CYJG("采样间隔","个点采样一次");

    private String name;
    private String unit;

    private ControlStart(String name, String unit) {
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
        for (ControlStart c : ControlStart.values()) {
            if (c.getName() == name) {
                return c.unit;
            }
        }
        return null;
    }

    public static List<String> listName(){
        List<String> list = new ArrayList<>();
        for (ControlStart c : ControlStart.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
