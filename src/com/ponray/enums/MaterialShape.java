package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 材料形状
 */
public enum MaterialShape {
    BANC(1,"板材"),BANGC(2,"棒材"),GC(3,"管材"),HXC(4,"弧形材"),YXC(5,"异型材");


    private int index;
    private String name;

    private MaterialShape(int index, String name) {
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
        for (MaterialShape c : MaterialShape.values()) {
            if (c.getName() == name) {
                return c.index;
            }
        }
        return 0;
    }

    public static List<String> listName() {
        List<String> list = new ArrayList<>();
        for (MaterialShape c : MaterialShape.values()) {
            list.add(c.getName());
        }
        return list;
    }
}
