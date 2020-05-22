package com.ponray.task;

import com.ponray.constans.Constants;
import com.ponray.entity.TestData;
import com.ponray.main.Main;
import com.ponray.main.UIOnline;
import com.ponray.serial.SerialPortManager;
import com.ponray.utils.ByteUtils;
import com.ponray.utils.CRC16Utils;
import com.ponray.utils.ConstantsUtils;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataTask extends ScheduledService<TestData> {
    @Override
    protected Task<TestData> createTask() {
        Task<TestData> task = new Task<TestData>() {

            @Override
            protected void updateValue(TestData value) {
                super.updateValue(value);
                System.out.println("updateValue:"+value);
            }

            @Override
            protected TestData call() throws Exception {
                //实验时间
                Main.startTime = Main.startTime + Main.periodTime;

                TestData testData = null;

                byte[] data = SerialPortManager.readFromPort(UIOnline.mSerialport);
                String hexString = ByteUtils.binaryToHexString(data);
                System.out.println(hexString);
                String[] list = hexString.split(Constants.A55A);
                if(list!=null && list.length>0) {
                    for (int i = 0; i < list.length; i++) {
                        String dataStr = list[i];
                        if (StringUtils.isNotBlank(dataStr) && dataStr.length() >= 46) {
                            if (!CRC16Utils.validateCrc16(dataStr)) {
                                continue;
                            }
                            BigInteger status = new BigInteger(dataStr.substring(0, 2), 16);
                            //存储当前试验机状态
                            BigInteger load1 = new BigInteger(dataStr.substring(2, 10), 16);
                            BigInteger load2 = new BigInteger(dataStr.substring(10, 18), 16);
                            BigInteger load3 = new BigInteger(dataStr.substring(18, 26), 16);
                            BigInteger pos = new BigInteger(dataStr.substring(26, 34), 16);
                            BigInteger transform = new BigInteger(dataStr.substring(34, 42), 16);
                            Float fload1 = Float.intBitsToFloat(load1.intValue());
                            Float fload2 = Float.intBitsToFloat(load2.intValue());
                            Float fload3 = Float.intBitsToFloat(load3.intValue());
                            Float fpos = Float.intBitsToFloat(pos.intValue());
                            Float ftransform = Float.intBitsToFloat(transform.intValue());
                            //设置峰值
                            if (fload1 > Main.topN) {
                                Main.topN = fload1;
                            }

                            if (ConstantsUtils.machineStatus.compareTo(ConstantsUtils.testIng) == 0) {
                                //实验开始状态保存数据，画曲线
                                testData = new TestData();
                                testData.setTestNum(Main.startTest.getTestNum());
                                testData.setLoadVal1(fload1);
                                testData.setLoadVal2(fload2);
                                testData.setLoadVal3(fload3);
                                testData.setPosVal(fpos);
                                testData.setTimeValue(Main.startTime + Main.periodTime);
                                testData.setDeformVal(ftransform);
                            }

                        }
                        Main.dataList.add(testData);
                        //获取到一个数据后终止循环
                        break;
                    }
                }
                return testData;
            }
        };
        return task;
    }
}
