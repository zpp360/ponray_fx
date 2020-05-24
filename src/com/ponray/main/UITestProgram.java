package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.*;
import com.ponray.enums.*;
import com.ponray.service.*;
import com.ponray.utils.AlertUtils;
import com.ponray.utils.ValidateUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private static ChoiceBox<Program> choiceBoxProgramName = new ChoiceBox<>();
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
    private static Button btnAdd = new Button("添加方案");
    private static Button btnDel = new Button("删除方案");
    private static Button btnSave = new Button("保存");

    //-----------------------------------tab2 start---------------------------------
    private static Label labelControl = new Label("控制方式");
    private static ToggleGroup toggleGroupControl = new ToggleGroup();
    private static RadioButton radioDisplacement = new RadioButton("位移方式");
    private static RadioButton radioControl = new RadioButton("程控方式");

    private static Label labelDisplacement = new Label("位移方式");
    private static Label labelTestSpend = new Label("试验速度");
    private static TextField textTestSpend = new TextField();

    private static Label labelProgramControl = new Label("程控方式");
    private static Label labelStartControl = new Label("始控方式：");
    private static Label labelEndControl = new Label("终控方式：");
    private static ChoiceBox<String> choiceBoxStartControl = new ChoiceBox<>();
    private static ChoiceBox<String> choiceBoxEndControl = new ChoiceBox<>();
    private static TextField textStartControl = new TextField();
    private static TextField textEndControl =new TextField();
    private static Label labelStartControlUnit = new Label();
    private static Label labelEndControlUnit = new Label();
    private static Label labelStartControlTip = new Label();
    private static Label labelEndControlTip = new Label();
    private static Button btnCAdd = new Button("添加");
    private static Button btnCDel = new Button("删除");
    private static Button btnCInsert = new Button("插入");
    private static Button btnCEdit = new Button("修改");
    private static Button btnCSave = new Button("保存");
    private static Button btnCReset = new Button("重置");
    private static TreeView<String> treeView = new TreeView<>();
    private static TreeItem<String> treeRoot = new TreeItem<>("根节点");

    private static Button tab2BtnSave = new Button("保存");

    private static GridPane displacementGrid = new GridPane();
    private static GridPane programGrid = new GridPane();
    //程控树列表
    private static List<ProgramControl> programControlList = new ArrayList<>();
    private static ProgramControl selectedControl = null;

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
    //坐标列表
    private static List<String> axisList = null;
    //曲线单位选择 力
    private static List<String> unitNList = null;
    private static List<String> unitTransformList = null;
    private static List<String> unitYLList = null;


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

    private static TableView<ProgramUserParam> tableViewParam = new TableView();
    private static TableColumn<ProgramUserParam,Integer> columnParamNo = new TableColumn("序号");
    private static TableColumn<ProgramUserParam,String> columnParamName = new TableColumn("参数名称");
    private static TableColumn<ProgramUserParam,String> columnParamValue = new TableColumn("默认值");
    private static TableColumn<ProgramUserParam,String> columnParamUnit = new TableColumn("单位");

    private static Button tab4BtnSave = new Button("保存");

    private static List<Param> userParamList = null;
    private static List<ProgramUserParam> programUserParamList = new ArrayList<>(20);
    private static ProgramUserParam selectedUserParam = null;

    //-----------------------------------tab4 end-----------------------------------

    //-----------------------------------tab5 start---------------------------------
    private static Label labelParamResult = new Label("结果参数");
    private static Label labelParamResultName = new Label("结果参数");
    private static ChoiceBox<Param> choiceBoxParamResultName = new ChoiceBox<>();
    private static CheckBox checkBoxResult = new CheckBox("结果判定");
    private static ChoiceBox<String> choiceBoxParamResultUnit = new ChoiceBox<>();
    private static TextField textParamResultTop = new TextField();
    private static TextField textParamResultBottom = new TextField();

    private static Button btnResultAdd = new Button("添加");
    private static Button btnResultDel = new Button("删除");
    private static Button btnResultEdit = new Button("修改");
    private static Button btnResultUp = new Button("上移");
    private static Button btnResultDown = new Button("下移");

    private static TableView<ProgramResultParam> tableViewResult = new TableView<>();
    private static TableColumn<ProgramResultParam,Integer> columnResultNo = new TableColumn("序号");
    private static TableColumn<ProgramResultParam,String> columnResultName = new TableColumn("参数名");
    private static TableColumn<ProgramResultParam,String> columnResultTop = new TableColumn("上限值");
    private static TableColumn<ProgramResultParam,String> columnResultBottom = new TableColumn("下限值");
    private static TableColumn<ProgramResultParam,String> columnResultUnit = new TableColumn("单位");

    private static Button tab5BtnSave = new Button("保存");

    private static List<Param> resultParamList = null;
    private static List<ProgramResultParam> programResultParamList = new ArrayList<>();
    private static ProgramResultParam selectedResultParam = null;

    //-----------------------------------tab5 end-----------------------------------
    //默认边框
    private static Border defaultBorder = new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT, Insets.EMPTY));
    private static Insets insets5 = new Insets(5);

    private static StandardService standardService = new StandardService();
    private static ProgramService programService = new ProgramService();
    private static ProgramControlService programControlService = new ProgramControlService();
    private static UnitService unitService = new UnitService();
    private static ParamService paramService = new ParamService();

    //方案列表
    private static List<Program> programList = null;
    //标准列表list
    private static List<Standard> standardList = null;
    //实验材料形状list
    private static List<String> shapeList = null;
    //变形计算list
    private static List<String> transformList = null;
    //选中的实验方案program
    private static Program selectedProgram = null;

    //选中的标准
    private static Standard selectedStandard = null;

    private static String operateTab1 = null;

    //始控方式list
    private static List<String> startControlList = null;
    //终控方式list
    private static List<String> endControlList = null;

    private static String operateControl = null;

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
        initData();
        registEvent();
        //选择第一个
        if(programList!=null && programList.size()>0){
            choiceBoxProgramName.setValue(programList.get(0));
        }
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
//        hBox1.getChildren().addAll(grid1,grid2);
        hBox1.getChildren().addAll(grid1);

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
//        top.getChildren().addAll(hBox1,hBox2,grid5);
        top.getChildren().addAll(hBox1);
        main.setPadding(new Insets(20));
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
//        controlGrid.add(radioControl,1,1);
        controlGrid.setBorder(defaultBorder);
        controlGrid.setPadding(new Insets(10));
        controlGrid.setHgap(50);
        controlGrid.setVgap(10);

        //位移方式
        displacementGrid.getChildren().clear();
        displacementGrid.add(labelDisplacement,0,0,3,1);
        displacementGrid.add(labelTestSpend,0,1);
        displacementGrid.add(textTestSpend,1,1);
        displacementGrid.add(new Label("mm/min"),2,1);
        displacementGrid.setVgap(10);
        displacementGrid.setHgap(5);
        displacementGrid.setBorder(defaultBorder);
        displacementGrid.setPadding(new Insets(10));

        //程控方式
        programGrid.getChildren().clear();
        programGrid.add(labelProgramControl,0,0,5,1);
        programGrid.add(labelStartControl,0,1);
        programGrid.add(choiceBoxStartControl,1,1);
        programGrid.add(labelStartControlTip,2,1);
        programGrid.add(textStartControl,3,1);
        programGrid.add(labelStartControlUnit,4,1);
        programGrid.add(labelEndControl,0,2);
        programGrid.add(choiceBoxEndControl,1,2);
        programGrid.add(labelEndControlTip,2,2);
        programGrid.add(textEndControl,3,2);
        programGrid.add(labelEndControlUnit,4,2);
        HBox hBoxBtn = new HBox();
        hBoxBtn.getChildren().addAll(btnCAdd,btnCDel,btnCInsert,btnCEdit,btnCSave,btnCReset);
        hBoxBtn.setSpacing(5);
        programGrid.add(hBoxBtn,0,3,5,1);
        programGrid.add(treeView,0,4,5,1);
        programGrid.setVgap(10);
        programGrid.setHgap(5);
        programGrid.setBorder(defaultBorder);
        programGrid.setPadding(new Insets(10));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(displacementGrid,programGrid);

        vBox.getChildren().addAll(controlGrid,stackPane);
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
//        breakageGrid.add(labelBreakage3Before,0,3);
//        breakageGrid.add(textBreakage3,1,3);
//        breakageGrid.add(labelBreakage3After,2,3);
        breakageGrid.setBorder(defaultBorder);
        breakageGrid.setPadding(insets5);
        breakageGrid.setHgap(5);
        breakageGrid.setVgap(10);
        //左侧容器
        left.getChildren().addAll(programGrid,directionHbox,transformHbox,breakageGrid);
        left.setSpacing(20);

        VBox right = new VBox();
        //变形切换
