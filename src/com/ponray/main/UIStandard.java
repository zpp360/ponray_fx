package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Standard;
import com.ponray.service.StandardService;
import com.ponray.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.List;

public class UIStandard {
    private static TableView tableView = null;

    private static TableColumn<Standard,Long> numColumn = null;
    private static TableColumn<Standard,String> codeColumn = null;
    private static TableColumn<Standard,String> nameColumn = null;
    private static TableColumn<Standard,String> remarkColumn = null;

    private static Label titleLabel1 = null;
    private static Label codeLabel1 = null;
    private static Label nameLabel1 = null;
    private static Label remarkLabel1 = null;
    private static TextField codeText1 = null;
    private static TextField nameText1 = null;
    private static TextArea remarkText = null;

    private static Button addBtn = null;
    private static Button editBtn = null;
    private static Button delBtn = null;
    private static Button saveBtn = null;
    private static Button cancelBtn = null;

    private static Label titleLabel2 = null;
    private static Label codeLabel2 = null;
    private static Label nameLabel2 = null;
    private static TextField codeText2 = null;
    private static TextField nameText2 = null;

    private static Button selectBtn = null;
    private static Button allBtn = null;


    private static String OPERATION = null;

    private static  List<Standard> list = null;

    private static StandardService service = new StandardService();

