package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.*;
import com.ponray.service.FormulaService;
import com.ponray.service.ParamService;
import com.ponray.service.StandardService;
import com.ponray.utils.AlertUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;
import java.sql.SQLException;
import java.util.List;

public class UIParam {

    private static Label labelParamList = new Label("参数列表");
    //左侧参数列表
    private static ListView<Param> paramListView = new ListView<>();

    private static Label labelStandardInfo = new Label("标准信息");
    private static Label labelStandardCode = new Label("标准代码：");
    private static ChoiceBox<Standard> choiceBoxStandard = new ChoiceBox<>();
    private static Label labelStandardName = new Label("标准名称：");
    private static Label labelStandardNameText = new Label();//标准名称显示用

    private static Label labelFormulaList = new Label("公式列表");
    private static TableView tableViewFormula = new TableView();
    private static TableColumn<Formula,String> tableColumnTempVari = new TableColumn<>("临时变量");
    private static TableColumn<Formula,String> tableColumnSymbol = new TableColumn<>("符号");
    private static TableColumn<Formula,String> tableColumnParamType1 = new TableColumn<>("参数类型1");
    private static TableColumn<Formula,String> tableColumnParamName1 = new TableColumn<>("参数名称1");
    private static TableColumn<Formula,String> tableColumnOpChar = new TableColumn<>("运算符");
    private static TableColumn<Formula,String> tableColumnParamType2= new TableColumn<>("参数类型2");
    private static TableColumn<Formula,String> tableColumnParamName2 = new TableColumn<>("参数名称2");

    private static Label labelParamEdit = new Label("参数编辑");
    private static Label labelParamName = new Label("参数名称：");
    private static TextField textParamName = new TextField();
    private static Label labelParamType = new Label("参数类型：");
    private static ChoiceBox<String> choiceBoxParamType = new ChoiceBox();
    private static Label labelParamUnit = new Label("参数单位：");
    private static ChoiceBox<String> choiceBoxParamUnit = new ChoiceBox<>();
    private static Separator separator = new Separator();

    private static Label labelParamType1 = new Label("参数类型1");
    private static Label labelParamName1 = new Label("参数名称1");
    private static Label labelTempName = new Label("临时变量：");
    private static Label labelTemp = new Label("temp=");
    private static ChoiceBox<String> choiceBoxParamType1 = new ChoiceBox<>();
    private static TextField textParamName1 = new TextField();
    private static ChoiceBox<String> choiceBoxParamName1 = new ChoiceBox<>();
    private static Label labelSymbol = new Label("运算符");
    private static Label labelParamType2 = new Label("参数类型2");
    private static Label labelParamName2 = new Label("参数名称2");
    private static ChoiceBox<String> choiceBoxOpChar = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBoxParamType2 = new ChoiceBox<>();
    private static TextField textParamName2 = new TextField();
    private static ChoiceBox<String> choiceBoxParamName2 = new ChoiceBox<>();

    private static Button btnAddParam = new Button("添加参数");
    private static Button btnAddFormula = new Button("添加公式");
    private static Button btnEdit = new Button("修改");
    private static Button btnSave = new Button("保存");
    private static Button btnDel = new Button("删除");
    private static Button btnReset = new Button("重置");



    private static StandardService standardService = new StandardService();
    private static ParamService paramService = new ParamService();
    private static FormulaService formulaService = new FormulaService();

    private static List<Standard> standardList = null;
    private static List<String> paramTypeList = null;
    private static List<String> unitList = null;
    private static List<String> formulaParamList = null;

