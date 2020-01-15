package com.ponray.main;

import com.ponray.constans.Constans;
import com.ponray.service.ForceSensorService;
import com.ponray.service.MachineService;
import com.ponray.utils.AlertUtils;
import com.ponray.utils.DataMap;
import com.ponray.utils.ValidateUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.List;


/**
 * 参数设计弹出框界面
 */
public class UIParamSet {

    //力选择器操作标志select代表选择，add代表添加，edit代表修改
    private static String flag = "select";

    //已选择的力选择器index，-1为还未选择
    private static int choiceSelect = -1;

    //力传感器
    private static List<DataMap> list = null;

    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constans.language.getProperty("menu_hardware_param"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(500);
        window.setMinHeight(400);

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("主机参数");
        tab1.setClosable(false);
        Tab tab2 = new Tab("位移传感器");
        tab2.setClosable(false);
        Tab tab3 = new Tab("力传感器");
        tab3.setClosable(false);
        Tab tab4 = new Tab("引伸计");
        tab4.setClosable(false);

        tabPane.getTabs().addAll(tab1,tab2,tab3,tab4);
        tabPane.setPrefSize(500,400);


        Label nameLabel = new Label("设备名称:");
        Label mnLabel = new Label("设备型号:");
        Label spLabel = new Label("设备规格:");
        Label speedLabel = new Label("最大速度:");
        Label preLabel = new Label("系统精度:");
        Label numLabel = new Label("出产编号:");
        Label timeLabel = new Label("出产日期:");
        MachineService machineService = new MachineService();
        DataMap map = machineService.findMachine();
        TextField nameText = new TextField(map==null?"":map.getString("name"));
        TextField mnText = new TextField(map==null?"":map.getString("model_number"));
        TextField spText = new TextField(map==null?"":map.getString("specification"));
        TextField speedText = new TextField(map==null?"":map.getInt("max_speed")+"");
        TextField preText = new TextField(map==null?"":map.getDouble("precision")+"");
        TextField numText = new TextField(map==null?"":map.getString("serial_number"));
        TextField timeText = new TextField(map==null?"":map.getString("create_time"));

        Button machineEditBtn = new Button("修改");
        machineEditBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameText.getText().trim();
                String modelNumber = mnText.getText().trim();
                String specification = spText.getText().trim();
                String maxSpeed = speedText.getText().trim();
                String precision = preText.getText().trim();
                String serialNum = numText.getText().trim();
                String createTime = timeText.getText().trim();
                if(StringUtils.isBlank(name)){
                    AlertUtils.alertError("请填写设备名称");
                    return;
                }
                if(StringUtils.isBlank(modelNumber)){
                    AlertUtils.alertError("请填写设备型号");
                    return;
                }
                if(StringUtils.isBlank(specification)){
                    AlertUtils.alertError("请填写设备规格");
                    return;
                }
                if(StringUtils.isNotBlank(maxSpeed)){
                    //最大速度不为空
                    if(!ValidateUtils.zIndex(maxSpeed)){
                        AlertUtils.alertError("最大速度为正整数");
                        return;
                    }

                }
                if(StringUtils.isNotBlank(precision)){
                    //最大速度不为空
                    if(!ValidateUtils.posttiveFloat(maxSpeed)){
                        AlertUtils.alertError("系统精度为小数");
                        return;
                    }

                }
                if(StringUtils.isNotBlank(createTime)){
                    //最大速度不为空
                    if(!ValidateUtils.isDateTime("yyyyMMdd",createTime)){
                        AlertUtils.alertError("请输入正确的时间格式，例如20200304");
                        return;
                    }

                }

                DataMap map = null;
                try {
                    map = machineService.findMachine();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //修改
                try {
                    if(map!=null && !map.isEmpty()){
                        //存在数据
                        long ID = map.getLong("ID");
                        int result = machineService.update(ID,name,modelNumber,specification,maxSpeed,precision,serialNum,createTime);
                        if(result>0){
                            //更新成功
                            AlertUtils.alertInfo("更新成功");
                        }
                    }else{
                        int result = machineService.insert(name,modelNumber,specification,maxSpeed,precision,serialNum,createTime);
                        if(result>0){
                            //插入成功
                            AlertUtils.alertInfo("更新成功");
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(nameLabel,nameText);
        hBox1.setSpacing(20);
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(mnLabel,mnText);
        hBox2.setSpacing(20);
        HBox hBox3 = new HBox();
        hBox3.getChildren().addAll(spLabel,spText);
        hBox3.setSpacing(20);
        HBox hBox4 = new HBox();
        hBox4.getChildren().addAll(speedLabel,speedText);
        hBox4.setSpacing(20);
        HBox hBox5 = new HBox();
        hBox5.getChildren().addAll(preLabel,preText);
        hBox5.setSpacing(20);
        HBox hBox6 = new HBox();
        hBox6.getChildren().addAll(numLabel,numText);
        hBox6.setSpacing(20);
        HBox hBox7 = new HBox();
        hBox7.getChildren().addAll(timeLabel,timeText);
        hBox7.setSpacing(20);
        HBox hBox8 = new HBox();
        hBox8.getChildren().addAll(machineEditBtn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox1,hBox2,hBox3,hBox4,hBox5,hBox6,hBox7,hBox8);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,150,10,100));
        tab1.setContent(vBox);

        tab3.setContent(createTab3());





        Scene scene = new Scene(tabPane);
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();

    }


    /**
     * 力传感器tab
     * @return
     */
    private BorderPane createTab3() throws SQLException, ClassNotFoundException {
        //获取列表
        ForceSensorService forceSensorService = new ForceSensorService();
        list = forceSensorService.list();


        BorderPane borderPane = new BorderPane();
        Label nameLabel = new Label("设备名称:");
        TextField nameText = new TextField();
        //设置隐藏
        nameText.setVisible(false);
        nameText.setPrefSize(150,20);
        ChoiceBox<String> nameChoice = new ChoiceBox<String>();

        nameChoice.setValue("a");
        nameChoice.setPrefSize(150,20);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(nameText,nameChoice);
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(nameLabel,stackPane);
        hBox1.setSpacing(10);

        Label nLabel = new Label("标准量程:");
        TextField nText = new TextField();
        nText.setPrefSize(150,20);
        Label tipLabel = new Label("N");
        tipLabel.setPadding(new Insets(5,0,0,0));
        HBox hBox22 = new HBox();
        hBox22.getChildren().addAll(nText,tipLabel);
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(nLabel,hBox22);
        hBox2.setSpacing(10);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox1,hBox2);
        vBox.setSpacing(15);

        //初始化数据
        updateChoice(nameChoice,nameText,nText,list);


        Button addBtn = new Button("新增");
        Button editBtn = new Button("修改");
        Button delBtn = new Button("删除");
        Button saveBtn = new Button("保存");
        Button cancelBtn = new Button("取消");
        cancelBtn.setDisable(true);

        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().addAll(addBtn,editBtn,delBtn,saveBtn,cancelBtn);
        bottomHBox.setSpacing(10);
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);


        borderPane.setTop(vBox);
        borderPane.setBottom(bottomHBox);
        borderPane.setPadding(new Insets(10));

        nameChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    nameText.setText(list.get(newValue.intValue()).getString("name"));
                    nText.setText(list.get(newValue.intValue()).getInt("range")+"");
                    choiceSelect = newValue.intValue();
                }
            }
        });
        //点击添加按钮
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameChoice.setVisible(false);
                nameText.setVisible(true);
                addBtn.setDisable(true);
                editBtn.setDisable(true);
                delBtn.setDisable(true);
                saveBtn.setDisable(false);
                cancelBtn.setDisable(false);
                //情况text
                nameText.setText("");
                nText.setText("");
                //添加操作
                flag = "add";



            }
        });
        //点击修改按钮
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameChoice.setVisible(false);
                nameText.setVisible(true);
                addBtn.setDisable(true);
                editBtn.setDisable(true);
                delBtn.setDisable(true);
                saveBtn.setDisable(false);
                cancelBtn.setDisable(false);
                //修改操作
                flag = "edit";
            }
        });

        //保存
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(flag == "select"){
                        //选择
                        DataMap data = list.get(choiceSelect);
                        int res = forceSensorService.selected(data.getLong("ID"));
                        if (res>0){
                            //选中成功
                            nameChoice.setValue(data.getString("name"));
                            AlertUtils.alertInfo("选择成功");
                        }
                    }
                    if(flag=="add"){
                        //添加操作
                        String name = nameText.getText().trim();
                        //标准量程
                        String range = nText.getText().trim();
                        if(!validateForceSensor(name,range)){
                            return;
                        }
                        int res = forceSensorService.insert(name,Integer.parseInt(range));
                        //更新列表
                        list = forceSensorService.list();
                        updateChoice(nameChoice,nameText,nText,list);
                        if (res>0){
                            AlertUtils.alertInfo("添加成功");
                        }
                    }
                    if(flag=="edit"){
                        //修改操作
                        DataMap data = list.get(choiceSelect);
                        String name = nameText.getText().trim();
                        //标准量程
                        String range = nText.getText().trim();
                        if(!validateForceSensor(name,range)){
                            return;
                        }
                        int res = forceSensorService.update(data.getLong("ID"),name,Integer.valueOf(range));
                        if (res>0){
                            AlertUtils.alertInfo("修改成功");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        //删除操作
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!AlertUtils.alertConfirm("确认删除吗？")){
                    return;
                }
                DataMap data = list.get(choiceSelect);
                try {
                    int res = forceSensorService.del(data.getLong("ID"));
                    if(res>0){
                        AlertUtils.alertInfo("删除成功");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        //取消按钮
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //新增和修改按钮可用
                addBtn.setDisable(false);
                editBtn.setDisable(false);
                delBtn.setDisable(false);
                //选择框可见
                nameChoice.setVisible(true);
                //名称输入框隐藏
                nameText.setVisible(false);
                //保存按钮可用和删除按钮不可用
                saveBtn.setDisable(false);
                cancelBtn.setDisable(true);
                updateChoice(nameChoice,nameText,nText,list);
                //flag重置为选择select
                flag = "select";
            }
        });

        return borderPane;
    }

    /**
     * 更新choice力选择框
     * @param choice
     * @param nameText
     * @param nText
     * @param list
     */
    private void updateChoice(ChoiceBox choice,TextField nameText,TextField nText, List<DataMap> list) {
        //先清空
        choice.getItems().clear();
        if(list!=null && list.size()>0){
            for (DataMap data:list){
                int i = 0;
                choice.getItems().add(data.getString("name"));
                if(data.getBoolean("selected")){
                    choice.setValue(data.getString("name"));
                    nameText.setText(data.getString("name"));
                    nText.setText(data.getInt("range")+"");
                    choiceSelect = i++;
                }
            }
        }
    }


    /**
     * 验证力传感器名称和标准量程
     * @param name
     * @param range
     * @return
     */
    private boolean validateForceSensor(String name,String range){
        if(StringUtils.isBlank(name)){
            AlertUtils.alertError("请输入传感器名称");
            return false;
        }
        if(StringUtils.isBlank(range)){
            AlertUtils.alertError("请输入标准量程");
            return false;
        }

        if(!ValidateUtils.zIndex(range)){
            AlertUtils.alertError("标准量程为正整数");
            return false;
        }

        return true;
    }

}
