package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Param;
import com.ponray.entity.Standard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UITestProgram {
    private static TabPane tabPane = null;
    private static Tab tab1 = null;
    private static Tab tab2 = null;
    private static Tab tab3 = null;
    private static Tab tab4 = null;
    private static Tab tab5 = null;

    //lable符号
    private final static Label labelS = new Label("s");
    private final static Label labelN = new Label("N");
    private final static Label labelMM = new Label("mm");
    private final static Label labelmmMin = new Label("mm/min");



    //方案编辑/选择
    private static Label labelProgramEdit = new Label("方案编辑/选择");
    private static Label labelProgramName = new Label("方案名称：");
    private static ChoiceBox<String> choiceBoxProgramName = new ChoiceBox<>();
    private static TextField textProgramName = new TextField();
    private static Label labelStanderdSelect = new Label("标准选择：");
    private static ChoiceBox<Standard> choiceBoxStanderdSelect = new ChoiceBox<>();
    private static Label labelStanderdName = new Label("试验标准：");
    private static TextArea textAreaStanderdName = new TextArea();
    private static Separator separator = new Separator();

    //方向设定
    private static Label labelDirection = new Label("方向设定：");
    private static RadioButton radioLa = new RadioButton("拉向");
    private static RadioButton radioYa = new RadioButton("压向");
    private static ToggleGroup toggleGroupDirection = new ToggleGroup();

    //实验材料形状
    private static Label labelShape = new Label("试验材料形状：");
    private static ChoiceBox<String> choiceBoxShape = new ChoiceBox<>();

    //变形计算选择
    private static Label labelTransform = new Label("变形计算选择：");
    private static ChoiceBox<String> choiceBoxTransform = new ChoiceBox<>();

    //自动判断断裂
    private static CheckBox checkBoxBreakage = new CheckBox("自动判断断裂");
    private static Label labelBreakage1Before = new Label("当力值大于");
    private static TextField textBreakage1 = new TextField();
    private static Label labelBreakage1After = new Label("N开始判断断裂");
    private static Label labelBreakage2Before = new Label("后前力值之比小于");
    private static TextField textBreakage2 = new TextField();
    private static Label labelBreakage2After = new Label("%为断裂");
    private static Label labelBreakage3Before = new Label("力值小于最大力的");
    private static TextField textBreakage3 = new TextField();
    private static Label labelBreakage3After = new Label("%为断裂");

    //变形切换
    private static CheckBox checkBoxTransformChange = new CheckBox("变形切换");
    private static RadioButton radioTransformChange1 = new RadioButton("屈服后去掉引伸计");
    private static RadioButton radioTransformChange2 = new RadioButton("变形");
    private static ToggleGroup toggleGroupTransformChange = new ToggleGroup();
    private static TextField textTransformchange = new TextField();
    private static Label labelTransformMM = new Label("mm后去掉引伸计");

    //实验结束参数
    private static Label labelTestEnd = new Label("试验结束参数：");
    private static CheckBox checkBoxFixTime = new CheckBox("定时间");
    private static CheckBox checkBoxFixN = new CheckBox("定力值");
    private static CheckBox checkBoxFixTransform = new CheckBox("定变形");
    private static CheckBox checkBoxFixDisplacement = new CheckBox("定位移");
    private static TextField textFixTime = new TextField();
    private static TextField textFixN = new TextField();
    private static TextField textFixTransform = new TextField();
    private static TextField textFixDisplacement = new TextField();
    private static Label labelFixTime = new Label("s");
    private static Label labelFixN = new Label("N");
    private static Label labelFixTransform = new Label("mm");
    private static Label labelFixDisplacement = new Label("mm");

    //预加载
    private static CheckBox checkBoxLoad = new CheckBox("预加载");
    private static Label labelLoadN = new Label("预加载力：");
    private static Label labelLoadSpeed = new Label("预加载速度：");
    private static TextField textLoadN = new TextField();
    private static TextField textLoadSpeed = new TextField();

    //实验开始清零
    private static Label labelTestClear = new Label("实验开始清零");
    private static CheckBox checkBoxDisplacement = new CheckBox("位移");
    private static CheckBox checkBoxTransform = new CheckBox("变形");
    private static CheckBox checkBoxN = new CheckBox("力");

    //实验结束自动返回
    private static CheckBox checkBoxTestEnd = new CheckBox("实验结束自动返回");
    private static Label labelBackSpeed = new Label("返回速度：");
    private static TextField textBackSpeed = new TextField();

    //去除点数
    private static Label labelPoint1 = new Label("去除点数：");
    private static TextField textPoint = new TextField();
    private static Label labelPoint2 = new Label("个（最多50个点）");

    //按钮
    private static Button btnAdd = new Button("添加");
    private static Button btnEdit = new Button("修改");
    private static Button btnDel = new Button("删除");
    private static Button btnSave = new Button("保存");
    private static Button btnReset = new Button("重置");

    //-----------------------------------tab2 start---------------------------------
    private static Label labelControl = new Label("控制方式");
    private static ToggleGroup toggleGroupControl = new ToggleGroup();
    private static RadioButton radioDisplacement = new RadioButton("位移方式");
    private static RadioButton radioControl = new RadioButton("程控方式");

    private static Label labelDisplacement = new Label("位移方式");
    private static Label labelTestSpend = new Label("试验速度");
    private static TextField textTestSpend = new TextField();

    private static Button tab2BtnSave = new Button("保存");

    //-----------------------------------tab2 end-----------------------------------
    //-----------------------------------tab3 start---------------------------------
    private static Label labelTab3_1 = new Label("主画面");
    private static Label labelTab3_2 = new Label("第二画面");
    private static Label labelTab3_3 = new Label("第三画面");
    private static Label labelTab3_4 = new Label("第四画面");
    private static ChoiceBox<String> choiceBox11 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox12 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox21 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox22 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox31 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox32 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox41 = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBox42 = new ChoiceBox<>();

    private static Label labelUnitSelect = new Label("曲线单位选择");
    private static ChoiceBox<String> choiceBoxUnitN = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBoxUnitTransform = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBoxUnitYL = new ChoiceBox<>();

    private static Button tab3BtnSave = new Button("保存");

    //-----------------------------------tab3 end-----------------------------------

    //-----------------------------------tab4 start---------------------------------
    private static Label labelUserParam = new Label("用户参数");
    private static Label labelParamName = new Label("参数名称：");
    private static ChoiceBox<Param> choiceBoxParam = new ChoiceBox<>();
    private static Label labelUnit = new Label("单位");
    private static ChoiceBox<String> choiceBoxUnit = new ChoiceBox<>();
    private static CheckBox checkBoxDefaultValue = new CheckBox("默认值：");
    private static TextField textDefaultValue = new TextField();

    private static Button paramBtnAdd = new Button("添加");
    private static Button paramBtnDel = new Button("删除");
    private static Button paramBtnEdit = new Button("修改");
    private static Button paramBtnUp = new Button("上移");
    private static Button paramBtnDown = new Button("下移");

    private static TableView<String> tableViewParam = new TableView();
    private static TableColumn columnParamNo = new TableColumn("序号");
    private static TableColumn columnParamName = new TableColumn("参数名称");
    private static TableColumn columnParamValue = new TableColumn("默认值");
    private static TableColumn columnParamUnit = new TableColumn("单位");

    private static Button tab4BtnSave = new Button("保存");

    //-----------------------------------tab4 end-----------------------------------

    //-----------------------------------tab5 start---------------------------------
    private static Label labelParamResult = new Label("结果参数");
    private static Label labelParamResultName = new Label("结果参数");
    private static ChoiceBox<String> choiceBoxParamResultName = new ChoiceBox<>();
    private static CheckBox checkBoxResult = new CheckBox("结果判定");
    private static ChoiceBox<String> choiceBoxParamResultUnit = new ChoiceBox<>();
    private static TextField textParamResultTop = new TextField();
    private static TextField textParamResultBottom = new TextField();

    private static Button btnResultAdd = new Button("添加");
    private static Button btnResultDel = new Button("删除");
    private static Button btnResultEdit = new Button("修改");
    private static Button btnResultUp = new Button("上移");
    private static Button btnResultDown = new Button("下移");

    private static TableView<String> tableViewResult = new TableView<>();
    private static TableColumn columnResultNo = new TableColumn("序号");
    private static TableColumn columnResultName = new TableColumn("参数名");
    private static TableColumn columnResultTop = new TableColumn("上限值");
    private static TableColumn columnResultBottom = new TableColumn("下限值");
    private static TableColumn columnResultUnit = new TableColumn("单位");

    private static Button tab5BtnSave = new Button("保存");

    //-----------------------------------tab5 end-----------------------------------
    //默认边框
    private static Border defaultBorder = new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT, Insets.EMPTY));
    private static Insets insets5 = new Insets(5);


    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("eidt_test"));
        //modality要使用Modality.APPLICATION_MODEL
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(600);
        window.setMinHeight(500);
        Scene scene = new Scene(createTabPane());
        window.setScene(scene);
        window.setResizable(false);
        //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
        window.showAndWait();
    }

    private TabPane createTabPane(){
        tabPane = new TabPane();
        tab1 = new Tab("基本参数");
        tab1.setClosable(false);
        tab1.setContent(createTab1());
        tab2 = new Tab("速度设置");
        tab2.setClosable(false);
        tab2.setContent(createTab2());
        tab3 = new Tab("曲线坐标");
        tab3.setClosable(false);
        tab3.setContent(createTab3());
        tab4 = new Tab("用户参数");
        tab4.setClosable(false);
        tab4.setContent(createTab4());
        tab5 = new Tab("结果参数");
        tab5.setClosable(false);
        tab5.setContent(createTab5());
        initComp();
        tabPane.getTabs().addAll(tab1,tab2,tab3,tab4,tab5);
        return tabPane;
    }

    private BorderPane createTab5(){
        BorderPane main = new BorderPane();

        VBox top = new VBox();
        GridPane grid = new GridPane();
        grid.add(labelParamResult,0,0,5,1);
        grid.add(labelParamResultName,0,1);
        grid.add(choiceBoxParamResultName,1,1);
        grid.add(checkBoxResult,2,1);
        grid.add(new Label("上限值"),3,1);
        grid.add(textParamResultTop,4,1);
        grid.add(new Label("单位"),0,2);
        grid.add(choiceBoxParamResultUnit,1,2);
        grid.add(new Label("下限值"),3,2);
        grid.add(textParamResultBottom,4,2);
        grid.setBorder(defaultBorder);
        grid.setPadding(insets5);
        grid.setHgap(10);
        grid.setVgap(5);

        HBox hBoxBtn = new HBox();
        hBoxBtn.getChildren().addAll(btnResultAdd,btnResultDel,btnResultEdit,btnResultUp,btnResultDown);
        hBoxBtn.setSpacing(10);
        hBoxBtn.setAlignment(Pos.CENTER);
        hBoxBtn.setPadding(new Insets(10));

        top.getChildren().addAll(grid,hBoxBtn);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(tab5BtnSave);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));

        main.setTop(top);
        main.setCenter(tableViewResult);
        main.setBottom(bottom);
        main.setPadding(new Insets(10));
        return main;
    }


    /**
     * 创建tab4
     * @return
     */
    private BorderPane createTab4(){
        BorderPane main = new BorderPane();

        VBox top = new VBox();
        GridPane grid = new GridPane();
        grid.add(labelUserParam,0,0,4,1);
        grid.add(labelParamName,0,1);
        grid.add(choiceBoxParam,1,1,1,1);
        grid.add(labelUnit,0,2);
        grid.add(choiceBoxUnit,1,2);
        grid.add(checkBoxDefaultValue,2,2);
        grid.add(textDefaultValue,3,2);
        grid.setBorder(defaultBorder);
        grid.setPadding(insets5);
        grid.setHgap(10);
        grid.setVgap(5);

        HBox hBoxBtn = new HBox();
        hBoxBtn.getChildren().addAll(paramBtnAdd,paramBtnDel,paramBtnEdit,paramBtnUp,paramBtnDown);
        hBoxBtn.setSpacing(10);
        hBoxBtn.setAlignment(Pos.CENTER);
        hBoxBtn.setPadding(new Insets(10));

        top.getChildren().addAll(grid,hBoxBtn);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(tab4BtnSave);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));

        main.setTop(top);
        main.setCenter(tableViewParam);
        main.setBottom(bottom);
        main.setPadding(new Insets(10));
        return main;
    }

    /**
     * 创建tab3
     * @return
     */
    private BorderPane createTab3(){
        BorderPane main = new BorderPane();
        VBox top =  new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        GridPane grid1 = new GridPane();
        grid1.add(labelTab3_1,0,0,2,1);
        grid1.add(new Label("X："),0,1);
        grid1.add(choiceBox11,1,1);
        grid1.add(new Label("Y："),0,2);
        grid1.add(choiceBox12,1,2);
        grid1.setBorder(defaultBorder);
        grid1.setPadding(new Insets(10));
        grid1.setVgap(10);
        grid1.setHgap(10);
        GridPane grid2 = new GridPane();
        grid2.add(labelTab3_2,0,0,2,1);
        grid2.add(new Label("X："),0,1);
        grid2.add(choiceBox21,1,1);
        grid2.add(new Label("Y："),0,2);
        grid2.add(choiceBox22,1,2);
        grid2.setBorder(defaultBorder);
        grid2.setPadding(new Insets(10));
        grid2.setVgap(10);
        grid2.setHgap(10);
        hBox1.setSpacing(10);
        hBox1.getChildren().addAll(grid1,grid2);

        GridPane grid3 = new GridPane();
        grid3.add(labelTab3_3,0,0,2,1);
        grid3.add(new Label("X："),0,1);
        grid3.add(choiceBox31,1,1);
        grid3.add(new Label("Y："),0,2);
        grid3.add(choiceBox32,1,2);
        grid3.setBorder(defaultBorder);
        grid3.setPadding(new Insets(10));
        grid3.setVgap(10);
        grid3.setHgap(10);
        GridPane grid4 = new GridPane();
        grid4.add(labelTab3_4,0,0,2,1);
        grid4.add(new Label("X："),0,1);
        grid4.add(choiceBox41,1,1);
        grid4.add(new Label("Y："),0,2);
        grid4.add(choiceBox42,1,2);
        grid4.setBorder(defaultBorder);
        grid4.setPadding(new Insets(10));
        grid4.setVgap(10);
        grid4.setHgap(10);
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(grid3,grid4);

        GridPane grid5 = new GridPane();
        grid5.add(labelUnitSelect,0,0,6,1);
        grid5.add(new Label("力："),0,1);
        grid5.add(choiceBoxUnitN,1,1);
        grid5.add(new Label("变形："),2,1);
        grid5.add(choiceBoxUnitTransform,3,1);
        grid5.add(new Label("应力："),4,1);
        grid5.add(choiceBoxUnitYL,5,1);
        grid5.setBorder(defaultBorder);
        grid5.setPadding(new Insets(10));
        grid5.setVgap(10);
        grid5.setHgap(10);
        top.setSpacing(10);
        top.getChildren().addAll(hBox1,hBox2,grid5);
        main.setPadding(new Insets(10));
        main.setTop(top);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(tab3BtnSave);
        hBox.setAlignment(Pos.CENTER);
        main.setBottom(hBox);
        return  main;
    }

    /**
     * tab2 速度设置
     * @return
     */
    private BorderPane createTab2(){
        BorderPane main = new BorderPane();
        VBox vBox = new VBox();
        //控制方式
        GridPane controlGrid = new GridPane();
        controlGrid.add(labelControl,0,0,2,1);
        radioDisplacement.setToggleGroup(toggleGroupControl);
        radioControl.setToggleGroup(toggleGroupControl);
        controlGrid.add(radioDisplacement,0,1);
        controlGrid.add(radioControl,1,1);
        controlGrid.setBorder(defaultBorder);
        controlGrid.setPadding(new Insets(10));
        controlGrid.setHgap(50);
        controlGrid.setVgap(10);

        //位移方式
        GridPane displacementGrid = new GridPane();
        displacementGrid.add(labelDisplacement,0,0,3,1);
        displacementGrid.add(labelTestSpend,0,1);
        displacementGrid.add(textTestSpend,1,1);
        displacementGrid.add(new Label("mm/min"),2,1);
        displacementGrid.setVgap(10);
        displacementGrid.setHgap(5);
        displacementGrid.setBorder(defaultBorder);
        displacementGrid.setPadding(new Insets(10));

        vBox.getChildren().addAll(controlGrid,displacementGrid);
        vBox.setSpacing(10);
        //按钮
        HBox hBox = new HBox();
        hBox.getChildren().addAll(tab2BtnSave);
        hBox.setAlignment(Pos.CENTER);

        main.setPadding(new Insets(10));
        main.setTop(vBox);
        main.setBottom(hBox);

        return main;
    }


    /**
     * tab1 基本参数
     * @return
     */
    private BorderPane createTab1(){
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(10));
        VBox left = new VBox();
        //方案编辑/选择
        GridPane programGrid = new GridPane();
        programGrid.setBorder(defaultBorder);
        programGrid.add(labelProgramEdit,0,0,2,1);
        programGrid.add(labelProgramName,0,1);
        HBox programStck = new HBox();
        programStck.getChildren().addAll(textProgramName,choiceBoxProgramName);
        programGrid.add(programStck,1,1);
        programGrid.add(labelStanderdSelect,0,2);
        programGrid.add(choiceBoxStanderdSelect,1,2);
        programGrid.add(labelStanderdName,0,3);
        programGrid.add(textAreaStanderdName,1,3);
        programGrid.setVgap(10);
        programGrid.setHgap(5);
        programGrid.setPadding(insets5);
        //方向设定  和  实验材料形状
        HBox directionHbox = new HBox();
        GridPane directionGrid = new GridPane();
        directionGrid.add(labelDirection,0,0,2,1);
        radioLa.setToggleGroup(toggleGroupDirection);
        radioYa.setToggleGroup(toggleGroupDirection);
        directionGrid.add(radioLa,0,1);
        directionGrid.add(radioYa,1,1);
        directionGrid.setHgap(18);
        directionGrid.setVgap(10);
        directionGrid.setBorder(defaultBorder);
        directionGrid.setPadding(new Insets(5,10,5,10));
        GridPane shaperGrid = new GridPane();
        shaperGrid.add(labelShape,0,0);
        shaperGrid.add(choiceBoxShape,0,1);
        shaperGrid.setHgap(8);
        shaperGrid.setBorder(defaultBorder);
        shaperGrid.setPadding(insets5);
        shaperGrid.setVgap(10);
        directionHbox.getChildren().addAll(directionGrid,shaperGrid);
        directionHbox.setSpacing(10);
        //变形计算选择
        HBox transformHbox = new HBox();
        transformHbox.getChildren().addAll(labelTransform,choiceBoxTransform);
        transformHbox.setBorder(defaultBorder);
        transformHbox.setSpacing(20);
        transformHbox.setPadding(insets5);
        //自动断裂判断
        GridPane breakageGrid = new GridPane();
        breakageGrid.add(checkBoxBreakage,0,0,3,1);
        breakageGrid.add(labelBreakage1Before,0,1);
        breakageGrid.add(textBreakage1,1,1);
        breakageGrid.add(labelBreakage1After,2,1);
        breakageGrid.add(labelBreakage2Before,0,2);
        breakageGrid.add(textBreakage2,1,2);
        breakageGrid.add(labelBreakage2After,2,2);
        breakageGrid.add(labelBreakage3Before,0,3);
        breakageGrid.add(textBreakage3,1,3);
        breakageGrid.add(labelBreakage3After,2,3);
        breakageGrid.setBorder(defaultBorder);
        breakageGrid.setPadding(insets5);
        breakageGrid.setHgap(5);
        breakageGrid.setVgap(10);
        //左侧容器
        left.getChildren().addAll(programGrid,directionHbox,transformHbox,breakageGrid);
        left.setSpacing(20);

        VBox right = new VBox();
        //变形切换
        GridPane shapeGrid = new GridPane();
        shapeGrid.add(checkBoxTransformChange,0,0,3,1);
        radioTransformChange1.setToggleGroup(toggleGroupTransformChange);
        radioTransformChange2.setToggleGroup(toggleGroupTransformChange);
        shapeGrid.add(radioTransformChange1,0,1,3,1);
        shapeGrid.add(radioTransformChange2,0,2);
        shapeGrid.add(textTransformchange,1,2);
        shapeGrid.add(labelTransformMM,2,2);
        shapeGrid.setVgap(5);
        shapeGrid.setBorder(defaultBorder);
        shapeGrid.setPadding(insets5);
        //实验结束参数
        GridPane testEndGrid = new GridPane();
        testEndGrid.add(labelTestEnd,0,0,3,1);
        testEndGrid.add(checkBoxFixTime,0,1);
        testEndGrid.add(textFixTime,1,1);
        testEndGrid.add(labelS,2,1);
        testEndGrid.add(checkBoxFixN,0,2);
        testEndGrid.add(textFixN,1,2);
        testEndGrid.add(labelN,2,2);
        testEndGrid.add(checkBoxFixTransform,0,3);
        testEndGrid.add(textFixTransform,1,3);
        testEndGrid.add(labelMM,2,3);
        testEndGrid.add(checkBoxFixDisplacement,0,4);
        testEndGrid.add(textFixDisplacement,1,4);
        testEndGrid.add(new Label("mm"),2,4);
        testEndGrid.setBorder(defaultBorder);
        testEndGrid.setPadding(insets5);
        testEndGrid.setVgap(5);
        testEndGrid.setHgap(5);
        //预加载 和 试验开始清零
        HBox loadHbox = new HBox();
        GridPane loadGrid = new GridPane();
        loadGrid.add(checkBoxLoad,0,0,3,1);
        loadGrid.add(labelLoadN,0,1);
        loadGrid.add(textLoadN,1,1);
        loadGrid.add(new Label("N"),2,1);
        loadGrid.add(labelLoadSpeed,0,2);
        loadGrid.add(textLoadSpeed,1,2);
        loadGrid.add(labelmmMin,2,2);
        loadGrid.setBorder(defaultBorder);
        loadGrid.setPadding(insets5);
        loadGrid.setVgap(5);
        loadGrid.setHgap(5);
        GridPane testClearGrid = new GridPane();
        testClearGrid.add(labelTestClear,0,0,2,1);
        testClearGrid.add(checkBoxDisplacement,0,1);
        testClearGrid.add(checkBoxTransform,1,1);
        testClearGrid.add(checkBoxN,0,2);
        testClearGrid.setBorder(defaultBorder);
        testClearGrid.setPadding(insets5);
        testClearGrid.setVgap(10);
        testClearGrid.setHgap(5);
        loadHbox.getChildren().addAll(loadGrid,testClearGrid);
        loadHbox.setSpacing(5);
        //实验结束自动返回
        GridPane endGrid = new GridPane();
        endGrid.add(checkBoxTestEnd,0,0,3,1);
        endGrid.add(labelBackSpeed,0,1);
        endGrid.add(textBackSpeed,1,1);
        endGrid.add(new Label("mm/min"),2,1);
        endGrid.setBorder(defaultBorder);
        endGrid.setPadding(insets5);
        endGrid.setHgap(5);
        endGrid.setVgap(5);
        //去除点数
        GridPane pointGrid = new GridPane();
        pointGrid.add(labelPoint1,0,0);
        pointGrid.add(textPoint,1,0);
        pointGrid.add(labelPoint2,2,0);
        pointGrid.setBorder(defaultBorder);
        pointGrid.setPadding(insets5);
        pointGrid.setHgap(5);

        right.getChildren().addAll(shapeGrid,testEndGrid,loadHbox,endGrid,pointGrid);
        right.setSpacing(7);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(btnAdd,btnEdit,btnDel,btnSave,btnReset);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);

        main.setLeft(left);
        main.setRight(right);
        main.setBottom(bottom);
        return main;
    }

    private void initComp(){
        textProgramName.setPrefSize(170,20);
        choiceBoxProgramName.setPrefSize(10,20);
        choiceBoxProgramName.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ccc"),CornerRadii.EMPTY,null)));
        choiceBoxStanderdSelect.setPrefSize(200,20);
        textAreaStanderdName.setPrefSize(200,30);
        choiceBoxShape.setPrefSize(130,20);
        choiceBoxTransform.setPrefSize(130,20);
        labelTransform.setPrefHeight(20);
        textBreakage1.setPrefSize(50,20);
        textBreakage2.setPrefSize(50,20);
        textBreakage3.setPrefSize(50,20);
        textFixTime.setPrefSize(100,20);
        textFixN.setPrefSize(100,20);
        textFixTransform.setPrefSize(100,20);
        textFixDisplacement.setPrefSize(100,20);
        textLoadN.setPrefSize(80,20);
        textLoadSpeed.setPrefSize(80,20);

        //tab3
        choiceBox11.setPrefSize(150,20);
        choiceBox12.setPrefSize(150,20);
        choiceBox21.setPrefSize(150,20);
        choiceBox22.setPrefSize(150,20);
        choiceBox31.setPrefSize(150,20);
        choiceBox32.setPrefSize(150,20);
        choiceBox41.setPrefSize(150,20);
        choiceBox42.setPrefSize(150,20);

        //tab4
        choiceBoxParam.setPrefSize(200,20);
        choiceBoxUnit.setPrefSize(80,20);
        textDefaultValue.setPrefSize(80,20);
        tableViewParam.setPrefHeight(300);
        tableViewParam.getColumns().clear();
        tableViewParam.getColumns().addAll(columnParamNo,columnParamName,columnParamValue,columnParamUnit);

        //tab5
        choiceBoxParamResultName.setPrefSize(200,20);
        choiceBoxParamResultUnit.setPrefSize(80,20);
        textParamResultTop.setPrefSize(80,20);
        textParamResultBottom.setPrefSize(80,20);
        tableViewResult.setPrefHeight(300);
        tableViewResult.getColumns().clear();
        tableViewResult.getColumns().addAll(columnResultNo,columnResultName,columnResultTop,columnResultBottom,columnResultUnit);
    }

}
