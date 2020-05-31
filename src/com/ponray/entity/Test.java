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
    private String imgFile;//实验曲线图片

    private Float maxLoad;//峰值

    private Float minLoad;//最小力值

    private Float avgLoad;//平均力值

    private Float maxLoadPos;//峰值对应的位移，拉伸力

    private Float deep;//穿刺深度

    private Float width;//宽度

    private Float hou;//厚度

    private Float dia;//直径

    private Float area;//面积

    private Float mpa;//拉伸强度

    private String simpleName;//试样名称

    private Float lo;//标距

    private Float extension;//伸长率

    private Float blqd;//剥离强度

    public Float getBlqd() {
        return blqd;
    }

    public void setBlqd(Float blqd) {
        this.blqd = blqd;
    }

    public Float getMinLoad() {
        return minLoad;
    }

    public void setMinLoad(Float minLoad) {
        this.minLoad = minLoad;
    }

    public Float getAvgLoad() {
        return avgLoad;
    }

    public void setAvgLoad(Float avgLoad) {
        this.avgLoad = avgLoad;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHou() {
        return hou;
    }

    public void setHou(Float hou) {
        this.hou = hou;
    }

    public Float getDia() {
        return dia;
    }

    public void setDia(Float dia) {
        this.dia = dia;
    }

    public Float getLo() {
        return lo;
    }

    public void setLo(Float lo) {
        this.lo = lo;
    }

    public Float getExtension() {
        return extension;
    }

    public void setExtension(Float extension) {
        this.extension = extension;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Float getMpa() {
        return mpa;
    }

    public void setMpa(Float mpa) {
        this.mpa = mpa;
    }

    public Float getDeep() {
        return deep;
    }

    public void setDeep(Float deep) {
        this.deep = deep;
    }

    public Float getMaxLoadPos() {
        return maxLoadPos;
    }

    public void setMaxLoadPos(Float maxLoadPos) {
        this.maxLoadPos = maxLoadPos;
    }

    public Float getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Float maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

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
