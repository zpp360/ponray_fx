package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

public enum BaseUnit {

    NO("无",""),PERCENT("%","百分号"),TEMPERATURE("温度","温度"),
    MM("mm","毫米"),SQMM("mm^2","平方毫米"),MPA("MPa","兆帕"),
    N("N","牛"),NS("N/s","牛/秒");

    private String unit;
    private String name;

    private BaseUnit(String unit,String name){
        this.name = name;
        this.unit = unit;
    }

    public static String getName(String unit) {
        for (BaseUnit c : BaseUnit.values()) {
            if (c.getUnit() == unit) {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static List<String> listUnit() {
        List<String> list = new ArrayList<>();
        for (BaseUnit c : BaseUnit.values()) {
            list.add(c.getUnit());
        }
        return list;
    }
}
