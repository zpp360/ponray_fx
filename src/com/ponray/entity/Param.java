package com.ponray.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *参数
 */
public class Param {

    private Long ID;

    private Standard standard;

    private String name;

    private String type;

    private String unit;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "Param{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", type=" + type + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }


}
