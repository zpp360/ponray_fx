package com.ponray.entity;

public class Unit {

    private Long ID;
    //类型
    private String unitType;
    //单位代号
    private String unitCode;
    //比例系数
    private String param;
    //单位基本代号
    private String baseUnitCode;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getBaseUnitCode() {
        return baseUnitCode;
    }

    public void setBaseUnitCode(String baseUnitCode) {
        this.baseUnitCode = baseUnitCode;
    }
}
