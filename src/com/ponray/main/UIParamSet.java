package com.ponray.main;

import com.ponray.constans.Constans;
import com.ponray.service.MachineService;
import com.ponray.utils.DataMap;
import com.ponray.utils.ValidateUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.sql.*;


/**
 * 参数设计弹出框界面
 */
public class UIParamSet {

    private static String flag = "";


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
                    alert(Alert.AlertType.WARNING,"请填写设备名称");
                    return;
                }
                if(StringUtils.isBlank(modelNumber)){
                    alert(Alert.AlertType.WARNING,"请填写设备型号");
                    return;
                }
                if(StringUtils.isBlank(specification)){
                    alert(Alert.AlertType.WARNING,"请填写设备规格");
                    return;
                }
                if(StringUtils.isNotBlank(maxSpeed)){
                    //最大速度不为空
                    if(!ValidateUtils.zIndex(maxSpeed)){
                        alert(Alert.AlertType.WARNING,"最大速度为正整数");
                        return;
                    }

                }
                if(StringUtils.isNotBlank(precision)){
                    //最大速度不为空
                    if(!ValidateUtils.posttiveFloat(maxSpeed)){
                        alert(Alert.AlertType.WARNING,"系统精度为小数");
                        return;
                    }

                }
                if(StringUtils.isNotBlank(createTime)){
                    //最大速度不为空
                    if(!ValidateUtils.isDateTime("yyyyMMdd",createTime)){
                        alert(Alert.AlertType.WARNING,"请输入正确的时间格式，例如20200304");
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
                            alert(Alert.AlertType.INFORMATION,"更新成功");
                        }
                    }else{
                        int result = machineService.insert(name,modelNumber,specification,maxSpeed,precision,serialNum,createTime);
                        if(result>0){
                            //插入成功
                            alert(Alert.AlertType.INFORMATION,"更新成功");
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
    private BorderPane createTab3(){
        BorderPane borderPane = new BorderPane();
        Label nameLabel = new Label("设备名称:");
        TextField nameText = new TextField();
        //设置隐藏
        nameText.setVisible(false);
        nameText.setPrefSize(150,20);
        String[] nameList = new String[]{"a","b"};
        ChoiceBox<String> nameChoice = new ChoiceBox<String>(FXCollections.observableArrayList(nameList));
        nameChoice.setValue("a");
        nameChoice.setPrefSize(150,20);
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(nameLabel,nameChoice,nameText);
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


        Button addBtn = new Button("新增");
        Button editBtn = new Button("修改");
        Button delBtn = new Button("删除");
        Button saveBtn = new Button("保存");
        Button cancelBtn = new Button("取消");

        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().addAll(addBtn,editBtn,delBtn,saveBtn,cancelBtn);
        bottomHBox.setSpacing(10);
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);


        borderPane.setTop(vBox);
        borderPane.setBottom(bottomHBox);
        borderPane.setPadding(new Insets(10));


        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameChoice.setDisable(true);
                nameText.setVisible(true);
                editBtn.setDisable(true);
                delBtn.setDisable(true);
                //添加操作
                flag = "add";



            }
        });

        //保存
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });


        return borderPane;
    }


    private void alert(Alert.AlertType type,String msg){
        Alert alert = new Alert(type);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }

}
