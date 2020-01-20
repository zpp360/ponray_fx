package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UIStandard {

    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("experiment_standard"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(600);
        window.setMinHeight(500);
        Scene scene = new Scene(createVBox());
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    private VBox createVBox(){
        List<Standard> list = new ArrayList<>();
        VBox main = new VBox();
        main.setPadding(new Insets(10));
        ObservableList<Standard> data = FXCollections.observableArrayList(list);
        TableView tableView = new TableView();
        tableView.setItems(data);
        TableColumn numColumn = new TableColumn("序号");
        TableColumn codeColumn = new TableColumn("标准代码");
        TableColumn nameColumn = new TableColumn("标准名称");
        TableColumn remarkColumn = new TableColumn("备注");
        tableView.getColumns().addAll(numColumn,codeColumn,nameColumn,remarkColumn);
        tableView.setPrefHeight(300);

        HBox hBox = new HBox();
        Label titleLabel1 = new Label("标准信息编辑");
        Label codeLabel1 = new Label("标准代码");
        Label nameLabel1 = new Label("标准名称");
        Label remarkLabel1 = new Label("备注");
        remarkLabel1.setPadding(new Insets(0,25,0,0));
        TextField codeText1 = new TextField();
        codeText1.setPrefSize(200,20);
        TextField nameText1 = new TextField();
        nameText1.setPrefSize(200,20);
        TextArea remarkText = new TextArea();
        remarkText.setPrefSize(200,50);
        Button addBtn = new Button("新增");
        Button editBtn = new Button("修改");
        Button delBtn = new Button("删除");
        Button saveBtn = new Button("保存");
        Button cancelBtn = new Button("取消");
        cancelBtn.setDisable(true);

        HBox hBox11 = new HBox();
        hBox11.getChildren().addAll(codeLabel1,codeText1);
        hBox11.setSpacing(10);
        HBox hBox12 = new HBox();
        hBox12.getChildren().addAll(nameLabel1,nameText1);
        hBox12.setSpacing(10);
        HBox hBox13 = new HBox();
        hBox13.getChildren().addAll(remarkLabel1,remarkText);
        hBox13.setSpacing(10);
        HBox hbox14 = new HBox();
        hbox14.setSpacing(10);
        hbox14.getChildren().addAll(addBtn,editBtn,delBtn,saveBtn,cancelBtn);
        VBox opVBox = new VBox();
        opVBox.setSpacing(15);
        opVBox.setPadding(new Insets(0,0,0,0));
        opVBox.getChildren().addAll(titleLabel1,hBox11,hBox12,hBox13,hbox14);

        Label titleLabel2 = new Label("标准信息查询");
        Label codeLabel2 = new Label("标准代码");
        Label nameLabel2 = new Label("标准名称");
        TextField codeText2 = new TextField();
        codeText2.setPrefSize(200,20);
        TextField nameText2 = new TextField();
        nameText2.setPrefSize(200,20);
        Button selectBtn = new Button("查询");
        Button allBtn = new Button("显示全部");
        HBox hBox21 = new HBox();
        hBox21.getChildren().addAll(codeLabel2,codeText2);
        hBox21.setSpacing(10);
        HBox hBox22 = new HBox();
        hBox22.getChildren().addAll(nameLabel2,nameText2);
        hBox22.setSpacing(10);
        HBox hBox23 = new HBox();
        hBox23.getChildren().addAll(selectBtn,allBtn);
        hBox23.setSpacing(10);
        VBox selectVBox = new VBox();
        selectVBox.setSpacing(15);
        selectVBox.setPadding(new Insets(0,0,0,50));
        selectVBox.getChildren().addAll(titleLabel2,hBox21,hBox22,hBox23);

        hBox.getChildren().addAll(opVBox,selectVBox);
        hBox.setPadding(new Insets(30,0,0,0));

        main.getChildren().addAll(tableView,hBox);
        return main;
    }
}
