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
    @Override
    protected Task<TestData> createTask() {
        Task<TestData> task = new Task<TestData>() {

            @Override
            protected void updateValue(TestData value) {
                super.updateValue(value);
                if (value != null) {
                    Main.lableNum1.setText(DecimalUtils.formatFloat(value.getLoadVal1()));
                    Main.lableNum2.setText(DecimalUtils.formatFloat(value.getPosVal()));
                    Main.lableNum3.setText(DecimalUtils.formatFloat(value.getDeformVal()));
                    if (Main.startFlag) {
                        //实验开始状态下计时
                        Main.lableNum4.setText(DecimalUtils.formatDouble((double) value.getTimeValue() / 1000));
                    } else {
                        Main.lableNum4.setText(DecimalUtils.formatFloat(0F));
                    }
                    Main.labelTop.setText(DecimalUtils.formatFloat(Main.topN));
                    if (Main.startFlag) {
                        //实验开始画曲线
//                        TestData testData = value;//复制一份，否则报错java.util.ConcurrentModificationException
                        Main.updateSeries(value);
                        TestData data = value;
                        Main.dataList.add(data);
                    }
                }

            }

            @Override
            protected TestData call() throws Exception {
                TestData testData = null;
                if (UIOnline.mSerialport != null) {
                    byte[] bytes = UIOnline.byteList.poll();
                    String dataStr = ByteUtils.binaryToHexString(UIOnline.byteList.poll());
                    System.out.println(dataStr);
                    if (!CRC16Utils.validateCrc16(bytes)) {
                        return testData;
                    }
                    byte[] byte4 = new byte[4];
                    System.arraycopy(bytes, 3, byte4, 0, 4);
                    Float fload1 = ByteUtils.getFloat(byte4, 0);
                    System.out.println(fload1);
                    System.arraycopy(bytes, 7, byte4, 0, 4);
                    Float fload2 = ByteUtils.byte2float(byte4, 0);
                    System.arraycopy(bytes, 11, byte4, 0, 4);
                    Float fload3 = ByteUtils.byte2float(byte4, 0);
                    System.arraycopy(bytes, 16, byte4, 0, 4);
                    Float fpos = ByteUtils.byte2float(byte4, 0);
                    System.arraycopy(bytes, 20, byte4, 0, 4);
                    Float ftransform = ByteUtils.byte2float(byte4, 0);
                    testData = new TestData();
                    testData.setLoadVal1(fload1);
                    testData.setLoadVal2(fload2);
                    testData.setLoadVal3(fload3);
                    testData.setPosVal(fpos);
                    testData.setDeformVal(ftransform);

                    //设置峰值
                    if (fload1 > Main.topN) {
                        Main.topN = fload1;
                    }
                    //实验时间
                    if (Main.startFlag) {
                        Main.startTime = Main.startTime + Main.periodTime;
                        //设置实验时间
                        testData.setTimeValue(Main.startTime);
                        //实验编号
                        UIOnline.testData.setTestNum(Main.startTest.getTestNum());

                        if (Constants.KQL.equals(Main.testName)) {
                            //停机条件
                            //峰值*50% + 当前力   大于  峰值
                            boolean flag = false;
                            if (fload1 > Main.selectedProgram.getGtForce()) {
                                flag = true;
                            }
                            if (flag) {
                                float a = Main.topN * Main.selectedProgram.getLtRate() / 100 + fload1;
                                System.out.println(a);
                                System.out.println("峰值：" + Main.topN);
                                if (a < Main.topN) {
                                    Main.stopTestAndSave();
                                }
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