//        GridPane shapeGrid = new GridPane();
//        shapeGrid.add(checkBoxTransformChange,0,0,3,1);
//        radioTransformChange1.setToggleGroup(toggleGroupTransformChange);
//        radioTransformChange2.setToggleGroup(toggleGroupTransformChange);
//        shapeGrid.add(radioTransformChange1,0,1,3,1);
//        shapeGrid.add(radioTransformChange2,0,2);
//        shapeGrid.add(textTransformchange,1,2);
//        shapeGrid.add(labelTransformMM,2,2);
//        shapeGrid.setVgap(5);
//        shapeGrid.setBorder(defaultBorder);
//        shapeGrid.setPadding(insets5);
//        //实验结束参数
//        GridPane testEndGrid = new GridPane();
//        testEndGrid.add(labelTestEnd,0,0,3,1);
//        testEndGrid.add(checkBoxFixTime,0,1);
//        testEndGrid.add(textFixTime,1,1);
//        testEndGrid.add(labelS,2,1);
//        testEndGrid.add(checkBoxFixN,0,2);
//        testEndGrid.add(textFixN,1,2);
//        testEndGrid.add(labelN,2,2);
//        testEndGrid.add(checkBoxFixTransform,0,3);
//        testEndGrid.add(textFixTransform,1,3);
//        testEndGrid.add(labelMM,2,3);
//        testEndGrid.add(checkBoxFixDisplacement,0,4);
//        testEndGrid.add(textFixDisplacement,1,4);
//        testEndGrid.add(new Label("mm"),2,4);
//        testEndGrid.setBorder(defaultBorder);
//        testEndGrid.setPadding(insets5);
//        testEndGrid.setVgap(5);
//        testEndGrid.setHgap(5);
//        //预加载 和 试验开始清零
        HBox loadHbox = new HBox();
//        GridPane loadGrid = new GridPane();
//        loadGrid.add(checkBoxLoad,0,0,3,1);
//        loadGrid.add(labelLoadN,0,1);
//        loadGrid.add(textLoadN,1,1);
//        loadGrid.add(new Label("N"),2,1);
//        loadGrid.add(labelLoadSpeed,0,2);
//        loadGrid.add(textLoadSpeed,1,2);
//        loadGrid.add(labelmmMin,2,2);
//        loadGrid.setBorder(defaultBorder);
//        loadGrid.setPadding(insets5);
//        loadGrid.setVgap(5);
//        loadGrid.setHgap(5);
        GridPane testClearGrid = new GridPane();
        testClearGrid.add(labelTestClear,0,0,2,1);
        testClearGrid.add(checkBoxDisplacement,0,1);
        testClearGrid.add(checkBoxTransform,1,1);
        testClearGrid.add(checkBoxN,0,2);
        testClearGrid.setBorder(defaultBorder);
        testClearGrid.setPadding(insets5);
        testClearGrid.setVgap(10);
        testClearGrid.setHgap(5);
//        loadHbox.getChildren().addAll(loadGrid,testClearGrid);
        loadHbox.getChildren().addAll(testClearGrid);
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

//        right.getChildren().addAll(shapeGrid,testEndGrid,loadHbox,endGrid,pointGrid);
        right.getChildren().addAll(loadHbox,endGrid);
        right.setSpacing(7);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(btnAdd,btnDel,btnSave);
        bottom.setSpacing(20);
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
        textAreaStanderdName.setDisable(true);
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

        disableBreakage();
        disablePreload();
        disableTestEnd();
        disableTransform();
        textBackSpeed.setDisable(true);
        textTransformchange.setDisable(true);
        textProgramName.setDisable(true);

        //tab2
        choiceBoxStartControl.setPrefSize(120,20);
        choiceBoxEndControl.setPrefSize(120,20);
        textStartControl.setPrefSize(100,20);
        textEndControl.setPrefSize(100,20);
        labelStartControlTip.setPrefSize(130,20);
        labelEndControlTip.setPrefSize(130,20);
        labelStartControlTip.setAlignment(Pos.CENTER_RIGHT);
        labelEndControlTip.setAlignment(Pos.CENTER_RIGHT);
        treeView.setPrefSize(600,200);
        treeView.setRoot(treeRoot);
        treeRoot.setExpanded(true);
