package com.ponray.entity;

public class ProgramControl {
    private Long ID;
    private Program program;
    private Integer num;
    private String start;
    private String startValue;
    private String startUnit;
    private String end;
    private String endValue;
    private String endUnit;
    public ProgramControl(){}

    public ProgramControl(Integer num, String start, String startValue, String startUnit, String end, String endValue, String endUnit) {
        this.num = num;
        this.start = start;
        this.startValue = startValue;
        this.startUnit = startUnit;
        this.end = end;
        this.endValue = endValue;
        this.endUnit = endUnit;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getStartUnit() {
        return startUnit;
    }

    public void setStartUnit(String startUnit) {
        this.startUnit = startUnit;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getEndUnit() {
        return endUnit;
    }

    public void setEndUnit(String endUnit) {
        this.endUnit = endUnit;
    }

    @Override
    public String toString() {
        return  (start != null ? ("：始控方式-" + start) : "") +
                (startValue != null ? ("：" + startValue) : "") +
                (startUnit != null ? startUnit : "") +
                (end != null ? ("  终控方式-" + end):"") +
                (endValue!=null?("：" + endValue):"") +
                (endUnit!=null?endUnit:"");
    }
}