    private static Standard selectStandard = null;

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
        try {
            list = service.list();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        VBox main = new VBox();
        main.setPadding(new Insets(10));
        ObservableList<Standard> data = FXCollections.observableArrayList(list);
        tableView = new TableView();
        tableView.setItems(data);
        numColumn = new TableColumn<Standard,Long>("序号");
        numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        codeColumn = new TableColumn<Standard,String>("标准代码");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn = new TableColumn<Standard,String>("标准名称");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        remarkColumn = new TableColumn<Standard,String>("备注");
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
        tableView.getColumns().clear();
        tableView.getColumns().addAll(numColumn,codeColumn,nameColumn,remarkColumn);
        tableView.setPrefHeight(300);

        HBox hBox = new HBox();
        titleLabel1 = new Label("标准信息编辑");
        codeLabel1 = new Label("标准代码");
        nameLabel1 = new Label("标准名称");
        remarkLabel1 = new Label("备注");
        remarkLabel1.setPadding(new Insets(0,25,0,0));
        codeText1 = new TextField();
        codeText1.setPrefSize(200,20);
        nameText1 = new TextField();
        nameText1.setPrefSize(200,20);
        remarkText = new TextArea();
        remarkText.setPrefSize(200,50);
        addBtn = new Button("新增");
        editBtn = new Button("修改");
        delBtn = new Button("删除");
        saveBtn = new Button("保存");
        cancelBtn = new Button("重置");
        //重置控件状态
        opReset();

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

        titleLabel2 = new Label("标准信息查询");
        codeLabel2 = new Label("标准代码");
        nameLabel2 = new Label("标准名称");
        codeText2 = new TextField();
        codeText2.setPrefSize(200,20);
        nameText2 = new TextField();
        nameText2.setPrefSize(200,20);
        selectBtn = new Button("查询");
        allBtn = new Button("显示全部");
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

        tableView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                selectStandard = (Standard)tableView.getSelectionModel().getSelectedItem();
                if(selectStandard!=null){
                    setComp(selectStandard);
                    System.out.println("Node click: " + selectStandard);
                }
            }
        });

        //添加
        addBtn.setOnAction(event -> {
            opAdd();
        });
        //修改
        editBtn.setOnAction(event -> {
            opEdit();
        });
        //删除
        delBtn.setOnAction(event -> {
            opDel();
        });
        //保存
        saveBtn.setOnAction(event -> {
            try {
                opSave();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        //取消
        cancelBtn.setOnAction(event -> {
            opReset();
        });
        //查询
        selectBtn.setOnAction(event -> {
            try {
                select();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        //显示全部
        allBtn.setOnAction(event -> {
            //清空搜索条件
            selectClear();
            //刷新列表
            refreshTable();
        });
        return main;
    }

    //查询
    private void select() throws SQLException, ClassNotFoundException {
        String code = codeText2.getText();
        String name = nameText2.getText();
        list = service.select(code,name);
        tableView.getItems().clear();
        tableView.getItems().addAll(list);
    }

    /**
     * 设置组件值
     * @param standard
     */
    private void setComp(Standard standard) {
        codeText1.setText(standard.getCode());
        nameText1.setText(standard.getName());
        remarkText.setText(standard.getRemark());
    }

    /**
     * 添加按钮
     */
    private void opAdd(){
        opClear();
        opAble();
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        delBtn.setDisable(true);
        saveBtn.setDisable(false);
        cancelBtn.setDisable(false);
        OPERATION = Constants.ADD;
    }

    /**
     * 修改按钮
     */
    private void opEdit(){
        if(selectStandard==null){
            AlertUtils.alertError("请点击列表选择标准");
            return;
        }
        opAble();
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        delBtn.setDisable(true);
        saveBtn.setDisable(false);
        cancelBtn.setDisable(false);
        OPERATION = Constants.EDIT;
    }

    /**
     * 删除按钮
     */
    private void opDel(){
        if(selectStandard==null){
            AlertUtils.alertError("请点击列表选择标准");
            return;
        }
        OPERATION = Constants.DEL;
        Boolean flag = AlertUtils.alertConfirm("确认删除此标准码？");
        if(flag){
            try {
                int result = service.del(selectStandard.getId());
                if(result>0){
                    //重置组件
                    opReset();
                    //刷新树
                    refreshTable();
                    selectStandard = null;
                    AlertUtils.alertInfo("删除标准成功");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消按钮
     */
    private void opCancel(){
        opReset();
    }

    /**
     * 保存按钮
     */
    private void opSave() throws SQLException, ClassNotFoundException {
        //添加
        if(Constants.ADD.equals(OPERATION)){
            String code = codeText1.getText().trim();
            String name = nameText1.getText().trim();
            String remark = remarkText.getText().trim();
            if(!validate(code,name,remark)){
                return;
            }
            int result = service.insert(code,name,remark);
            if(result>0){
                //重置控件
                opReset();
                //刷新tableview
                refreshTable();
                //保存成功
                AlertUtils.alertInfo("标准保存成功");
            }
        }
        //修改
        if(Constants.EDIT.equals(OPERATION)){
            String code = codeText1.getText().trim();
            String name = nameText1.getText().trim();
            String remark = remarkText.getText().trim();
            if(!validate(code,name,remark)){
                return;
            }
            int result = service.update(selectStandard.getId(),code,name,remark);
            if(result>0){
                //重置控件
                opReset();
                //刷新tableview
                refreshTable();
                //保存成功
                AlertUtils.alertInfo("标准更新成功");
            }
        }
    }


    private void opReset(){
        selectStandard = null;
        OPERATION = null;
        opClear();
        opDisable();
        addBtn.setDisable(false);
        editBtn.setDisable(false);
        delBtn.setDisable(false);
        saveBtn.setDisable(true);
        cancelBtn.setDisable(true);
    }

    /**
     * 清空操作组件
     */
    private void opClear(){
        codeText1.clear();
        nameText1.clear();
        remarkText.clear();
    }

    /**
     * 清空查询组件
     */
    private void selectClear(){
        codeText2.clear();
        nameText2.clear();
    }

    /**
     * 控件不可用
     */
    private void opDisable(){
        codeText1.setDisable(true);
        nameText1.setDisable(true);
        remarkText.setDisable(true);
    }

    /**
     * 控件可用
     */
    private void opAble(){
        codeText1.setDisable(false);
        nameText1.setDisable(false);
        remarkText.setDisable(false);
    }

    private void refreshTable(){
        try {
            list = service.list();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableView.getItems().clear();
        tableView.getItems().addAll(list);
    }

    /**
     * 验证输入信息
     * @param code
     * @param name
     * @param remark
     * @return
     */
    private boolean validate(String code, String name, String remark) {
        if(StringUtils.isBlank(code)){
            AlertUtils.alertError("请输入标准代码");
            return false;
        }

        if(StringUtils.isBlank(name)){
            AlertUtils.alertError("请输入标准名称");
            return false;
        }
        return true;
    }
}
