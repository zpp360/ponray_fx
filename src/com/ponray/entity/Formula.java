package com.ponray.entity;

/**
 * 公式
 */
public class Formula {
    private Long ID;
    /**
     * 临时变量
     */
    private String tempVari;
    /**
     * 符号
     */
    private String symbol;
    /**
     * 参数类型1
     */
    private String paramTypeOne;
    /**
     * 参数名称1
     */
    private String paramNameOne;
    /**
     * 运算符
     */
    private String opChar;
    /**
     * 参数类型2
     */
    private String paramTypeTwo;
    /**
     * 参数名称2
     */
    private String paramNameTwo;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTempVari() {
        return tempVari;
    }

    public void setTempVari(String tempVari) {
        this.tempVari = tempVari;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getParamTypeOne() {
        return paramTypeOne;
    }

    public void setParamTypeOne(String paramTypeOne) {
        this.paramTypeOne = paramTypeOne;
    }

    public String getParamNameOne() {
        return paramNameOne;
    }

    public void setParamNameOne(String paramNameOne) {
        this.paramNameOne = paramNameOne;
    }

    public String getOpChar() {
        return opChar;
    }

    public void setOpChar(String opChar) {
        this.opChar = opChar;
    }

    public String getParamTypeTwo() {
        return paramTypeTwo;
    }

    public void setParamTypeTwo(String paramTypeTwo) {
        this.paramTypeTwo = paramTypeTwo;
    }

    public String getParamNameTwo() {
        return paramNameTwo;
    }

    public void setParamNameTwo(String paramNameTwo) {
        this.paramNameTwo = paramNameTwo;
    }

    @Override
    public String toString() {
        return "Formula{" +
                "ID=" + ID +
                ", tempVari='" + tempVari + '\'' +
                ", symbol='" + symbol + '\'' +
                ", paramTypeOne=" + paramTypeOne +
                ", paramNameOne='" + paramNameOne + '\'' +
                ", opChar='" + opChar + '\'' +
                ", paramTypeTwo=" + paramTypeTwo +
                ", paramNameTwo='" + paramNameTwo + '\'' +
                '}';
    }
}
