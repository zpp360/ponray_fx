package com.ponray.entity;

public class TestData {

    private Long ID;

    private Long testNum;

    private Long timeValue;

    private Float loadVal1;//力1
    private Float loadVal2;//力2
    private Float loadVal3;//力3

    private Float posVal;//位移值

    private Float deformVal;//变形值

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getTestNum() {
        return testNum;
    }

    public void setTestNum(Long testNum) {
        this.testNum = testNum;
    }

    public Long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Long timeValue) {
        this.timeValue = timeValue;
    }

    public Float getLoadVal1() {
        return loadVal1;
    }

    public void setLoadVal1(Float loadVal1) {
        this.loadVal1 = loadVal1;
    }

    public Float getLoadVal2() {
        return loadVal2;
    }

    public void setLoadVal2(Float loadVal2) {
        this.loadVal2 = loadVal2;
    }

    public Float getLoadVal3() {
        return loadVal3;
    }

    public void setLoadVal3(Float loadVal3) {
        this.loadVal3 = loadVal3;
    }

    public Float getPosVal() {
        return posVal;
    }

    public void setPosVal(Float posVal) {
        this.posVal = posVal;
    }

    public Float getDeformVal() {
        return deformVal;
    }

    public void setDeformVal(Float deformVal) {
        this.deformVal = deformVal;
    }
}
