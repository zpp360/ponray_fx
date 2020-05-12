package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.TestData;
import com.ponray.serial.SerialPortManager;
import com.ponray.service.TestService;
import com.ponray.utils.AlertUtils;
import com.ponray.utils.ByteUtils;
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
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

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

    private static DecimalFormat decimalFormat1=new DecimalFormat("0.0000");
    private static DecimalFormat decimalFormat2=new DecimalFormat("00.000");
    private static DecimalFormat decimalFormat3=new DecimalFormat("000.00");

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
        choiceBoxMBaudrate.setValue("9600");
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
                    //添加监听
                    SerialPortManager.addListener(UIOnline.mSerialport, new SerialPortManager.DataAvailableListener() {
                        @Override
                        public void dataAvailable() {
                            byte[] data = null;
                            try {
                                // 读取串口数据
                                data = SerialPortManager.readFromPort(UIOnline.mSerialport);
                                //实验开始状态
                                if (Main.startFlag) {
                                    // 以十六进制的形式接收数据
                                    String hexString = ByteUtils.byteArrayToHexString(data);
                                    String[] list = hexString.split(Constants.A55A);
                                    if(list!=null && list.length>0){
                                        for (int i=0;i<list.length;i++){
                                            String dataStr = list[i];
                                            if(StringUtils.isNotBlank(dataStr) && dataStr.length()>=40){
                                                BigInteger load1 = new BigInteger(dataStr.substring(0,8),16);
                                                BigInteger load2 = new BigInteger(dataStr.substring(8,16),16);
                                                BigInteger load3 = new BigInteger(dataStr.substring(16,24),16);
                                                BigInteger pos = new BigInteger(dataStr.substring(24,32),16);
                                                BigInteger transform = new BigInteger(dataStr.substring(32,40),16);
                                                Float fload1 = Float.intBitsToFloat(load1.intValue());
                                                Float fload2 = Float.intBitsToFloat(load2.intValue());
                                                Float fload3 = Float.intBitsToFloat(load3.intValue());
                                                Float fpos = Float.intBitsToFloat(pos.intValue());
                                                Float ftransform = Float.intBitsToFloat(transform.intValue());
                                                //设置峰值
                                                if(Main.topN==null || fload1>Main.topN){
                                                    Main.topN = fload1;
                                                }
                                                //当前时间
                                                Long nowTime = System.currentTimeMillis();
                                                Long runtime = nowTime-Main.startTime;
                                                TestData testData = new TestData();
                                                testData.setLoadVal1(fload1);
                                                testData.setLoadVal2(fload2);
                                                testData.setLoadVal3(fload3);
                                                testData.setPosVal(fpos);
                                                testData.setTimeValue(runtime);
                                                testData.setDeformVal(ftransform);
                                                testData.setTestNum(Main.startTest.getTestNum());
                                                Main.dataList.add(testData);

                                                //主界面值设置
                                                String strFload1 = formatFloat(fload1);
                                                String strFpos = formatFloat(fpos);
                                                String strFtransform = formatFloat(ftransform);
                                                String strRuntime = formatFloat((float)runtime);
                                                String strTop = formatFloat(Main.topN);
                                                System.out.println(fload1);
                                                System.out.println("力："+strFload1);
                                                System.out.println("位移："+strFpos);
                                                System.out.println("变形："+strFtransform);
                                                System.out.println("时间："+strRuntime);
                                                System.out.println("峰值："+strTop);
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Main.lableNum1.setText(strFload1);
                                                        Main.lableNum2.setText(strFpos);
                                                        Main.lableNum3.setText(strFtransform);
                                                        Main.lableNum4.setText(strRuntime);
                                                        Main.labelTop.setText(strTop);
                                                    }
                                                });
                                                //更新折线
                                                Main.updateSeries(testData);
                                                //自动判断实验是否结束
                                                if(Main.selectedProgram.isTime()){
                                                    //定时间
                                                    if(runtime>=(Main.selectedProgram.getTimeValue()*1000)){
                                                        //先保存实验
                                                        Main.startTest.setRunTime(runtime);
                                                        testService.insert(Main.startTest);
                                                        //运行时间大于等于设置时间，实验停止
                                                        Main.stopTest();

                                                    }
                                                }
                                                if(Main.selectedProgram.isLoad()){
                                                    //定力值,目前检测的是力1
                                                    if(fload1>=Main.selectedProgram.getLoadValue()){
                                                        //力值大于等于设定值，保存test
                                                        Main.startTest.setRunTime(runtime);
                                                        testService.insert(Main.startTest);
                                                        //实验停止
                                                        Main.stopTest();
                                                    }
                                                }
                                                if(Main.selectedProgram.isPos()){
                                                    //定位移
                                                    if(fpos>=Main.selectedProgram.getPosValue()){
                                                        //位移大于等于设定值，保存test
                                                        Main.startTest.setRunTime(runtime);
                                                        testService.insert(Main.startTest);
                                                        //实验停止
                                                        Main.stopTest();
                                                    }
                                                }
                                                if(Main.selectedProgram.isTransform()){
                                                    //定变形
                                                    if(ftransform>=Main.selectedProgram.getTransformValue()){
                                                        //变形大于等于设定值，保存test
                                                        Main.startTest.setRunTime(runtime);
                                                        testService.insert(Main.startTest);
                                                        //实验停止
                                                        Main.stopTest();
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                AlertUtils.alertError(e.toString());
                                // 发生读取错误时显示错误信息后退出系统
                                Platform.exit();
                            }
                        }
                    });
                    //打开串口成功
                    btnOpen.setText("关闭串口");
                    //设置实验开始按钮可用
                    Main.startBt.setDisable(false);
                    Main.upBt.setDisable(false);
                    Main.downBt.setDisable(false);
                    Main.resetBt.setDisable(false);
                    Main.clearLoadBt.setDisable(false);
                    Main.clearPosBt.setDisable(false);
                    Main.clearTransformBt.setDisable(false);
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
                    AlertUtils.alertInfo("关闭串口成功");
                }
            }
        });
    }

    /**
     * 对float值进行format
     * @return
     */
    private String formatFloat(float fl){
        String strFload = Constants.STR_ZERO;
        if(fl>100){
            strFload = decimalFormat1.format(fl);
        }else if(fl>10){
            strFload = decimalFormat2.format(fl);
        }else if(fl>0){
            strFload = decimalFormat3.format(fl);
        }
        return strFload;
    }

}
