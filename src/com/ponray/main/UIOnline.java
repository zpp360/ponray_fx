package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.serial.SerialPortManager;
import com.ponray.utils.AlertUtils;
import com.ponray.utils.ByteUtils;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class UIOnline {
    private static Label labelSerialSet = new Label("串口设置");
    //串口
    private static ChoiceBox<String> choiceBoxSerialPort = new ChoiceBox<>();
    //波特率
    private static ChoiceBox<String> choiceBoxMBaudrate = new ChoiceBox<>();

    private static Button btnOpen = new Button("打开串口");

    // 串口列表
    private List<String> mCommList = null;
    // 串口对象
    private SerialPort mSerialport = null;

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
            // 获取串口名称
            String commName = choiceBoxSerialPort.getValue();
            // 获取波特率，默认为9600
            int baudrate = 9600;
            String bps = choiceBoxMBaudrate.getValue();
            baudrate = Integer.parseInt(bps);
            // 检查串口名称是否获取正确
            if (commName == null || commName.equals("")) {
                AlertUtils.alertError("没有搜索到有效串口！");
            } else {
                try {
                    mSerialport = SerialPortManager.openPort(commName, baudrate);
                    if (mSerialport != null) {
                        btnOpen.setText("关闭串口");
                    }
                } catch (PortInUseException e) {
                    AlertUtils.alertError("串口已被占用！");
                }
            }

            if(mSerialport!=null){
                // 添加串口监听
                SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

                    @Override
                    public void dataAvailable() {
                        byte[] data = null;
                        try {
                            if (mSerialport == null) {
                                AlertUtils.alertError("串口对象为空，监听失败！");
                            } else {
                                // 读取串口数据
                                data = SerialPortManager.readFromPort(mSerialport);

                                // 以十六进制的形式接收数据
                                String hexString = ByteUtils.byteArrayToHexString(data);
                            }
                        } catch (Exception e) {
                            AlertUtils.alertError(e.toString());
                            // 发生读取错误时显示错误信息后退出系统
                            Platform.exit();
                        }
                    }
                });
            }
        });
    }
}
