package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Unit;
import com.ponray.enums.UnitType;
import com.ponray.service.UnitService;
import com.ponray.utils.AlertUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.List;

public class UIUnit {

    private static TableView<Unit> tableView = new TableView();
    private static TableColumn<Unit,String> columnType = new TableColumn<>("单位类型");
    private static TableColumn<Unit,String> columnCode = new TableColumn<>("单位符号");
    private static TableColumn<Unit,String> columnParam = new TableColumn<>("比例系数");
    private static TableColumn<Unit,String> columnBaseCode = new TableColumn<>("基本单位");

    private static Label labelType = new Label("所属类型");
    private static Label labelCode = new Label("单位符号");
    private static Label labelParam = new Label("比例系数");
    private static Label labelBaseCode = new Label("基本单位符号");

    private static ChoiceBox<String> choiceBoxType = new ChoiceBox<>();
    private static TextField textCode = new TextField();
    private static TextField textParam = new TextField();
    private static TextField textBaseCode = new TextField();

    private static Button btnAdd = new Button("添加");
    private static Button btnEdit = new Button("修改");
    private static Button btnDel = new Button("删除");
    private static Button btnSave = new Button("保存");
    private static Button btnReset = new Button("重置");

    private static UnitService unitService = new UnitService();

    private static List<String> typeList = null;
    private static List<Unit> unitList = null;
    private static Unit selectUnit = null;
    private static String operation = null;

    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("title_set_unit"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(300);
        Scene scene = new Scene(createBorderPane());
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    /**
     * 绘制主界面
     * @return
     */
    private BorderPane createBorderPane(){
        BorderPane main = new BorderPane();
        columnType.setPrefWidth(100);
        columnType.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        columnCode.setPrefWidth(100);
        columnCode.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
        columnParam.setPrefWidth(100);
        columnParam.setCellValueFactory(new PropertyValueFactory<>("param"));
        columnBaseCode.setPrefWidth(90);
        columnBaseCode.setCellValueFactory(new PropertyValueFactory<>("baseUnitCode"));
        tableView.getColumns().clear();
        tableView.getColumns().addAll(columnType,columnCode,columnParam,columnBaseCode);
        tableView.setPrefHeight(150);
        main.setTop(tableView);

        GridPane grid = new GridPane();
        grid.add(labelType,0,0);
        grid.add(labelCode,1,0);
        grid.add(labelParam,3,0);
        grid.add(labelBaseCode,4,0);
        choiceBoxType.setPrefSize(100,20);
        grid.add(choiceBoxType,0,1);
        textCode.setPrefSize(90,20);
        grid.add(textCode,1,1);
        grid.add(new Label("="),2,1);
        textParam.setPrefSize(90,20);
        grid.add(textParam,3,1);
        textBaseCode.setPrefSize(90,20);
        grid.add(textBaseCode,4,1);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        main.setCenter(grid);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnAdd,btnEdit,btnDel,btnSave,btnReset);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        main.setBottom(hBox);

        main.setPadding(new Insets(10));
        initData();
        registEvent();
        return main;
    }

