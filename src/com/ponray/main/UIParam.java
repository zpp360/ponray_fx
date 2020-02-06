package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Formula;
import com.ponray.entity.Standard;
import com.ponray.service.StandardService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.text.DefaultEditorKit;
import java.sql.SQLException;
import java.util.List;

public class UIParam {

    private static Label labelParamList = new Label("参数列表");
    //左侧参数列表
    private static ListView<String> paramListView = new ListView<>();

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
    private static ChoiceBox<String> choiceBoxSymbol = new ChoiceBox<>();
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
    private static List<Standard> standardList = null;


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
        paramListView.getItems().addAll("dfsdfsdfsdf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf","sdfsdf4564564564654564564564564654dsf");
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
        gridPane.add(choiceBoxSymbol,1,3);
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
        choiceBoxStandard.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1) {
                    Standard s = standardList.get(newValue.intValue());
                    //设置标准名称显示
                    labelStandardNameText.setText(s.getName());
                    //左侧参数列表
                }
            }
        });
    }

}
