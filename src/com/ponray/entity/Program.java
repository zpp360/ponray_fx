package com.ponray.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Program {
    private Long ID;
    //试验方案名
    private String  name;
    //标准
    private Standard standard;
    //试验方向,拉向为0,压向为1
    private Integer direct;
    //试样形状名称
    private String shapeName;
    //变形传感器名
    private String transformSensor;
    //试验结束时去除点数
    private Integer clearDot;
    //是否要变形切换
    private Boolean transformChange;
    //屈服后去掉引伸计
    private Boolean abandonExtend;
    //变形…后去掉引伸计(变形值)
    private Float deformExtend;
    //是否自动判断断裂１是０不是
    private Boolean autoBreakage;
    //当力值大于….开始判断断裂
    private Float gtForce;
    //后前力测量之比小于….为断裂
    private Float ltRate;
    //测量力值小于最大值的….为断裂
    private Float ltMearure;
    //是否定时间
    private  Boolean time;
    //时间值
    private Float timeValue;
    //是否定负荷
    private Boolean load;
    //负荷值
    private Float loadValue;
    //是否定变形
    private Boolean transform;
    //变形值
    private Float transformValue;
    //是否定位移
    private Boolean pos;
    //位移值
    private Float posValue;
    //是否预加载
    private Boolean preload;
    //加载值
    private Float preloadValue;
    //预加载速度
    private Float preloadSpeed;
    //是否自动返回
    private Boolean isReturn;
    //返回速度
    private Float returnSpeed;
    //是否为程序控制，如果是程序控制为1,否则为0
    private Float proControl;
    //位移方式的试验运行速度
    private Float generalSpeed;
    //是否为默认方案
    private Boolean isDefault;
    //pid 暂无
    private String selPid;
    //位移清零
    private Boolean clearDisp;
    //变形清零
    private Boolean clearTransform;
    //力清零
    private Boolean clearN;

    //主界面x轴
    private String oneX;

    private String oneY;

    private String twoX;

    private String twoY;

    private String threeX;

    private String threeY;

    private String fourX;

    private String fourY;
    //曲线单位力
    private String unitN;
    //曲线单位变形
    private String unitTransform;
    //曲线单位应力
    private String unitLoad;

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

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public String getTransformSensor() {
        return transformSensor;
    }

    public void setTransformSensor(String transformSensor) {
        this.transformSensor = transformSensor;
    }

    public Integer getClearDot() {
        return clearDot;
    }

    public void setClearDot(Integer clearDot) {
        this.clearDot = clearDot;
    }

    public Boolean isTransformChange() {
        return transformChange;
    }

    public void setTransformChange(Boolean transformChange) {
        this.transformChange = transformChange;
    }

    public Boolean isAbandonExtend() {
        return abandonExtend;
    }

    public void setAbandonExtend(Boolean abandonExtend) {
        this.abandonExtend = abandonExtend;
    }

    public Float getDeformExtend() {
        return deformExtend;
    }

    public void setDeformExtend(Float deformExtend) {
        this.deformExtend = deformExtend;
    }

    public Boolean isAutoBreakage() {
        return autoBreakage;
    }

    public void setAutoBreakage(Boolean autoBreakage) {
        this.autoBreakage = autoBreakage;
    }

    public Float getGtForce() {
        return gtForce;
    }

    public void setGtForce(Float gtForce) {
        this.gtForce = gtForce;
    }

    public Float getLtRate() {
        return ltRate;
    }

    public void setLtRate(Float ltRate) {
        this.ltRate = ltRate;
    }

    public Float getLtMearure() {
        return ltMearure;
    }

    public void setLtMearure(Float ltMearure) {
        this.ltMearure = ltMearure;
    }

    public Boolean isTime() {
        return time;
    }

    public void setTime(Boolean time) {
        this.time = time;
    }

    public Float getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Float timeValue) {
        this.timeValue = timeValue;
    }

    public Boolean isLoad() {
        return load;
    }

    public void setLoad(Boolean load) {
        this.load = load;
    }

    public Float getLoadValue() {
        return loadValue;
    }

    public void setLoadValue(Float loadValue) {
        this.loadValue = loadValue;
    }

    public Boolean isTransform() {
        return transform;
    }

    public void setTransform(Boolean transform) {
        this.transform = transform;
    }

    public Float getTransformValue() {
        return transformValue;
    }

    public void setTransformValue(Float transformValue) {
        this.transformValue = transformValue;
    }

    public Boolean isPos() {
        return pos;
    }

    public void setPos(Boolean pos) {
        this.pos = pos;
    }

    public Float getPosValue() {
        return posValue;
    }

    public void setPosValue(Float posValue) {
        this.posValue = posValue;
    }

    public Boolean isPreload() {
        return preload;
    }

    public void setPreload(Boolean preload) {
        this.preload = preload;
    }

    public Float getPreloadValue() {
        return preloadValue;
    }

    public void setPreloadValue(Float preloadValue) {
        this.preloadValue = preloadValue;
    }

    public Float getPreloadSpeed() {
        return preloadSpeed;
    }

    public void setPreloadSpeed(Float preloadSpeed) {
        this.preloadSpeed = preloadSpeed;
    }

    public Boolean isReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public Float getReturnSpeed() {
        return returnSpeed;
    }

    public void setReturnSpeed(Float returnSpeed) {
        this.returnSpeed = returnSpeed;
    }

    public Float getProControl() {
        return proControl;
    }

    public void setProControl(Float proControl) {
        this.proControl = proControl;
    }

    public Float getGeneralSpeed() {
        return generalSpeed;
    }

    public void setGeneralSpeed(Float generalSpeed) {
        this.generalSpeed = generalSpeed;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getSelPid() {
        return selPid;
    }

    public void setSelPid(String selPid) {
        this.selPid = selPid;
    }

    public Boolean isClearDisp() {
        return clearDisp;
    }

    public void setClearDisp(Boolean clearDisp) {
        this.clearDisp = clearDisp;
    }

    public Boolean isClearTransform() {
        return clearTransform;
    }

    public void setClearTransform(Boolean clearTransform) {
        this.clearTransform = clearTransform;
    }

    public Boolean isClearN() {
        return clearN;
    }

    public void setClearN(Boolean clearN) {
        this.clearN = clearN;
    }

    public String getOneX() {
        return oneX;
    }

    public void setOneX(String oneX) {
        this.oneX = oneX;
    }

    public String getOneY() {
        return oneY;
    }

    public void setOneY(String oneY) {
        this.oneY = oneY;
    }

    public String getTwoX() {
        return twoX;
    }

    public void setTwoX(String twoX) {
        this.twoX = twoX;
    }

    public String getTwoY() {
        return twoY;
    }

    public void setTwoY(String twoY) {
        this.twoY = twoY;
    }

    public String getThreeX() {
        return threeX;
    }

    public void setThreeX(String threeX) {
        this.threeX = threeX;
    }

    public String getThreeY() {
        return threeY;
    }

    public void setThreeY(String threeY) {
        this.threeY = threeY;
    }

    public String getFourX() {
        return fourX;
    }

    public void setFourX(String fourX) {
        this.fourX = fourX;
    }

    public String getFourY() {
        return fourY;
    }

    public void setFourY(String fourY) {
        this.fourY = fourY;
    }

    public String getUnitN() {
        return unitN;
    }

    public void setUnitN(String unitN) {
        this.unitN = unitN;
    }

    public String getUnitTransform() {
        return unitTransform;
    }

    public void setUnitTransform(String unitTransform) {
        this.unitTransform = unitTransform;
    }

    public String getUnitLoad() {
        return unitLoad;
    }

    public void setUnitLoad(String unitLoad) {
        this.unitLoad = unitLoad;
    }
}
