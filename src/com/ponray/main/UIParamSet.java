package com.ponray.main;

import com.ponray.constans.Constans;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * 参数设计弹出框界面
 */
public class UIParamSet {

    public void display(){
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
        TextField nameText = new TextField();
        TextField mnText = new TextField();
        TextField spText = new TextField();
        TextField speedText = new TextField();
        TextField preText = new TextField();
        TextField numText = new TextField();
        TextField timeText = new TextField();

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

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox1,hBox2,hBox3,hBox4,hBox5,hBox6,hBox7);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20,150,20,100));
        tab1.setContent(vBox);



        Scene scene = new Scene(tabPane);
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();

    }

}