//        treeView.setCellFactory(new Callback<TreeView<ProgramControl>, TreeCell<ProgramControl>>() {
//            @Override
//            public TreeCell<ProgramControl> call(TreeView<ProgramControl> param) {
//                return new TreeCell<ProgramControl>(){
//                    @Override
//                    protected void updateItem(final ProgramControl p,boolean empty) {
//                        super.updateItem(p, empty);
//                        if (!empty && p != null) {
//                            setText(p.toString());
//                        } else {
//                            setText(null);
//                            setGraphic(null);
//                        }
//                    }
//                };
//            }
//        });
        //tab3
        choiceBox11.setPrefSize(150,20);
        choiceBox12.setPrefSize(150,20);
        choiceBox21.setPrefSize(150,20);
        choiceBox22.setPrefSize(150,20);
        choiceBox31.setPrefSize(150,20);
        choiceBox32.setPrefSize(150,20);
        choiceBox41.setPrefSize(150,20);
        choiceBox42.setPrefSize(150,20);
        choiceBoxUnitN.setPrefSize(90,20);
        choiceBoxUnitTransform.setPrefSize(90,20);
        choiceBoxUnitYL.setPrefSize(90,20);

        //tab4
        choiceBoxParam.setPrefSize(200,20);
        choiceBoxParam.setConverter(new StringConverter<Param>() {
            @Override
            public String toString(Param object) {
                return object.getName();
            }

            @Override
            public Param fromString(String string) {
                for(Param p : userParamList){
                    if(string.equals(p.getName())){
                        return p;
                    }
                }
                return null;
            }
        });
        choiceBoxUnit.setPrefSize(80,20);
        textDefaultValue.setPrefSize(80,20);
        tableViewParam.setPrefHeight(300);
        tableViewParam.getColumns().clear();
        columnParamNo.setCellValueFactory(new PropertyValueFactory<>("num"));
        columnParamName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnParamUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        columnParamValue.setCellValueFactory(new PropertyValueFactory<>("defaultVal"));
        tableViewParam.getColumns().addAll(columnParamNo,columnParamName,columnParamValue,columnParamUnit);

        //tab5
        choiceBoxParamResultName.setPrefSize(200,20);
        choiceBoxParamResultName.setConverter(new StringConverter<Param>() {
            @Override
            public String toString(Param object) {
                return object.getName();
            }

            @Override
            public Param fromString(String string) {
                for (Param p : resultParamList){
                    if(string.equals(p.getName())){
                        return p;
                    }
                }
                return null;
            }
        });
        choiceBoxParamResultUnit.setPrefSize(80,20);
        textParamResultTop.setPrefSize(80,20);
        textParamResultBottom.setPrefSize(80,20);
        tableViewResult.setPrefHeight(300);
        tableViewResult.getColumns().clear();
        columnResultNo.setCellValueFactory(new PropertyValueFactory<>("num"));
        columnResultName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnResultTop.setCellValueFactory(new PropertyValueFactory<>("up"));
        columnResultBottom.setCellValueFactory(new PropertyValueFactory<>("low"));
        columnResultUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        tableViewResult.getColumns().addAll(columnResultNo,columnResultName,columnResultTop,columnResultBottom,columnResultUnit);
    }

    private void initData(){
        try {
            programList = programService.list();
            standardList = standardService.list();
            axisList = Axis.listName();
            unitNList = unitService.listByBaseCode(Constants.BASE_UNIT_N);
            unitTransformList = unitService.listByBaseCode(Constants.BASE_UNIT_TRANSFORM);
            unitYLList = unitService.listByBaseCode(Constants.BASE_UNIT_YL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        choiceBoxProgramName.converterProperty().set(new StringConverter<Program>() {
            @Override
            public String toString(Program object) {
                return object.getName();
            }

            @Override
            public Program fromString(String string) {
                for(Program p:programList){
                    if(p.getName().equals(string)){
                        return p;
                    }
                }
                return null;
            }
        });
        choiceBoxProgramName.getItems().clear();
        choiceBoxProgramName.getItems().addAll(programList);
        choiceBoxStanderdSelect.converterProperty().set(new StringConverter<Standard>() {
            @Override
            public String toString(Standard object) {
                return object.getCode();
            }

            @Override
            public Standard fromString(String string) {
                for (Standard s:standardList){
                    if(string.equals(s.getCode())){
                        return s;
                    }
                }
                return null;
            }
        });

        choiceBoxStanderdSelect.getItems().clear();
        choiceBoxStanderdSelect.getItems().addAll(standardList);
        choiceBoxShape.getItems().clear();
        shapeList = MaterialShape.listName();
        choiceBoxShape.getItems().addAll(shapeList);
        transformList = TransformCalculat.listName();
        choiceBoxTransform.getItems().clear();
        choiceBoxTransform.getItems().addAll(transformList);
        //始控方式
        startControlList = ControlStart.listName();
        choiceBoxStartControl.getItems().clear();
        choiceBoxStartControl.getItems().addAll(startControlList);
        //终控方式
        endControlList = ControlEnd.listName();
        choiceBoxEndControl.getItems().clear();
        choiceBoxEndControl.getItems().addAll(endControlList);
        //主画面
        choiceBox11.getItems().clear();
        choiceBox11.getItems().addAll(axisList);
        choiceBox12.getItems().clear();
        choiceBox12.getItems().addAll(axisList);
        //第二画面
        choiceBox21.getItems().clear();
        choiceBox21.getItems().addAll(axisList);
        choiceBox22.getItems().clear();
        choiceBox22.getItems().addAll(axisList);
        //第三画面
        choiceBox31.getItems().clear();
        choiceBox31.getItems().addAll(axisList);
        choiceBox32.getItems().clear();
        choiceBox32.getItems().addAll(axisList);
        //第四画面
        choiceBox41.getItems().clear();
        choiceBox41.getItems().addAll(axisList);
        choiceBox42.getItems().clear();
        choiceBox42.getItems().addAll(axisList);
        //曲线单位选择
        choiceBoxUnitN.getItems().clear();
        choiceBoxUnitN.getItems().addAll(unitNList);
        choiceBoxUnitTransform.getItems().clear();
        choiceBoxUnitTransform.getItems().addAll(unitTransformList);
        choiceBoxUnitYL.getItems().clear();
        choiceBoxUnitYL.getItems().addAll(unitYLList);
    }

    private void registEvent(){
        choiceBoxProgramName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > -1) {
                    selectedProgram = programList.get(newValue.intValue());
                    textProgramName.setText(selectedProgram.getName());
                    //标准
                    try {
                        selectedStandard = standardService.findByCode(selectedProgram.getStandard());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    choiceBoxStanderdSelect.setValue(selectedStandard);
                    operateTab1 = null;
                    initTab1Data();
                }
            }
        });
        choiceBoxStanderdSelect.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > -1) {
                    selectedStandard = standardList.get(newValue.intValue());
                    textAreaStanderdName.setText(selectedStandard.getName());
                }
            }
        });
        //自动判断断裂
        checkBoxBreakage.setOnAction(event -> {
            System.out.println(checkBoxBreakage.isSelected());
           if(checkBoxBreakage.isSelected()){
               ableBreakage();
           }else{
               disableBreakage();
           }
        });
        //变形切换
        checkBoxTransformChange.setOnAction(event -> {
            if(checkBoxTransformChange.isSelected()){
                ableTransform();
            }else{
                disableTransform();
            }
        });
        radioTransformChange1.setOnAction(event -> {
            textTransformchange.setDisable(true);
        });
        //变形  mm后去掉引伸计
        radioTransformChange2.setOnAction(event -> {
            if(radioTransformChange2.isSelected()){
                textTransformchange.setDisable(false);
            }
        });
        //定时间
        checkBoxFixTime.setOnAction(event -> {
            textFixTime.setDisable(!checkBoxFixTime.isSelected());
        });
        //定力值
        checkBoxFixN.setOnAction(event -> {
            textFixN.setDisable(!checkBoxFixN.isSelected());
        });
        //定变形
        checkBoxFixTransform.setOnAction(event -> {
            textFixTransform.setDisable(!checkBoxFixTransform.isSelected());
        });
        //定位移
        checkBoxFixDisplacement.setOnAction(event -> {
            textFixDisplacement.setDisable(!checkBoxFixDisplacement.isSelected());
        });
        //预加载
        checkBoxLoad.setOnAction(event -> {
            if(checkBoxLoad.isSelected()){
                ablePreload();
            }else{
                disablePreload();
            }
        });
        //实验结束自动返回
        checkBoxTestEnd.setOnAction(event -> {
            textBackSpeed.setDisable(!checkBoxTestEnd.isSelected());
        });
        //添加方案
        btnAdd.setOnAction(event -> {
            operateTab1 = Constants.ADD;
            textProgramName.setDisable(false);
        });
        //保存
        btnSave.setOnAction(event -> {
            if(Constants.ADD.equals(operateTab1)){
                Program p = getProgram();
                if(!validata(p)){
                    return;
                }
                try {
                    int result = programService.insert(p);
                    if(result>0){
                        programList = programService.list();
                        choiceBoxProgramName.getItems().clear();
                        choiceBoxProgramName.getItems().addAll(programList);
                        if(programList!=null && programList.size()>0){
                            choiceBoxProgramName.setValue(programList.get(programList.size()-1));
                        }
                        AlertUtils.alertInfo("添加实验方案成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                //更新
                Program p = getProgram();
                p.setID(selectedProgram.getID());
                if(!validata(p)){
                    return;
                }
                try {
                    int result = programService.updateBase(p);
                    if(result>0){
                        programList = programService.list();
                        choiceBoxProgramName.getItems().clear();
                        choiceBoxProgramName.getItems().addAll(programList);
                        if(programList!=null && programList.size()>0){
                            choiceBoxProgramName.setValue(selectedProgram);
                        }
                        AlertUtils.alertInfo("更新实验方案成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //删除
        btnDel.setOnAction(event -> {
            if(selectedProgram==null){
                AlertUtils.alertError("请选择实验方案");
                return;
            }
            try {
               int result = programService.del(selectedProgram.getID());
               if(result>0){
                   programList.remove(selectedProgram);
                   choiceBoxProgramName.getItems().remove(selectedProgram);
                   if(programList!=null && programList.size()>0){
                       choiceBoxProgramName.setValue(programList.get(0));
                   }
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //位移方式
        radioDisplacement.setOnAction(event -> {
            if(radioDisplacement.isSelected()){
                displacementGrid.setVisible(true);
                programGrid.setVisible(false);
            }
        });
        //程控方式
        radioControl.setOnAction(event -> {
            if(radioControl.isSelected()){
                displacementGrid.setVisible(false);
                programGrid.setVisible(true);
                resetControl();
            }
        });

        tab2.setOnSelectionChanged(event -> {
            //选中tab2
            if(tab2.isSelected()){
                if(selectedProgram!=null){
                   if(selectedProgram.isControl()){
                        //程序控制
                       radioControl.setSelected(true);
                       displacementGrid.setVisible(false);
                       programGrid.setVisible(true);
                       //初始化程控treeview数据
                       try {
                           programControlList = programControlService.listByProgramId(selectedProgram.getID());
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                       resetControl();
                       refreshControlTree();
                   }else{
                       radioDisplacement.setSelected(true);
                       if(selectedProgram.getGeneralSpeed()!=null){
                           textTestSpend.setText(selectedProgram.getGeneralSpeed()+Constants.BLANK);
                       }
                       displacementGrid.setVisible(true);
                       programGrid.setVisible(false);
                   }
                }else{
                    radioDisplacement.setSelected(true);
                    displacementGrid.setVisible(true);
                    programGrid.setVisible(false);
                }
            }
        });

        //始控方式
        choiceBoxStartControl.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    String start = startControlList.get(newValue.intValue());
                    labelStartControlTip.setText(start);
                    labelStartControlUnit.setText(ControlStart.getUnit(start));
                }
            }
        });
        //中控方式
        choiceBoxEndControl.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    String end = endControlList.get(newValue.intValue());
                    labelEndControlTip.setText(end);
                    labelEndControlUnit.setText(ControlStart.getUnit(end));
                }
            }
        });
        //程控添加
        btnCAdd.setOnAction(event -> {
            ableControl();
            clearControl();
            btnAddControl();
            operateControl = Constants.ADD;
        });
        //插入
        btnCInsert.setOnAction(event -> {
            ableControl();
            clearControl();
            btnAddControl();
            operateControl = Constants.INSERT;
        });
        btnCDel.setOnAction(event -> {
            if(selectedControl==null){
                AlertUtils.alertError("请选择程控过程");
                return;
            }
            programControlList.remove(selectedControl);
            refreshControlTree();
            resetControl();
            AlertUtils.alertInfo("删除成功");
        });
        btnCEdit.setOnAction(event -> {
            ableControl();
            btnAddControl();
            operateControl = Constants.EDIT;
        });
        //程控保存
        btnCSave.setOnAction(event -> {
            if(Constants.ADD.equals(operateControl)){
                //添加
                ProgramControl p = getProgramControl();
                if(!validataProgramControl(p)){
                    return;
                }
                programControlList.add(p);
            }
            if(Constants.INSERT.equals(operateControl)){
                //插入
                ProgramControl p = getProgramControl();
                if(!validataProgramControl(p)){
                    return;
                }
                int index = programControlList.indexOf(selectedControl);
                programControlList.add(index,p);
            }
            if(Constants.EDIT.equals(operateControl)){
                //修改
                ProgramControl p = getProgramControl();
                if(!validataProgramControl(p)){
                    return;
                }
                int index = programControlList.indexOf(selectedControl);
                programControlList.remove(selectedControl);
                programControlList.add(index,p);
            }
            refreshControlTree();
            resetControl();
        });

        //程控重置
        btnCReset.setOnAction(event -> {
            resetControl();
        });

        //treeview选择
        treeView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>0 && programControlList.size()>0){
                    selectedControl = programControlList.get(newValue.intValue()-1);
                    initControlData();
                    btnCAdd.setDisable(true);
                    btnCEdit.setDisable(false);
                    btnCDel.setDisable(false);
                    btnCInsert.setDisable(false);
                    btnCSave.setDisable(true);
                    btnCReset.setDisable(false);
                }
            }
        });

        //tab2中的save
        tab2BtnSave.setOnAction(event -> {
            if(radioDisplacement.isSelected()){
                //位移方式
                selectedProgram.setControl(false);
                String speed = textTestSpend.getText();
                if(StringUtils.isBlank(speed)){
                    AlertUtils.alertError("请输入实验速度");
                    return;
                }
                if(!ValidateUtils.zIndex(speed) && !ValidateUtils.posttiveFloat(speed)){
                    AlertUtils.alertError("实验速度值为数字");
                    return ;
                }
                selectedProgram.setGeneralSpeed(Float.valueOf(speed.trim()));
                try {
                    int result = programService.updateControl(selectedProgram);
                    if(result>0){
                        AlertUtils.alertInfo("保存位移方式成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(radioControl.isSelected()){
                //程控方式
                selectedProgram.setControl(true);
                selectedProgram.setGeneralSpeed(0F);
                try {
                    programService.updateControl(selectedProgram);
                    programControlService.delByProgramId(selectedProgram.getID());
                    programControlService.insertControl(programControlList,selectedProgram.getID());
                    resetControl();
                    AlertUtils.alertInfo("保存程控方式成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //选中tab3
        tab3.setOnSelectionChanged(event -> {
            if(tab3.isSelected()){
                choiceBox11.setValue(selectedProgram.getOneX());
                choiceBox12.setValue(selectedProgram.getOneY());
                choiceBox21.setValue(selectedProgram.getTwoX());
                choiceBox22.setValue(selectedProgram.getTwoY());
                choiceBox31.setValue(selectedProgram.getThreeX());
                choiceBox32.setValue(selectedProgram.getThreeY());
                choiceBox41.setValue(selectedProgram.getFourX());
                choiceBox42.setValue(selectedProgram.getFourY());
                choiceBoxUnitN.setValue(selectedProgram.getUnitN());
                choiceBoxUnitTransform.setValue(selectedProgram.getUnitTransform());
                choiceBoxUnitYL.setValue(selectedProgram.getUnitLoad());
            }
        });
        //tab3保存
        tab3BtnSave.setOnAction(event -> {
            String oneX = choiceBox11.getValue();
            String oneY = choiceBox12.getValue();
//            String twoX = choiceBox21.getValue();
//            String twoY = choiceBox22.getValue();
//            String threeX = choiceBox31.getValue();
//            String threeY = choiceBox32.getValue();
//            String fourX = choiceBox41.getValue();
//            String fourY = choiceBox42.getValue();
//            String unitN = choiceBoxUnitN.getValue();
//            String unitTransform = choiceBoxUnitTransform.getValue();
//            String unitYL = choiceBoxUnitYL.getValue();
//            if(StringUtils.isBlank(oneX) || StringUtils.isBlank(oneY) || StringUtils.isBlank(twoX) || StringUtils.isBlank(twoY) || StringUtils.isBlank(threeX) || StringUtils.isBlank(threeY)
//                    || StringUtils.isBlank(fourX) || StringUtils.isBlank(fourY) || StringUtils.isBlank(unitN) || StringUtils.isBlank(unitTransform) || StringUtils.isBlank(unitYL)){
//                AlertUtils.alertError("请选择未选择项");
//                return;
//            }
            if(StringUtils.isBlank(oneX) || StringUtils.isBlank(oneY)){
                AlertUtils.alertError("请选择未选择项");
                return;
            }
            selectedProgram.setOneX(oneX);
            selectedProgram.setOneY(oneY);
//            selectedProgram.setTwoX(twoX);
//            selectedProgram.setTwoY(twoY);
//            selectedProgram.setThreeX(threeX);
//            selectedProgram.setThreeY(threeY);
//            selectedProgram.setFourX(fourX);
//            selectedProgram.setFourY(fourY);
//            selectedProgram.setUnitN(unitN);
//            selectedProgram.setUnitTransform(unitTransform);
//            selectedProgram.setUnitLoad(unitYL);
            try {
                int res = programService.updateAxis(selectedProgram);
                if(res>0){
                    //更新selectProgram
                    //selectedProgram = programService.findById(selectedProgram.getID());
                    AlertUtils.alertInfo("保存成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        tab4.setOnSelectionChanged(event -> {
            //tab4选中
            if(tab4.isSelected()){
                //用户参数
                try {
                    programUserParamList = programService.listUserParam(selectedProgram.getID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resetUserParam();
                try {
                    userParamList = paramService.listUserParamByStandId(selectedStandard.getId());
                    choiceBoxParam.getItems().clear();
                    choiceBoxParam.getItems().addAll(userParamList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        choiceBoxParam.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>-1){
                Param param = userParamList.get(newValue.intValue());
                List<String> unitList = null;
                try {
                    unitList = unitService.listByBaseCode(param.getUnit());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                choiceBoxUnit.getItems().clear();
                if(unitList!=null && unitList.size()>0) {
                    choiceBoxUnit.getItems().addAll(unitList);
                    choiceBoxUnit.setValue(param.getUnit());
                }else{
                    choiceBoxUnit.getItems().add(param.getUnit());
                    choiceBoxUnit.setValue(param.getUnit());
                }
            }
        });

        checkBoxDefaultValue.setOnAction(event -> {
            if(checkBoxDefaultValue.isSelected()){
                textDefaultValue.setDisable(false);
            }else{
                textDefaultValue.setDisable(true);
            }
        });
        //用户参数列表选择
        tableViewParam.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>-1 && programUserParamList!=null && programUserParamList.size()>0){
                selectedUserParam = programUserParamList.get(newValue.intValue());
                choiceBoxParam.setValue(selectedUserParam.getParam());
                choiceBoxUnit.setValue(selectedUserParam.getUnit());
                if(StringUtils.isNotBlank(selectedUserParam.getDefaultVal())){
                    checkBoxDefaultValue.setSelected(true);
                    textDefaultValue.setDisable(false);
                }else{
                    checkBoxDefaultValue.setSelected(false);
                }
                textDefaultValue.setText(selectedUserParam.getDefaultVal());
                paramBtnAdd.setDisable(false);
                paramBtnEdit.setDisable(false);
                paramBtnDel.setDisable(false);
                paramBtnUp.setDisable(false);
                paramBtnDown.setDisable(false);
            }
        });
        //用户参数添加
        paramBtnAdd.setOnAction(event -> {
            ProgramUserParam  p = getUserParam();
            boolean flag = false;
            for (ProgramUserParam up : programUserParamList){
                if(up.getName().equals(p.getName())){
                    flag = true;
                }
            }
            if(flag){
                AlertUtils.alertError("参数已存在");
                return;
            }
            programUserParamList.add(p);
            resetUserParam();
        });
        //用户参数删除
        paramBtnDel.setOnAction(event -> {
            programUserParamList.remove(selectedUserParam);
            try {
                programService.delUserParamByProgramId(selectedUserParam.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
            resetUserParam();
            selectedUserParam = null;
        });
        //用户参数修改
        paramBtnEdit.setOnAction(event -> {
            ProgramUserParam  p = selectedUserParam;
            p.setParam(choiceBoxParam.getValue());
            p.setName(choiceBoxParam.getValue().getName());
            p.setUnit(choiceBoxUnit.getValue());
            p.setDefaultVal(textDefaultValue.getText().trim());
            int index = programUserParamList.indexOf(selectedUserParam);
            programUserParamList.remove(selectedUserParam);
            programUserParamList.add(index,p);
            resetUserParam();
        });
        //用户参数上移
        paramBtnUp.setOnAction(event -> {
            int index = programUserParamList.indexOf(selectedUserParam);
            if(index>0){
                int last = index - 1;
                programUserParamList.get(last).setNum(index+1);
                programUserParamList.get(index).setNum(last+1);
                Collections.swap(programUserParamList,index,last);
                refreshUserParam();
            }
        });
        //用户参数下移
        paramBtnDown.setOnAction(event -> {
            int index = programUserParamList.indexOf(selectedUserParam);
            if(index < (programUserParamList.size()-1)){
                int next = index+1;
                programUserParamList.get(index).setNum(next+1);
                programUserParamList.get(next).setNum(index+1);
                Collections.swap(programUserParamList,next,index);
                refreshUserParam();
            }
        });
        //tab4 save
        tab4BtnSave.setOnAction(event -> {
            if(programUserParamList==null && programUserParamList.size()==0){
                AlertUtils.alertError("请先添加用户参数");
                return;
            }
            try {
                programService.batchSaveUserParam(programUserParamList,selectedProgram.getID());
                AlertUtils.alertInfo("保存用户参数成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        tab5.setOnSelectionChanged(event -> {
            //tab5选中
            if(tab5.isSelected()){
                try {
                    programResultParamList = programService.listResultParam(selectedProgram.getID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resetResultParam();
                //用户参数
                try {
                    resultParamList = paramService.listResultParamByStandId(selectedStandard.getId());
                    choiceBoxParamResultName.getItems().clear();
                    choiceBoxParamResultName.getItems().addAll(resultParamList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        choiceBoxParamResultName.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>-1){
                Param param = resultParamList.get(newValue.intValue());
                List<String> unitList = null;
                try {
                    unitList = unitService.listByBaseCode(param.getUnit());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                choiceBoxParamResultUnit.getItems().clear();
                if(unitList!=null && unitList.size()>0){
                    choiceBoxParamResultUnit.getItems().addAll(unitList);
                    choiceBoxParamResultUnit.setValue(param.getUnit());
                }else{
                    choiceBoxParamResultUnit.getItems().add(param.getUnit());
                    choiceBoxParamResultUnit.setValue(param.getUnit());
                }
            }
        });

        checkBoxResult.setOnAction(event -> {
            if(checkBoxResult.isSelected()){
                textParamResultTop.setDisable(false);
                textParamResultBottom.setDisable(false);
            }else{
                textParamResultTop.setDisable(true);
                textParamResultBottom.setDisable(true);
            }
        });
        //结果参数列表选择
        tableViewResult.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>-1 && programResultParamList!=null && programResultParamList.size()>0){
                selectedResultParam = programResultParamList.get(newValue.intValue());
                choiceBoxParamResultName.setValue(selectedResultParam.getParam());
                choiceBoxParamResultUnit.setValue(selectedResultParam.getUnit());
                if(selectedResultParam.getResultFlag()){
                    checkBoxResult.setSelected(true);
                    textParamResultBottom.setDisable(false);
                    textParamResultTop.setDisable(false);
                }else{
                    checkBoxResult.setSelected(false);
                    textParamResultBottom.setDisable(true);
                    textParamResultTop.setDisable(true);
                }
                textParamResultBottom.setText(selectedResultParam.getLow());
                textParamResultTop.setText(selectedResultParam.getUp());
                btnResultAdd.setDisable(false);
                btnResultEdit.setDisable(false);
                btnResultDel.setDisable(false);
                btnResultUp.setDisable(false);
                btnResultDown.setDisable(false);
            }
        });
        //结果参数添加
        btnResultAdd.setOnAction(event -> {
            ProgramResultParam  p = getResultParam();
            boolean flag = false;
            for (ProgramResultParam up : programResultParamList){
                if(up.getName().equals(p.getName())){
                    flag = true;
                }
            }
            if(flag){
                AlertUtils.alertError("参数已存在");
                return;
            }
            programResultParamList.add(p);
            resetResultParam();
        });
        //结果参数删除
        btnResultDel.setOnAction(event -> {
            programResultParamList.remove(selectedResultParam);
            resetResultParam();
            selectedResultParam = null;
        });
        //结果参数修改
        btnResultEdit.setOnAction(event -> {
            ProgramResultParam  p = selectedResultParam;
            p.setParam(choiceBoxParamResultName.getValue());
            p.setName(choiceBoxParamResultName.getValue().getName());
            p.setUnit(choiceBoxParamResultUnit.getValue());
            p.setResultFlag(checkBoxResult.isSelected());
            p.setUp(textParamResultTop.getText().trim());
            p.setLow(textParamResultBottom.getText().trim());
            int index = programResultParamList.indexOf(selectedResultParam);
            programResultParamList.remove(selectedResultParam);
            programResultParamList.add(index,p);
            resetResultParam();
        });
        //结果参数上移
        btnResultUp.setOnAction(event -> {
            int index = programResultParamList.indexOf(selectedResultParam);
            if(index>0){
                int last = index - 1;
                programResultParamList.get(last).setNum(index+1);
                programResultParamList.get(index).setNum(last+1);
                Collections.swap(programResultParamList,last,index);
                refreshResultParam();
            }
        });
        //结果参数下移
        btnResultDown.setOnAction(event -> {
            int index = programResultParamList.indexOf(selectedResultParam);
            if(index < (programResultParamList.size()-1)){
                int next = index+1;
                programResultParamList.get(index).setNum(next+1);
                programResultParamList.get(next).setNum(index+1);
                Collections.swap(programResultParamList,index,next);
                refreshResultParam();
            }
        });

        tab5BtnSave.setOnAction(event -> {
            if(programResultParamList==null || programResultParamList.size()==0){
                AlertUtils.alertError("请先添加结果参数");
                return;
            }
            try {
                programService.batchSaveResultParam(programResultParamList,selectedProgram.getID());
                AlertUtils.alertInfo("保存结果参数成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void initControlData() {
        choiceBoxStartControl.setValue(selectedControl.getStart());
        textStartControl.setText(selectedControl.getStartValue());

        choiceBoxEndControl.setValue(selectedControl.getEnd());
        textEndControl.setText(selectedControl.getEndValue());
    }

    /**
     * 校验程控添加
     * @param p
     * @return
     */
    private boolean validataProgramControl(ProgramControl p) {
        if(StringUtils.isBlank(p.getStart())){
            AlertUtils.alertError("请选择始控方式");
            return false;
        }
        if(StringUtils.isBlank(p.getStartValue())){
            AlertUtils.alertError("请输入始控方式值");
            return false;
        }
        if(!ValidateUtils.zIndex(p.getStartValue()) && !ValidateUtils.posttiveFloat(p.getStartValue())){
            AlertUtils.alertError("始控方式值为数字");
            return false;
        }
        if(StringUtils.isNotBlank(p.getEnd()) && StringUtils.isBlank(p.getEndValue())){
            AlertUtils.alertError("请输入终控方式值");
            return false;
        }
        if(StringUtils.isNotBlank(p.getEndValue()) && !ValidateUtils.zIndex(p.getEndValue()) && !ValidateUtils.posttiveFloat(p.getEndValue())){
            AlertUtils.alertError("终控方式值为数字");
            return false;
        }

        return true;
    }

    /**
     * 获取程控组件值
     * @return
     */
    private ProgramControl getProgramControl() {
        ProgramControl p = new ProgramControl();
        p.setNum(programControlList.size()+1);
        p.setProgram(selectedProgram);
        p.setStart(choiceBoxStartControl.getValue());
        if(StringUtils.isNotBlank(textStartControl.getText())){
            p.setStartValue(textStartControl.getText().trim());
        }
        p.setStartUnit(labelStartControlUnit.getText());
        p.setEnd(choiceBoxEndControl.getValue());
        if(StringUtils.isNotBlank(textEndControl.getText())){
            p.setEndValue(textEndControl.getText().trim());
        }
        p.setEndUnit(labelEndControlUnit.getText());
        System.out.println(p);
        return p;
    }

    /**
     * 初始化tab1数据
     */
    private void initTab1Data() {
        if(selectedProgram!=null){
            if(Constants.INT_ZERO == selectedProgram.getDirect()){
                radioLa.setSelected(true);
            }
            if(Constants.INT_ONE == selectedProgram.getDirect()){
                radioYa.setSelected(true);
            }
            choiceBoxShape.setValue(selectedProgram.getShapeName());
            choiceBoxTransform.setValue(selectedProgram.getTransformSensor());
            checkBoxBreakage.setSelected(selectedProgram.isAutoBreakage());
            if(selectedProgram.isAutoBreakage()){
                ableBreakage();
            }else{
                disableBreakage();
            }
            if(selectedProgram.getGtForce()!=null && selectedProgram.getGtForce()!=0F){
                textBreakage1.setText(selectedProgram.getGtForce()+Constants.BLANK);
            }else{
                textBreakage1.setText(null);
            }
            if(selectedProgram.getLtRate()!=null && selectedProgram.getLtRate()!=0F){
                textBreakage2.setText(selectedProgram.getLtRate()+Constants.BLANK);
            }else{
                textBreakage2.setText(null);
            }
            if(selectedProgram.getLtMearure()!=null && selectedProgram.getLtMearure()!=0F){
                textBreakage3.setText(selectedProgram.getLtMearure()+Constants.BLANK);
            }else{
                textBreakage3.setText(null);
            }
            checkBoxTransformChange.setSelected(selectedProgram.isTransformChange());
            if(selectedProgram.isTransformChange()){
                ableTransform();
            }else{
                disableTransform();
            }
            radioTransformChange1.setSelected(selectedProgram.isAbandonExtend());
            if(selectedProgram.getDeformExtend()!=null && selectedProgram.getDeformExtend()!=0F){
                radioTransformChange2.setSelected(true);
                textTransformchange.setDisable(false);
                textTransformchange.setText(selectedProgram.getDeformExtend()+Constants.BLANK);
            }else{
                textTransformchange.setDisable(true);
                textTransformchange.setText(null);
            }
            if(selectedProgram.isTime()){
                textFixTime.setDisable(false);
                checkBoxFixTime.setSelected(true);
            }
            if(selectedProgram.getTimeValue()!=null && selectedProgram.getTimeValue()!=0F){
                textFixTime.setText(selectedProgram.getTimeValue()+Constants.BLANK);
            }else {
                textFixTime.setText(null);
            }
            if(selectedProgram.isLoad()){
                textFixN.setDisable(false);
                checkBoxFixN.setSelected(true);
            }
            if(selectedProgram.getLoadValue()!=null && selectedProgram.getLoadValue()!=0F){
                textFixN.setText(selectedProgram.getLoadValue()+Constants.BLANK);
            }else{
                textFixN.setText(null);
            }
            if(selectedProgram.isTransform()){
                textFixTransform.setDisable(false);
                checkBoxFixTransform.setSelected(true);
            }
            if(selectedProgram.getTransformValue()!=null && selectedProgram.getTransformValue()!=0F){
                textFixTransform.setText(selectedProgram.getTransformValue()+Constants.BLANK);
            }else {
                textFixTransform.setText(null);
            }
            if(selectedProgram.isPos()){
                textFixDisplacement.setDisable(false);
                checkBoxFixDisplacement.setSelected(true);
            }
            if(selectedProgram.getPosValue()!=null && selectedProgram.getPosValue()!=0F){
                textFixDisplacement.setText(selectedProgram.getPosValue()+Constants.BLANK);
            }else{
                textFixDisplacement.setText(null);
            }
            checkBoxLoad.setSelected(selectedProgram.isPreload());
            if(selectedProgram.isPreload()){
                ablePreload();
            }else{
                disablePreload();
            }
            if(selectedProgram.getPreloadValue()!=null && selectedProgram.getPreloadValue()!=0F){
                textLoadN.setText(selectedProgram.getPreloadValue()+Constants.BLANK);
            }else{
                textLoadN.setText(null);
            }
            if(selectedProgram.getPreloadSpeed()!=null && selectedProgram.getPreloadSpeed()!=0F){
                textLoadSpeed.setText(selectedProgram.getPreloadSpeed()+Constants.BLANK);
            }else{
                textLoadSpeed.setText(null);
            }
            checkBoxDisplacement.setSelected(selectedProgram.isClearDisp());
            checkBoxTransform.setSelected(selectedProgram.isClearTransform());
            checkBoxN.setSelected(selectedProgram.isClearN());
            checkBoxTestEnd.setSelected(selectedProgram.isReturn());
            if(selectedProgram.isReturn()){
                textBackSpeed.setDisable(false);
            }else{
                textBackSpeed.setDisable(true);
            }
            if(selectedProgram.getReturnSpeed()!=null && selectedProgram.getReturnSpeed()!=0F){
                textBackSpeed.setText(selectedProgram.getReturnSpeed()+Constants.BLANK);
            }else {
                textBackSpeed.setText(null);
            }
            if(selectedProgram.getClearDot()!=null && selectedProgram.getClearDot()!=0F){
                textPoint.setText(selectedProgram.getClearDot()+Constants.BLANK);
            }else{
                textPoint.setText(null);
            }
        }
    }

    private boolean validata(Program p) {
        if(StringUtils.isBlank(p.getName())){
            AlertUtils.alertError("方案名称不能为空");
            return false;
        }
        if(StringUtils.isBlank(p.getStandard())){
            AlertUtils.alertError("请选择标准");
            return false;
        }
        return true;
    }

    /**
     * 获取组件值
     * @return
     */
    private Program getProgram() {
        Program p = new Program();
        p.setName(textProgramName.getText().trim());
        p.setStandard(choiceBoxStanderdSelect.getValue().getCode());
        if (radioLa.isSelected()) {
            //拉向为1
            p.setDirect(Constants.INT_ONE);
        }
        if (radioYa.isSelected()) {
            //压向为2
            p.setDirect(Constants.INT_TWO);
        }
        p.setShapeName(choiceBoxShape.getValue());
        p.setTransformSensor(choiceBoxTransform.getValue());
        p.setAutoBreakage(checkBoxBreakage.isSelected());
        if (StringUtils.isBlank(textBreakage1.getText())) {
            p.setGtForce(0F);
        } else {
            p.setGtForce(Float.valueOf(textBreakage1.getText().trim()));
        }
        if (StringUtils.isBlank(textBreakage2.getText())) {
            p.setLtRate(0F);
        } else {
            p.setLtRate(Float.valueOf(textBreakage2.getText().trim()));
        }
        if (StringUtils.isBlank(textBreakage3.getText())) {
            p.setLtMearure(0F);
        } else {
            p.setLtMearure(Float.valueOf(textBreakage3.getText().trim()));
        }
        p.setTransformChange(checkBoxTransformChange.isSelected());
        p.setAbandonExtend(radioTransformChange1.isSelected());
        if (StringUtils.isBlank(textTransformchange.getText())) {
            p.setDeformExtend(0F);
        } else {
            p.setDeformExtend(Float.valueOf(textTransformchange.getText().trim()));
        }
        p.setTime(checkBoxFixTime.isSelected());
        if (StringUtils.isBlank(textFixTime.getText())) {
            p.setTimeValue(0F);
        } else {
            p.setTimeValue(Float.valueOf(textFixTime.getText().trim()));
        }
        p.setLoad(checkBoxFixN.isSelected());
        if (StringUtils.isBlank(textFixN.getText())) {
            p.setLoadValue(0F);
        } else {
            p.setLoadValue(Float.valueOf(textFixN.getText().trim()));
        }
        p.setTransform(checkBoxFixTransform.isSelected());
        if (StringUtils.isBlank(textFixTransform.getText())) {
            p.setTransformValue(0F);
        } else {
            p.setTransformValue(Float.valueOf(textFixTransform.getText().trim()));
        }
        p.setPos(checkBoxFixDisplacement.isSelected());
        if (StringUtils.isBlank(textFixDisplacement.getText())) {
            p.setPosValue(0F);
        } else {
            p.setPosValue(Float.valueOf(textFixDisplacement.getText().trim()));
        }
        p.setPreload(checkBoxLoad.isSelected());
        if (StringUtils.isBlank(textLoadN.getText())) {
            p.setPreloadValue(0F);
        } else {
            p.setPreloadValue(Float.valueOf(textLoadN.getText().trim()));
        }
        if (StringUtils.isBlank(textLoadSpeed.getText())) {
            p.setPreloadSpeed(0F);
        } else {
            p.setPreloadSpeed(Float.valueOf(textLoadSpeed.getText().trim()));
        }
        p.setClearDisp(checkBoxDisplacement.isSelected());
        p.setClearTransform(checkBoxTransform.isSelected());
        p.setClearN(checkBoxN.isSelected());
        p.setReturn(checkBoxTestEnd.isSelected());
        if (StringUtils.isBlank(textBackSpeed.getText())) {
            p.setReturnSpeed(0F);
        } else {
            p.setReturnSpeed(Float.valueOf(textBackSpeed.getText().trim()));
        }
        if (StringUtils.isBlank(textPoint.getText())) {
            p.setClearDot(0);
        } else {
            p.setClearDot(Integer.valueOf(textPoint.getText().trim()));
        }
        p.setDefault(false);
        return p;
    }

    /**
     *自动断裂输入框不可用
     */
    private void disableBreakage(){
        textBreakage1.setDisable(true);
        textBreakage2.setDisable(true);
        textBreakage3.setDisable(true);
    }

    /**
     * 自动断裂输入框可用
     */

    private void ableBreakage(){
        textBreakage1.setDisable(false);
        textBreakage2.setDisable(false);
        textBreakage3.setDisable(false);
    }

    /**
     * 变形切换不可用
     */
    private void disableTransform(){
        radioTransformChange1.setDisable(true);
        radioTransformChange2.setDisable(true);
    }

    /**
     * 变形切换不可用
     */
    private void ableTransform(){
        radioTransformChange1.setDisable(false);
        radioTransformChange2.setDisable(false);
    }

    /**
     * 实验结束参数输入框不可用
     */
    private void disableTestEnd(){
        textFixTime.setDisable(true);
        textFixN.setDisable(true);
        textFixDisplacement.setDisable(true);
        textFixTransform.setDisable(true);
    }

    /**
     * 预加载不可用
     */
    private void disablePreload(){
        textLoadN.setDisable(true);
        textLoadSpeed.setDisable(true);
    }

    /**
     * 预加载可用
     */
    private void ablePreload(){
        textLoadN.setDisable(false);
        textLoadSpeed.setDisable(false);
    }

    /**
     * 程控方式组件不可用
     */
    private void disableControl(){
        choiceBoxStartControl.setDisable(true);
        choiceBoxEndControl.setDisable(true);
        textStartControl.setDisable(true);
        textEndControl.setDisable(true);
    }

    private void ableControl(){
        choiceBoxStartControl.setDisable(false);
        choiceBoxEndControl.setDisable(false);
        textStartControl.setDisable(false);
        textEndControl.setDisable(false);
    }

    private void clearControl(){
        choiceBoxStartControl.setValue(null);
        choiceBoxEndControl.setValue(null);
        textStartControl.setText(null);
        textEndControl.setText(null);
        labelStartControlUnit.setText(null);
        labelStartControlTip.setText(null);
        labelEndControlUnit.setText(null);
        labelEndControlTip.setText(null);
    }

    /**
     * 程控按钮保存状态
     */
    private void btnAddControl(){
        btnCAdd.setDisable(true);
        btnCInsert.setDisable(true);
        btnCEdit.setDisable(true);
        btnCDel.setDisable(true);
        btnCSave.setDisable(false);
        btnCReset.setDisable(false);
    }

    /**
     * 程控重置
     */
    private void resetControl(){
        disableControl();
        clearControl();
        btnCAdd.setDisable(false);
        btnCDel.setDisable(true);
        btnCInsert.setDisable(true);
        btnCEdit.setDisable(true);
        btnCSave.setDisable(true);
        btnCReset.setDisable(true);
    }

    /**
     * 刷新程控树
     */
    private void refreshControlTree(){
        treeView.getRoot().getChildren().clear();
        List<TreeItem<String>> treeItems = new ArrayList<>();
        if(programControlList!=null && programControlList.size()>0){
            int i=1;
            for (ProgramControl p:programControlList){
                treeItems.add(new TreeItem<>(i++ + p.toString()));
            }
        }
        treeView.getRoot().getChildren().addAll(treeItems);
    }

    /**
     * 重置用户参数界面
     */
    private void resetUserParam(){
        choiceBoxParam.setValue(null);
        choiceBoxUnit.setValue(null);
        checkBoxDefaultValue.setSelected(false);
        textDefaultValue.clear();
        textDefaultValue.setDisable(true);
        paramBtnAdd.setDisable(false);
        paramBtnDel.setDisable(true);
        paramBtnEdit.setDisable(true);
        paramBtnUp.setDisable(true);
        paramBtnDown.setDisable(true);
        //刷新列表
        refreshUserParam();
    }

    private void refreshUserParam(){
        tableViewParam.getItems().clear();
        if(programUserParamList!=null && programUserParamList.size()>0){
            tableViewParam.getItems().addAll(programUserParamList);
        }
    }

    private void refreshResultParam(){
        tableViewResult.getItems().clear();
        if(programResultParamList!=null && programResultParamList.size()>0){
            tableViewResult.getItems().addAll(programResultParamList);
        }
    }

    private ProgramUserParam getUserParam(){
        ProgramUserParam p = new ProgramUserParam();
        p.setParam(choiceBoxParam.getValue());
        p.setName(choiceBoxParam.getValue().getName());
        p.setUnit(choiceBoxUnit.getValue());
        p.setDefaultVal(textDefaultValue.getText().trim());
        p.setNum(programUserParamList.size()+1);
        return p;
    }

    private ProgramResultParam getResultParam(){
        ProgramResultParam p = new ProgramResultParam();
        p.setParam(choiceBoxParamResultName.getValue());
        p.setName(choiceBoxParamResultName.getValue().getName());
        p.setUnit(choiceBoxParamResultUnit.getValue());
        p.setResultFlag(checkBoxResult.isSelected());
        p.setUp(textParamResultTop.getText().trim());
        p.setLow(textParamResultBottom.getText().trim());
        p.setNum(programResultParamList.size()+1);
        return p;
    }

    /**
     * 重置结果参数界面
     */
    private void resetResultParam(){
        choiceBoxParamResultName.setValue(null);
        choiceBoxParamResultUnit.setValue(null);
        checkBoxResult.setSelected(false);
        textParamResultBottom.clear();
        textParamResultBottom.setDisable(true);
        textParamResultTop.clear();
        textParamResultTop.setDisable(true);
        btnResultAdd.setDisable(false);
        btnResultDel.setDisable(true);
        btnResultEdit.setDisable(true);
        btnResultUp.setDisable(true);
        btnResultDown.setDisable(true);
        //刷新列表
        refreshResultParam();
    }

}
