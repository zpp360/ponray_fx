package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

public enum TransformCalculat {

    WY(1,"位移"),BX(2,"变形"),KZSB(3,"扩展设备"),BQFB(4,"百/千分表");


    private int index;
    private String name;

    private TransformCalculat(int index, String name) {
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

    public static int getIndex(String name) {
        for (TransformCalculat c : TransformCalculat.values()) {
            if (c.getName() == name) {
                return c.index;
            }
        }
        return 0;
    }

    public static List<String> listName() {
        List<String> list = new ArrayList<>();
        for (TransformCalculat c : TransformCalculat.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
