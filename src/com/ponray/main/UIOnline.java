package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.TestData;
import com.ponray.serial.SerialPortManager;
import com.ponray.service.TestService;
import com.ponray.task.ByteTask;
import com.ponray.task.DataTask;
import com.ponray.utils.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UIOnline {
    private static Label labelSerialSet = new Label("串口设置");
    //串口
    private static ChoiceBox<String> choiceBoxSerialPort = new ChoiceBox<>();
    //波特率
    private static ChoiceBox<String> choiceBoxMBaudrate = new ChoiceBox<>();

    public static Button btnOpen = new Button("打开串口");

    // 串口列表
    public static List<String> mCommList = null;
    // 串口对象
    public static SerialPort mSerialport = null;

    private TestService testService = new TestService();

    private DataTask dataTask = new DataTask();

    public static TestData testData = null;
    //串口打开时间
    public static long openTime = 0l;


    public static Queue<byte[]> byteList =  new LinkedList<>();



    private Stage window = new Stage();
    public void display(){
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("online"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(200);
        window.setMinHeight(180);
        Scene scene = new Scene(createGridPane());
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    private GridPane createGridPane(){
        GridPane main = new GridPane();
        main.setPadding(new Insets(10));
        main.add(labelSerialSet,0,0,1,1);
        labelSerialSet.setAlignment(Pos.CENTER);
        main.add(new Label("串口"),0,1);
        main.add(choiceBoxSerialPort,1,1);
        main.add(new Label("波特率"),0,2);
        main.add(choiceBoxMBaudrate,1,2);
        main.add(btnOpen,0,3,1,1);
        btnOpen.setAlignment(Pos.CENTER);
        main.setHgap(10);
        main.setVgap(10);
        initComp();
        initData();
        registEvent();
        return main;
    }

    private void initComp(){
        choiceBoxSerialPort.setPrefSize(100,20);
        choiceBoxMBaudrate.setPrefSize(100,20);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        choiceBoxSerialPort.getItems().clear();
        choiceBoxMBaudrate.getItems().clear();
        mCommList = SerialPortManager.findPorts();
        // 检查是否有可用串口，有则加入选项中
        if (mCommList == null || mCommList.size() < 1) {
            AlertUtils.alertError("没有搜索到有效串口！");
        } else {
            for (String s : mCommList) {
                choiceBoxSerialPort.getItems().add(s);
            }
        }
        choiceBoxMBaudrate.getItems().addAll("9600","19200","38400","57600","115200");
        choiceBoxMBaudrate.setValue("19200");
    }

    /**
     * 注册事件
     */
    private void registEvent(){
        btnOpen.setOnAction(event -> {
            String text = btnOpen.getText();
            if ("打开串口".equals(text)) {
                // 获取串口名称
                String commName = choiceBoxSerialPort.getValue();
                // 获取波特率，默认为9600
                int baudrate = 9600;
                String bps = choiceBoxMBaudrate.getValue();
                baudrate = Integer.parseInt(bps);
                // 检查串口名称是否获取正确
                if (commName == null || commName.equals("")) {
                    AlertUtils.alertError("没有搜索到有效串口！");
                    return;
                }
                try {
                    mSerialport = SerialPortManager.openPort(commName, baudrate);
                    if (mSerialport == null) {
                        AlertUtils.alertError("串口对象为空，监听失败！");
                        return;
                    }
                    SerialPortManager.readFromPort(UIOnline.mSerialport);
                    //添加监听
//                    SerialPortManager.addListener(UIOnline.mSerialport, new SerialPortManager.DataAvailableListener() {
//                        @Override
//                        public void dataAvailable() {
//
//                            byte[] data = null;
//                            try {
//                                // 读取串口数据
//                                data = SerialPortManager.readFromPort(UIOnline.mSerialport);
//                                //实验开始状态
////                                if (Main.startFlag) {
//                                    // 以十六进制的形式接收数据
//                                    String hexString = ByteUtils.binaryToHexString(data);
//                                System.out.println(hexString);
////                                    String[] list = hexString.split(Constants.A55A);
////                                    if(list!=null && list.length>0){
////                                        for (int i=0;i<list.length;i++){
////                                            String dataStr = list[i];
////                                            if(StringUtils.isNotBlank(dataStr) && dataStr.length()>=46){
////                                                if(!CRC16Utils.validateCrc16(dataStr)){
////                                                    return;
////                                                }
////                                                BigInteger status = new BigInteger(dataStr.substring(0,2),16);
////                                                //存储当前试验机状态
////                                                machineStatus(status);
////                                                BigInteger load1 = new BigInteger(dataStr.substring(2,10),16);
////                                                BigInteger load2 = new BigInteger(dataStr.substring(10,18),16);
////                                                BigInteger load3 = new BigInteger(dataStr.substring(18,26),16);
////                                                BigInteger pos = new BigInteger(dataStr.substring(26,34),16);
////                                                BigInteger transform = new BigInteger(dataStr.substring(34,42),16);
////                                                Float fload1 = Float.intBitsToFloat(load1.intValue());
////                                                Float fload2 = Float.intBitsToFloat(load2.intValue());
////                                                Float fload3 = Float.intBitsToFloat(load3.intValue());
////                                                Float fpos = Float.intBitsToFloat(pos.intValue());
////                                                Float ftransform = Float.intBitsToFloat(transform.intValue());
////
////                                                if(testData==null){
////                                                    testData = new TestData();
////                                                }
////                                                testData.setLoadVal1(fload1);
////                                                testData.setLoadVal2(fload2);
////                                                testData.setLoadVal3(fload3);
////                                                testData.setPosVal(fpos);
//////                                                testData.setTimeValue(runtime);
////                                                testData.setDeformVal(ftransform);
////
////                                                //如果收到状态7，重新发送上一条命令
////                                                if(ConstantsUtils.machineStatus.compareTo(ConstantsUtils.acceptFail)==0){
////                                                    if(CommandUtils.lastCommand!=null){
////                                                        SerialPortManager.sendToPort(UIOnline.mSerialport, CommandUtils.lastCommand);
////                                                    }
////                                                }
////                                                if(Main.startFlag){
////                                                    //手动点击实验开始后才可以把数据加入list，之后进行保存
////                                                    testData.setTestNum(Main.startTest.getTestNum());
////                                                }
////                                                if(ConstantsUtils.machineStatus.compareTo(ConstantsUtils.testEnd)==0 && Main.startFlag){
////                                                    //收到实验结束状态，并且软件实验状态是正在实验中
////                                                    //先保存实验
//////                                                    Main.startTest.setRunTime(runtime);
////                                                    //运行时间大于等于设置时间，实验停止
////                                                    Main.stopTest();
////                                                }
//                                                //自动判断实验是否结束
////                                                if(Main.selectedProgram !=null && Main.selectedProgram.isTime() && Main.startFlag){
////                                                    //定时间
////                                                    if(runtime>=(Main.selectedProgram.getTimeValue()*1000)){
////                                                        //先保存实验
////                                                        Main.startTest.setRunTime(runtime);
////                                                        //运行时间大于等于设置时间，实验停止
////                                                        Main.stopTest();
////                                                    }
////                                                }
////                                                if(Main.selectedProgram !=null && Main.selectedProgram.isLoad() && Main.startFlag){
////                                                    //定力值,目前检测的是力1
////                                                    if(fload1>=Main.selectedProgram.getLoadValue()){
////                                                        //力值大于等于设定值，保存test
////                                                        Main.startTest.setRunTime(runtime);
////                                                        //实验停止
////                                                        Main.stopTest();
////                                                    }
////                                                }
////                                                if(Main.selectedProgram !=null && Main.selectedProgram.isPos() && Main.startFlag){
////                                                    //定位移
////                                                    if(fpos>=Main.selectedProgram.getPosValue()){
////                                                        //位移大于等于设定值，保存test
////                                                        Main.startTest.setRunTime(runtime);
////                                                        //实验停止
////                                                        Main.stopTest();
////                                                    }
////                                                }
////                                                if(Main.selectedProgram !=null && Main.selectedProgram.isTransform() && Main.startFlag){
////                                                    //定变形
////                                                    if(ftransform>=Main.selectedProgram.getTransformValue()){
////                                                        //变形大于等于设定值，保存test
////                                                        Main.startTest.setRunTime(runtime);
////                                                        //实验停止
////                                                        Main.stopTest();
////                                                    }
////                                                }
//
////                                            }
////                                        }
////                                    }
////                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                // 发生读取错误时显示错误信息后退出系统
//                                Platform.exit();
//                            }
//                        }
//                    });
                    ByteTask byteTask = new ByteTask();
                    byteTask.setDelay(Duration.millis(20));
                    //每隔多久运行一次
                    byteTask.setPeriod(Duration.millis(20));
                    if(!byteTask.isRunning()){
                        byteTask.start();
                    }

                    //延迟多久运行
                    dataTask.setDelay(Duration.millis(20));
                    //每隔多久运行一次
                    dataTask.setPeriod(Duration.millis(Main.periodTime));
                    if(!dataTask.isRunning()){
                        dataTask.start();
                    }
                    //打开串口成功
                    btnOpen.setText("关闭串口");
                    //设置实验开始按钮可用
                    Main.startBt.setDisable(false);
                    Main.upBt.setDisable(false);
                    Main.downBt.setDisable(false);
                    Main.resetBt.setDisable(false);
                    Main.stopBt.setDisable(false);
                    Main.button1.setDisable(false);
                    Main.button2.setDisable(false);
                    Main.button3.setDisable(false);
                    //脱机按钮可用
                    Main.offlineItem.setDisable(false);
                } catch (PortInUseException e) {
                    AlertUtils.alertError("串口已被占用！");
                }
            }
            if("关闭串口".equals(text)){
                if(mSerialport!=null){
                    SerialPortManager.closePort(mSerialport);
                    mSerialport = null;
                    mCommList = null;
                    btnOpen.setText("打开串口");
                    //初始化主界面操作按钮
                    Main.initBt();
                    //脱机按钮不可用
                    Main.offlineItem.setDisable(true);
                    //task
                    if(dataTask.isRunning()){
                        dataTask.cancel();
                        dataTask.reset();
                    }
                    AlertUtils.alertInfo("关闭串口成功");
                }
            }
        });
    }


    /**
     * 根据发上来的数据存储试验机当前状态
     * @return
     */
    private void machineStatus(BigInteger status){
        ConstantsUtils.machineStatus = status;
    }

}
