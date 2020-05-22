package com.ponray.utils;

import com.ponray.constans.Constants;

import java.text.DecimalFormat;

public class DecimalUtils {

    private static DecimalFormat decimalFormat1=new DecimalFormat("000.00");
    private static DecimalFormat decimalFormat2=new DecimalFormat("00.000");
    private static DecimalFormat decimalFormat3=new DecimalFormat("000.00");
    private static DecimalFormat decimalFormat4=new DecimalFormat("0000.0");
    private static DecimalFormat decimalFormat5=new DecimalFormat("000.0");

    /**
     * 格式化数字，在数显框显示
     * @param fl
     * @return
     */
    public static String formatFloat(float fl){
        String strFload = Constants.STR_ZERO;
        if(fl>1000){
            strFload = decimalFormat4.format(fl);
        }else if(fl>100){
            strFload = decimalFormat1.format(fl);
        }else if(fl>10){
            strFload = decimalFormat2.format(fl);
        }else if(fl>0){
            strFload = decimalFormat3.format(fl);
        }else{
            strFload = decimalFormat5.format(fl);
        }
        return strFload;
    }
}