    /**
     * 绑定事件
     */
    private void registEvent(){
        tableView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    //赋值
                    selectUnit = unitList.get(newValue.intValue());
                    assignComp(selectUnit);
                    //状态设置
                    ableBtn();
                    btnSave.setDisable(true);
                    btnReset.setDisable(true);
                }
            }
        });
        choiceBoxType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    String type = typeList.get(newValue.intValue());
                    textBaseCode.setText(UnitType.getCode(type));
                }
            }
        });

        btnAdd.setOnAction(event -> {
            operation = Constants.ADD;
            clearComp();
            ableComp();
            disableBtn();
            btnSave.setDisable(false);
            btnReset.setDisable(false);
        });
        btnEdit.setOnAction(event -> {
            operation = Constants.EDIT;
            ableComp();
            disableBtn();
            btnSave.setDisable(false);
            btnReset.setDisable(false);
        });
        btnDel.setOnAction(event -> {
            if(UnitType.isBaseCode(selectUnit.getUnitCode())){
                AlertUtils.alertError("不能删除基本单位");
                return;
            }
            try {
                int result = unitService.del(selectUnit.getID());
                if(result>0){
                    reset();
                    refreshTableView();
                    AlertUtils.alertInfo("删除成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnSave.setOnAction(event -> {
            String type = choiceBoxType.getValue();
            String code = textCode.getText().trim();
            String param = textParam.getText().trim();
            String baseCode = textBaseCode.getText().trim();
            if(!validata(type,code,param,baseCode)){
                return;
            }
            Unit unit = new Unit();
            unit.setUnitType(type);
            unit.setUnitCode(code);
            unit.setParam(param);
            unit.setBaseUnitCode(baseCode);
            if(Constants.ADD.equals(operation)){
                try {
                    int result = unitService.insert(unit);
                    if(result>0){
                        reset();
                        refreshTableView();
                        AlertUtils.alertInfo("添加单位成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(Constants.EDIT.equals(operation)){
                try {
                    unit.setID(selectUnit.getID());
                    int result = unitService.update(unit);
                    if(result>0){
                        reset();
                        refreshTableView();
                        AlertUtils.alertInfo("更新单位成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnReset.setOnAction(event -> {
            reset();
        });

    }

    private void initData(){
        //重置所有组件
        reset();
        try {
            typeList = UnitType.listType();
            unitList = unitService.list();
            tableView.getItems().clear();
            tableView.getItems().addAll(unitList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        choiceBoxType.setItems(FXCollections.observableArrayList(typeList));

    }

    private void disableComp(){
        choiceBoxType.setDisable(true);
        textParam.setDisable(true);
        textCode.setDisable(true);
        textBaseCode.setDisable(true);
    }

    private void ableComp(){
        choiceBoxType.setDisable(false);
        textParam.setDisable(false);
        textCode.setDisable(false);
    }

    private void clearComp(){
        choiceBoxType.setValue(null);
        textParam.setText(null);
        textCode.setText(null);
        textBaseCode.setText(null);
    }

    private void assignComp(Unit unit){
        choiceBoxType.setValue(unit.getUnitType());
        textParam.setText(unit.getParam());
        textCode.setText(unit.getUnitCode());
        textBaseCode.setText(unit.getBaseUnitCode());
    }

    private void disableBtn(){
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDel.setDisable(true);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
    }

    private void ableBtn(){
        btnAdd.setDisable(false);
        btnEdit.setDisable(false);
        btnDel.setDisable(false);
        btnSave.setDisable(false);
        btnReset.setDisable(false);
    }

    private void reset(){
        selectUnit = null;
        disableComp();
        clearComp();
        disableBtn();
        btnAdd.setDisable(false);
    }

    private void refreshTableView(){
        try {
            unitList = unitService.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableView.getItems().clear();
        tableView.getItems().addAll(unitList);
    }

    /**
     * 验证
     * @param type
     * @param code
     * @param param
     * @param baseCode
     * @return
     */
    private boolean validata(String type,String code,String param,String baseCode){
        if(StringUtils.isBlank(type)){
            AlertUtils.alertError("请选择所属类型");
            return false;
        }
        if(StringUtils.isBlank(code)){
            AlertUtils.alertError("请输入单位符号");
            return false;
        }
        //检测是否重复
        try {
            Long id=null;
            if(Constants.EDIT.equals(operation)){
                id = selectUnit.getID();
            }
            Unit unit = unitService.findByCode(code,id);
            if(unit != null){
                AlertUtils.alertError("已经存在该单位符号");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(param)){
            AlertUtils.alertError("请输入比例系数");
            return false;
        }
        if(StringUtils.isBlank(baseCode)){
            AlertUtils.alertError("请选择基本单位符号");
            return false;
        }
        return true;
    }


}
