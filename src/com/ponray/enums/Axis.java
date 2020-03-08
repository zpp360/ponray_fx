package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 坐标轴
 */
public enum Axis {
    N(1,"力"),DISPLACEMENT(2,"位移"),TRANSFORM(3,"变形"),TIME(4,"时间"),
    YL(5,"应力"),YB(6,"应变"),BQFB(7,"百千分表"),KZSB(8,"扩展设备");

    private int index;
    private String name;

    Axis(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<String> listName(){
        List<String> list = new ArrayList<>();
        for (Axis c : Axis.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
