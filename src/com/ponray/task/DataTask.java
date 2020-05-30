package com.ponray.task;

import com.ponray.constans.Constants;
import com.ponray.entity.TestData;
import com.ponray.main.Main;
import com.ponray.main.UIOnline;
import com.ponray.serial.SerialPortManager;
import com.ponray.utils.*;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.math.BigInteger;

public class DataTask extends ScheduledService<TestData> {
    private static int count = 0;
    private static boolean flag = false;
    @Override
    protected Task<TestData> createTask() {
        Task<TestData> task = new Task<TestData>() {

            @Override
            protected void updateValue(TestData value) {
                super.updateValue(value);
                if (value != null) {
                    if(count%4==0){
                        Main.lableNum1.setText(DecimalUtils.formatFloat(value.getLoadVal1()));
                        Main.lableNum2.setText(DecimalUtils.formatFloat(value.getPosVal()));
                        Main.lableNum3.setText(DecimalUtils.formatFloat(value.getDeformVal()));
                        Main.labelTop.setText(DecimalUtils.formatFloat(Main.topN));
                        if(Main.startFlag){
                            //实验开始状态下计时
                            Main.lableNum4.setText(DecimalUtils.formatDouble((double) value.getTimeValue() / 1000));
                        }
                        count = 0;
                    }
                    if (Main.startFlag) {
                        Main.updateSeries(value);
                        Main.dataList.add(value);
                    }
                }

            }

            @Override
            protected TestData call() throws Exception {
                TestData testData = null;
                if (UIOnline.mSerialport != null) {
                    long start = System.currentTimeMillis();
                    byte[] bytes = null;
                    if(UIOnline.byteList.size()>0){
                        bytes = UIOnline.byteList.get(UIOnline.byteList.size()-1);
                        UIOnline.byteList.clear();
                    }else{
                        return testData;
                    }
                    if (!CRC16Utils.validateCrc16(bytes)) {
                        return testData;
                    }
                    byte[] byte4 = new byte[4];
                    System.arraycopy(bytes, 3, byte4, 0, 4);
                    Float fload1 = ByteUtils.getFloat(byte4, 0);
                    System.arraycopy(bytes, 7, byte4, 0, 4);
                    Float fload2 = ByteUtils.getFloat(byte4, 0);
                    System.arraycopy(bytes, 11, byte4, 0, 4);
                    Float fload3 = ByteUtils.getFloat(byte4, 0);
                    System.arraycopy(bytes, 15, byte4, 0, 4);
                    Float fpos = ByteUtils.getFloat(byte4, 0);
                    System.arraycopy(bytes, 19, byte4, 0, 4);
                    Float ftransform = ByteUtils.getFloat(byte4, 0);
                    testData = new TestData();
                    testData.setLoadVal1(fload1);
                    testData.setLoadVal2(fload2);
                    testData.setLoadVal3(fload3);
                    testData.setPosVal(fpos);
                    //变形取绝对值
                    testData.setDeformVal(Math.abs(ftransform));

                    //设置峰值
                    if (fload1 > Main.topN) {
                        Main.topN = fload1;
                    }
                    //计数器加1
                    count++;
                    //实验时间
                    if (Main.startFlag) {
                        Main.startTime = Main.startTime + Main.periodTime;
                        //设置实验时间
                        testData.setTimeValue(Main.startTime);
                        //实验编号
                        testData.setTestNum(Main.startTest.getTestNum());
                        //自动判断断裂
                        if (Main.selectedProgram.isAutoBreakage()) {
                            //停机条件
                            //峰值*50% + 当前力   大于  峰值
                            if (fload1 > Main.selectedProgram.getGtForce()) {
                                flag = true;
                            }
                            if (flag) {
                                float a = Main.topN * Main.selectedProgram.getLtRate() / 100 + fload1;
                                if (a < Main.topN) {
                                    Main.stopTestAndSave();
                                    flag = false;
                                }
                            }
                        }

                        if(Constants.CCL.equals(Main.testName)){
                            //穿刺力
                            String deep = Main.selectedUserParam.get(Constants.CCL_DEEP);
                            Float fDeep = Float.parseFloat(deep);
                            if(Math.abs(fpos)>=fDeep){
                                //穿刺深度到设定值，停止实验
                                Main.stopTestAndSave();
                                flag = false;
                            }
                        }
                    }
                    UIOnline.openTime = UIOnline.openTime + Main.periodTime;
                }
                return testData;
            }
        };
        return task;
    }
}
