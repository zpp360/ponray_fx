package com.ponray.utils;

import com.ponray.constans.Constants;

import java.math.BigInteger;
import java.text.DecimalFormat;

public class ConstantsUtils {

    /**
     * 当前试验机状态,默认0
     */
    public static BigInteger machineStatus = new BigInteger(Constants.STR_ZERO);

    public static BigInteger testAwait = new BigInteger("0");

    /**
     * 表示实验结束
     */
    public static BigInteger testEnd = new BigInteger("4");

    /**
     * 表示正在进行实验
     */
    public static BigInteger testIng = new BigInteger("5");

    public static BigInteger acceptFail = new BigInteger("7");





    public static void main(String[] args){
        DecimalFormat decimalFormat3=new DecimalFormat("000.00");
        System.out.println(decimalFormat3.format(-100F));
    }

}