    //操作
    private static String OPERATION = null;
    //选中的标准
    private static Standard selectedStandard = null;
    //选中的参数
    private static Param selectedParam = null;
    //选中的公式
    private static Formula selectedFormula = null;
    //参数列表
    private static List<Param> paramList = null;
    //公式列表
    private static List<Formula> formulaList = null;

    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("title_set_param"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(800);
        window.setMinHeight(500);
        Scene scene = new Scene(createHBox());
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    private HBox createHBox(){
        HBox main = new HBox();
        //左侧布局
        VBox vBoxLeft = new VBox();
        labelParamList.setPadding(new Insets(0,0,10,10));
        paramListView.setPrefSize(180,420);
        paramListView.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        vBoxLeft.getChildren().addAll(labelParamList,paramListView);

        //右侧布局
        VBox vBoxRight = new VBox();
        //顶部标准
        VBox vBox1 = new VBox();
        HBox hBoxStandard = new HBox();
        hBoxStandard.setPadding(new Insets(10,0,10,0));
        labelStandardCode.setPadding(new Insets(3,0,0,10));
        choiceBoxStandard.setPadding(new Insets(0,0,0,10));
        choiceBoxStandard.setPrefSize(200,20);
        labelStandardName.setPadding(new Insets(3,0,0,10));
        labelStandardNameText.setPadding(new Insets(3,0,0,10));
        hBoxStandard.getChildren().addAll(labelStandardCode,choiceBoxStandard,labelStandardName,labelStandardNameText);
        vBox1.getChildren().addAll(labelStandardInfo,hBoxStandard);
        separator.setPadding(new Insets(5,0,5,0));
        //公式列表
        VBox vBox2 = new VBox();
        tableColumnTempVari.setPrefWidth(60);
        tableColumnSymbol.setPrefWidth(40);
        tableColumnTempVari.setCellValueFactory(new PropertyValueFactory<>("tempVari"));
        tableColumnSymbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        tableColumnParamType1.setCellValueFactory(new PropertyValueFactory<>("paramTypeOne"));
        tableColumnParamName1.setCellValueFactory(new PropertyValueFactory<>("paramNameOne"));
        tableColumnOpChar.setCellValueFactory(new PropertyValueFactory<>("opChar"));
        tableColumnParamType2.setCellValueFactory(new PropertyValueFactory<>("paramTypeTwo"));
        tableColumnParamName2.setCellValueFactory(new PropertyValueFactory<>("paramNameTwo"));
        tableViewFormula.getColumns().addAll(tableColumnTempVari,tableColumnSymbol,tableColumnParamType1,tableColumnParamName1,tableColumnOpChar,tableColumnParamType2,tableColumnParamName2);
        tableViewFormula.setPrefSize(600,180);
        labelFormulaList.setPadding(new Insets(5,0,0,0));
        vBox2.getChildren().addAll(labelFormulaList,tableViewFormula);
        //参数编辑
        VBox vBox3 = new VBox();
        HBox hBoxParam = new HBox();
        labelParamName.setPadding(new Insets(3,0,0,10));
        textParamName.setPrefSize(200,20);
        labelParamType.setPadding(new Insets(3,0,0,10));
        choiceBoxParamType.setPrefSize(100,20);
        choiceBoxParamType.setPadding(new Insets(0,0,0,10));
        labelParamUnit.setPadding(new Insets(3,0,0,10));
        choiceBoxParamUnit.setPrefSize(100,20);
        choiceBoxParamUnit.setPadding(new Insets(0,0,0,10));
        hBoxParam.getChildren().addAll(labelParamName,textParamName,labelParamType,choiceBoxParamType,labelParamUnit,choiceBoxParamUnit);
        HBox hBoxFormula = new HBox();
        hBoxFormula.setSpacing(40);
        hBoxFormula.setPadding(new Insets(20,0,0,0));
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        choiceBoxParamType1.setPrefSize(100,20);
        choiceBoxParamType2.setPrefSize(100,20);
        choiceBoxParamName1.setPrefSize(180,20);
        choiceBoxParamName2.setPrefSize(180,20);
        textParamName1.setPrefSize(200,20);
        textParamName2.setPrefSize(200,20);
        gridPane.add(labelParamType1,2,0);
        gridPane.add(labelParamName1,3,0);
        gridPane.add(labelTempName,0,1);
        gridPane.add(labelTemp,1,1);
        gridPane.add(choiceBoxParamType1,2,1);
        StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().addAll(choiceBoxParamName1,textParamName1);
        gridPane.add(stackPane1,3,1);
        gridPane.add(labelSymbol,1,2);
        gridPane.add(labelParamType2,2,2);
        gridPane.add(labelParamName2,3,2);
        gridPane.add(choiceBoxOpChar,1,3);
        gridPane.add(choiceBoxParamType2,2,3);
        StackPane stackPane2 = new StackPane();
        stackPane2.getChildren().addAll(choiceBoxParamName2,textParamName2);
        gridPane.add(stackPane2,3,3);
        GridPane gridPaneBtn = new GridPane();
        gridPaneBtn.setHgap(10);
        gridPaneBtn.setVgap(10);
        btnAddParam.setPrefSize(65,30);
        gridPaneBtn.add(btnAddParam,0,0);
        btnAddFormula.setPrefSize(65,30);
        gridPaneBtn.add(btnAddFormula,1,0);
        btnEdit.setPrefSize(65,30);
        gridPaneBtn.add(btnEdit,0,1);
        btnSave.setPrefSize(65,30);
        gridPaneBtn.add(btnSave,1,1);
        btnDel.setPrefSize(65,30);
        gridPaneBtn.add(btnDel,0,2);
        btnReset.setPrefSize(65,30);
        gridPaneBtn.add(btnReset,1,2);
        gridPaneBtn.setAlignment(Pos.CENTER_RIGHT);
        hBoxFormula.getChildren().addAll(gridPane,gridPaneBtn);
        vBox3.setPadding(new Insets(10,0,0,0));
        vBox3.getChildren().addAll(labelParamEdit,hBoxParam,separator,hBoxFormula);
        vBoxRight.getChildren().addAll(vBox1,vBox2,vBox3);
        main.getChildren().addAll(vBoxLeft,vBoxRight);
        main.setSpacing(20);
        main.setPadding(new Insets(10));

        //设置数据
        initData();
        //初始化组件状态
        initComp();
        //注册事件
        registEvent();
        return main;
    }

    /**
     * 初始化数据
     */
    private void initData(){

        try {
            standardList = standardService.list();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        choiceBoxStandard.setItems(FXCollections.observableList(standardList));
        choiceBoxStandard.converterProperty().set(new StringConverter<Standard>() {
            @Override
            public String toString(Standard object) {
                return object.getCode();
            }

            @Override
            public Standard fromString(String string) {
                for (Standard s: standardList){
                    if(string.equals(s.getCode())){
                        return s;
                    }
                }
                return null;
            }
        });

        //listView绑定对象
        paramListView.setCellFactory(lv -> new ListCell<Param>(){
            private TextField textField = new TextField() ;

            {
                textField.setOnAction(e -> {
                    commitEdit(getItem());
                });
                textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                });
            }

            @Override
            protected void updateItem(Param param, boolean empty) {
                super.updateItem(param, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (isEditing()) {
                    textField.setText(param.getName());
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(param.getName());
                    setGraphic(null);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                textField.setText(getItem().getName());
                setText(null);
                setGraphic(textField);
                textField.selectAll();
                textField.requestFocus();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem().getName());
                setGraphic(null);
            }

            @Override
            public void commitEdit(Param param) {
                super.commitEdit(param);
                param.setName(textField.getText());
                setText(textField.getText());
                setGraphic(null);
            }
        });

        //用户参数和结果参数
        choiceBoxParamType.setItems(FXCollections.observableArrayList(ParamType.listType()));
        choiceBoxParamUnit.setItems(FXCollections.observableArrayList(BaseUnit.listUnit()));
        choiceBoxParamType1.setItems(FXCollections.observableArrayList(FormulaParamType.listType()));
        choiceBoxParamType2.setItems(FXCollections.observableArrayList(FormulaParamType.listType()));

    }

    /**
     * 注册事件
     */
    private void registEvent(){
        choiceBoxStandard.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1) {
                    selectedStandard = standardList.get(newValue.intValue());
                    //设置标准名称显示
                    labelStandardNameText.setText(selectedStandard.getName());
                    //左侧参数列表
                    try {
                        paramList = paramService.listByStandId(selectedStandard.getId());
                        refreshParamListView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //每次选择标准后清空组件并重置组件状态
                    reset();
                    //清空选中的param
                    selectedParam = null;
                }
            }
        });
        /**
         * 左侧参数列表选择事件
         */
        paramListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > -1){
                    selectedParam = paramList.get(newValue.intValue());
                    //更新参数框
                    updateParamComp();
                    //设置按钮状态
                    setParamSelectBtn();
                    //更新公式列表
                    try {
                        formulaList = formulaService.listByParamId(selectedParam.getID());
                        tableViewFormula.getItems().clear();
                        tableViewFormula.getItems().addAll(formulaList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //选中的formula置空
                    selectedFormula = null;
                }
            }
        });

        choiceBoxParamType1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    String value = choiceBoxParamType1.getValue();
                    if(FormulaParamType.TEMP.getName().equals(value)){
                        //temp，输入框和选择框都隐藏
                        textParamName1.setVisible(false);
                        choiceBoxParamName1.setVisible(false);
                    }
                    if(FormulaParamType.CONSTANT.equals(value)){
                        //常量，显示输入框
                        textParamName1.setVisible(true);
                        choiceBoxParamName1.setVisible(false);
                    }
                    if(FormulaParamType.BASE_PARAM.equals(value)){
                        //基本参数
                    }
                    if(FormulaParamType.EXTEND_PARAM.equals(value)){
                        //扩展参数
                    }
                }
            }
        });



        btnAddParam.setOnAction(event -> {
            ableParam();
            btnAddParam.setDisable(true);
            btnSave.setDisable(false);
            btnReset.setDisable(false);
            OPERATION = Constants.ADD_PARAM;
        });
        btnAddFormula.setOnAction(event -> {
            ableFormula();
            btnAddFormula.setDisable(true);
            btnSave.setDisable(false);
            btnReset.setDisable(false);
            OPERATION = Constants.ADD_FORMULA;
        });
        btnEdit.setOnAction(event -> {
            disableBtn();
            btnSave.setDisable(false);
            btnReset.setDisable(false);
            if(selectedFormula!=null){
                OPERATION = Constants.EDIT_FORMULA;
                ableFormula();
            }else{
                OPERATION = Constants.EDIT_PARAM;
                ableParam();
            }
        });
        btnSave.setOnAction(event -> {
            if(Constants.ADD_PARAM.equals(OPERATION)){
                String name = textParamName.getText();
                String type = choiceBoxParamType.getValue();
                String unit = choiceBoxParamUnit.getValue();
                if(!validataParam(name,type,unit)){
                    return;
                }
                Param param = new Param();
                param.setStandard(selectedStandard);
                param.setName(name);
                param.setType(type);
                param.setUnit(unit);
                try {
                    int result = paramService.insert(param);
                    if(result>0){
                        //添加成功 重置文本及操作按钮，并刷新左侧参数框
                        reset();
                        refreshParamListView();
                        AlertUtils.alertInfo("添加参数成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(Constants.EDIT_PARAM.equals(OPERATION)){
                //参数修改
                String name = textParamName.getText();
                String type = choiceBoxParamType.getValue();
                String unit = choiceBoxParamUnit.getValue();
                if(!validataParam(name,type,unit)){
                    return;
                }
                Param param = new Param();
                param.setID(selectedParam.getID());
                param.setStandard(selectedStandard);
                param.setName(name);
                param.setType(type);
                param.setUnit(unit);
                try {
                    int result = paramService.update(param);
                    if(result>0){
                        //更新成功 重置文本及操作按钮，并刷新左侧参数框
                        reset();
                        refreshParamListView();
                        AlertUtils.alertInfo("修改参数成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(Constants.ADD_FORMULA.equals(OPERATION)){

            }
        });
        btnDel.setOnAction(event -> {
            if(selectedFormula!=null){
                //删除公式
                try {
                    int result = formulaService.del(selectedFormula.getID());
                    if(result>0){
                        //清空formula输入框
                        clearFormula();
                        //更新formula表格
                        formulaList = formulaService.listByParamId(selectedParam.getID());
                        tableViewFormula.getItems().clear();
                        tableViewFormula.getItems().addAll(formulaList);
                        reset();
                        AlertUtils.alertInfo("删除公式成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(selectedParam!=null){
                //删除参数
                try {
                    int result = paramService.del(selectedParam.getID());
                    if(result>0){
                        //删除成功
                        clearParam();
                        //清空公式表格
                        tableViewFormula.getItems().clear();
                        //更新左侧列表
                        paramList = paramService.listByStandId(selectedStandard.getId());
                        paramListView.getItems().clear();
                        paramListView.getItems().addAll(paramList);
                        reset();
                        AlertUtils.alertInfo("删除参数成功");
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

    /**
     * 设置选择参数后的按钮状态
     */
    private void setParamSelectBtn() {
        btnAddParam.setDisable(false);
        btnEdit.setDisable(false);
        btnDel.setDisable(false);
        btnAddFormula.setDisable(false);
        btnSave.setDisable(true);
        btnReset.setDisable(true);
    }

    /**
     * 更新参数框组件
     */
    private void updateParamComp() {
        textParamName.setText(selectedParam.getName());
        choiceBoxParamType.setValue(selectedParam.getType());
        choiceBoxParamUnit.setValue(selectedParam.getUnit());
        disableParam();
        clearFormula();
        disableParam();
    }

    /**
     * 加载页面的时候所有组件设置为不可用
     */
    private void initComp(){
        disableParam();
        disableFormula();
        disableBtn();
    }

    private void reset(){
        clearParam();
        clearFormula();
        disableParam();
        disableFormula();
        disableBtn();
        textParamName1.setVisible(false);
        textParamName2.setVisible(false);
        btnAddParam.setDisable(false);
    }

    /**
     * 参数编辑控件不可用
     */
    private void disableParam(){
        textParamName.setDisable(true);
        choiceBoxParamType.setDisable(true);
        choiceBoxParamUnit.setDisable(true);
    }

    /**
     * 参数编辑控件可用
     */
    private void ableParam(){
        textParamName.setDisable(false);
        choiceBoxParamType.setDisable(false);
        choiceBoxParamUnit.setDisable(false);
    }

    private void clearParam(){
        textParamName.clear();
        choiceBoxParamType.setValue(null);
        choiceBoxParamUnit.setValue(null);
    }

    private void ableFormula(){
        choiceBoxParamType1.setDisable(false);
        choiceBoxParamName1.setDisable(false);
        textParamName1.setDisable(false);
        choiceBoxOpChar.setDisable(false);
        choiceBoxParamType2.setDisable(false);
        choiceBoxParamName2.setDisable(false);
        textParamName2.setDisable(false);
    }

    private void disableFormula(){
        choiceBoxParamType1.setDisable(true);
        choiceBoxParamName1.setDisable(true);
        textParamName1.setDisable(true);
        choiceBoxOpChar.setDisable(true);
        choiceBoxParamType2.setDisable(true);
        choiceBoxParamName2.setDisable(true);
        textParamName2.setDisable(true);
    }

    private void clearFormula(){
        choiceBoxParamType1.setValue(null);
        choiceBoxParamName1.setValue(null);
        textParamName1.clear();
        choiceBoxOpChar.setValue(null);
        choiceBoxParamType2.setValue(null);
        choiceBoxParamName2.setValue(null);
        textParamName2.clear();
    }

    private void disableBtn(){
        btnAddParam.setDisable(true);
        btnAddFormula.setDisable(true);
        btnSave.setDisable(true);
        btnEdit.setDisable(true);
        btnDel.setDisable(true);
        btnReset.setDisable(true);
    }

    private void refreshParamListView(){
        if(selectedStandard!=null){
            try {
                paramList = paramService.listByStandId(selectedStandard.getId());
                paramListView.getItems().clear();
                paramListView.setItems(FXCollections.observableArrayList(paramList));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validataParam(String name,String type,String unit){
        if(selectedStandard==null){
            AlertUtils.alertError("请选择标准");
            return false;
        }
        if(StringUtils.isBlank(name)){
            AlertUtils.alertError("请输入参数名称");
            return false;
        }
        if(StringUtils.isBlank(type)){
            AlertUtils.alertError("请选择参数类型");
            return false;
        }
        if(StringUtils.isBlank(unit)){
            AlertUtils.alertError("请选择参数单位");
            return false;
        }
        return true;
    }

}
