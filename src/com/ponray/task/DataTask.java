package com.ponray.task;

import com.ponray.entity.TestData;
import com.ponray.main.Main;
import com.ponray.main.UIOnline;
import com.ponray.utils.*;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class DataTask extends ScheduledService<TestData> {
    @Override
    protected Task<TestData> createTask() {
        Task<TestData> task = new Task<TestData>() {

            @Override
            protected void updateValue(TestData value) {
                super.updateValue(value);
                if(value!=null){
                    Main.lableNum1.setText(DecimalUtils.formatFloat(value.getLoadVal1()));
                    Main.lableNum2.setText(DecimalUtils.formatFloat(value.getPosVal()));
                    Main.lableNum3.setText(DecimalUtils.formatFloat(value.getDeformVal()));
                    if(Main.startFlag){
                        //实验开始状态下计时
                        Main.lableNum4.setText(DecimalUtils.formatDouble((double)value.getTimeValue()/1000));
                    }else{
                        Main.lableNum4.setText(DecimalUtils.formatFloat(0F));
                    }
                    //设置峰值
                    if(value.getLoadVal1()>Main.topN){
                        Main.topN = value.getLoadVal1();
                    }
                    Main.labelTop.setText(DecimalUtils.formatFloat(Main.topN));
                    if(Main.startFlag){
                        //实验开始画曲线
                        Main.updateSeries(value);
                    }
                }

            }

            @Override
            protected TestData call() throws Exception {
                if(UIOnline.mSerialport!=null) {
                    //实验时间
                    if(Main.startFlag){
                        Main.startTime = Main.startTime + Main.periodTime;
                        UIOnline.testData.setTimeValue(Main.startTime);
                    }
                    UIOnline.openTime = UIOnline.openTime + Main.periodTime;
                }
                return UIOnline.testData;
            }
        };
        return task;
    }
}
