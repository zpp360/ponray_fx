package com.ponray.enums;

import java.util.ArrayList;
import java.util.List;

public enum UnitType {
    BXSL("变形速率","mm/s"),BLQD("剥离强度","N/mm"),CD("长度","mm"),
    JD("角度","°"),L("力","N"),LZSL("力值速率","N/s"),
    MJ("面积","mm²"),QD("强度","MPa"),SD("湿度","%"),SJ("时间","s"),WD("温度","℃");

    private String type;
    private String code;

    private UnitType(String type,String code){
        this.type = type;
        this.code = code;
    }

    public static String getCode(String type) {
        for (UnitType c : UnitType.values()) {
            if (c.getType() == type) {
                return c.code;
            }
        }
        return null;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<String> listType() {
        List<String> list = new ArrayList<>();
        for (UnitType c : UnitType.values()) {
            list.add(c.getType());
        }
        return list;
    }

    /**
     * 是否是基本单位，不能删除基本单位
     * @param code
     * @return
     */
    public static boolean isBaseCode(String code){
        for (UnitType c : UnitType.values()) {
            return c.getCode().equals(code);
        }
        return false;
    }
}
