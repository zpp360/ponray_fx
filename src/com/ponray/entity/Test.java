package com.ponray.entity;


import java.sql.Date;

/**
 * 实验
 */
public class Test {
    private Long testNum;//实验编号
    private Date testTime;//实验时间
    private String programName;//实验方案名称
    private String standardName;//实验标准
    private String transformSensor;//变形计算选择
    private String loadUnit;//力单位名称
    private String transformUnit;//变形单位名称
    private String pressUnit;//强度单位名称
    private String saveFile;//保存文件名
    private Float speed;//位移控制的速度，如果是程序控制则为“0”
    private Long runTime;//运行时间
    private String shape;//试样的形状

    public Long getTestNum() {
        return testNum;
    }

    public void setTestNum(Long testNum) {
        this.testNum = testNum;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getTransformSensor() {
        return transformSensor;
    }

    public void setTransformSensor(String transformSensor) {
        this.transformSensor = transformSensor;
    }

    public String getLoadUnit() {
        return loadUnit;
    }

    public void setLoadUnit(String loadUnit) {
        this.loadUnit = loadUnit;
    }

    public String getTransformUnit() {
        return transformUnit;
    }

    public void setTransformUnit(String transformUnit) {
        this.transformUnit = transformUnit;
    }

    public String getPressUnit() {
        return pressUnit;
    }

    public void setPressUnit(String pressUnit) {
        this.pressUnit = pressUnit;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
