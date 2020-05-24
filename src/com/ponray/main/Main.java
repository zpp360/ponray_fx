package com.ponray.main;

import com.healthmarketscience.jackcess.Database;
import com.ponray.constans.Constants;
import com.ponray.entity.Program;
import com.ponray.entity.ProgramUserParam;
import com.ponray.entity.Test;
import com.ponray.entity.TestData;
import com.ponray.enums.Axis;
import com.ponray.serial.SerialPortManager;
import com.ponray.service.ProgramService;
import com.ponray.service.TestService;
import com.ponray.task.DataTask;
import com.ponray.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main extends Application {

    private static Stage stage = null;

    //顶部值
    public static Label lableNum1 = null; //力
    public static Label labelTop = null;  //峰值
    public static Label lableNum2 = null; //位移
    public static Label lableNum3 = null; //变形
    public static Label lableNum4 = null; //时间
    public static Float topN = 0F;

    private static TabPane tabPane = null;
    private static Tab tab1 = null;
    private static Tab tab2 = null;
    private static Tab tab3 = null;
    private static Tab tab4 = null;

    //--------------------------------tab1 start-----------------------------------
    private static ChoiceBox<Program> choiceBoxProgram = new ChoiceBox<>();
    private static TextField textFileName = new TextField();
    private static TreeView<String> treeView = new TreeView<>();
    private static TreeItem<String> treeRoot = new TreeItem<>("方案简略信息：");
    private static Button btnAddTest = new Button("添加试样");
    private static Button btnDelTest = new Button("删除试样");
    private static Button btnSaveParam = new Button("保存参数");
    private static TableView tableView = new TableView<>();
    private static List<Program> programList = null;
    public static Program selectedProgram = null;
    //选中的实验方案用户参数列表
    private static List<ProgramUserParam> userParamList = null;
    //实验参数列表
    private static ObservableList<HashMap<String,String>> allData = FXCollections.observableArrayList();
    //选中的用户参数
    private static HashMap<String,String> selectedUserParam = null;
    private static int selectedUserParamIndex = 0;
    private static HashMap<String,String> editDataRow = null;
    //清零按钮
    public static Button button1 = null;
    public static Button button2 = null;
    public static Button button3 = null;
    //--------------------------------tab1 end-------------------------------------

    //--------------------------------tab2 start-----------------------------------
    public static LineChart mainChart = null;
    //线
    public static XYChart.Series<Number,Number> series1 = new XYChart.Series();
    private static NumberAxis xAxis = new NumberAxis();
    private static NumberAxis yAxis = new NumberAxis();
    //--------------------------------tab2 end-------------------------------------

    //-------------tab3-----------
    private static LineChart twoChart = null;
    private static LineChart threeChart = null;
    private static LineChart fourChart = null;
    private static String xAxisName = Axis.TIME.getName();
    private static String yAxisNmae = Axis.N.getName();


    //------------tab3 end --------------

    //-------------tab4-----------
    private static ChoiceBox<Program> programSearchChoiceBox = new ChoiceBox<>();
    private static Program searchSelectProgram = null;
    private static Button searchBtn = new Button("查询");
    private static Button viewLineBtn = new Button("观看曲线");
    private static Button reportBtn = new Button("出报告");
    private static TableView<Test> testTableView = new TableView<>();
    private static TableColumn<Test,Long> testColumnNo = new TableColumn("实验编号");
    private static TableColumn<Test,String> testColumnProgram = new TableColumn("实验方案");
    private static TableColumn<Test,String> testColumnFileName = new TableColumn("文件名");
    private static TableColumn<Test, java.sql.Date> testColumnDate = new TableColumn("实验日期");
    private static TableColumn<Test,String> testColumnStand = new TableColumn("执行标准");
    private static TableColumn<Test,String> testColumnShap = new TableColumn("试样形状");
    private static TableColumn<Test,String> testColumnTransform = new TableColumn("变形计算选择");


    //------------tab4 end --------------
    public static MenuItem offlineItem = new MenuItem("脱机");

    //右侧速度滑动条
    private static Slider speedSlider = new Slider();
    private static TextField speedTextField = new TextField("5");
    private static Button speedButton = new Button("设定");

    //右侧操作按钮
    public static Button upBt = null;
    public static Button downBt = null;
    public static Button startBt = null;
    public static Button stopBt = null;
    public static Button resetBt = null;

    //实验开始标志
    public static boolean startFlag = false;
    //实验开始时间，在UIonlin里使用计算运行时间
    public static Long startTime = 0L;
    //每隔20毫秒运行一次
    public static Long periodTime = 50L;

    public static Long sum = 0L;
    //实验中实验，在UIOnline里保存
    public static Test startTest = null;
    //实验数据
    public static List<TestData> dataList = new ArrayList<>();
    private static ProgramService programService = new ProgramService();
    private static TestService testService = new TestService();

    public static String testName = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        //创建菜单，初始化语言
        MenuBar menuBar = null;

        try {
            menuBar = createMenuBar();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tabPane = new TabPane();
        tab1 = new Tab(Constants.language.getProperty("user_param"));
        tab2 = new Tab(Constants.language.getProperty("single_picture"));
        tab3 = new Tab(Constants.language.getProperty("more_picture"));
        tab4 = new Tab(Constants.language.getProperty("search"));

        //力 显示框布局
        lableNum1 = new Label("0.0000");
        lableNum1.setMinSize(140,55);
        lableNum1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum1.setAlignment(Pos.CENTER);
        lableNum1.setStyle("-fx-background-color: #002060;");
        lableNum1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum1.setTextFill(Color.web("#fff"));

        Label labelN1 = new Label("N");
        labelN1.setMinSize(50,26);
        labelN1.setStyle("-fx-background-color: #002060;");
        labelN1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN1.setTextFill(Color.web("#fff"));
        labelN1.setAlignment(Pos.CENTER);
        labelN1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName1 = new Label(Constants.language.getProperty("n"));
        labelName1.setMinSize(50,23);
        labelName1.setStyle("-fx-background-color: #5FB41B;");
        labelName1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName1.setTextFill(Color.web("#fff"));
        labelName1.setAlignment(Pos.CENTER);
        labelName1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        VBox vBox1 = new VBox();
        //调整上下组件间距
        vBox1.setSpacing(5);
        vBox1.setPadding(new Insets(0,5,0,10));
        vBox1.getChildren().addAll(labelN1,labelName1);

        button1 = new Button(Constants.language.getProperty("clear"));
        button1.setMinSize(50,53);

        GridPane topGrid1 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid1.add(lableNum1,0,0,1,2);
        topGrid1.add(vBox1,1,0);
        topGrid1.add(button1,2,0,1,2);
        topGrid1.setPadding(new Insets(5));
        topGrid1.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));
        //lableNum1.prefWidthProperty().bind(topGrid1.widthProperty());

        //峰值显示布局
        //力峰值
        labelTop = new Label("0.0000");
        labelTop.setMinSize(140,55);
        labelTop.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        labelTop.setAlignment(Pos.CENTER);
        labelTop.setStyle("-fx-background-color: #002060;");
        labelTop.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1,1),Insets.EMPTY)));
        labelTop.setTextFill(Color.web("#fff"));

        Label labelNTop = new Label("N");
        labelNTop.setMinSize(50,26);
        labelNTop.setStyle("-fx-background-color: #002060;");
        labelNTop.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelNTop.setTextFill(Color.web("#fff"));
        labelNTop.setAlignment(Pos.CENTER);
        labelNTop.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelNameTop = new Label("峰值");
        labelNameTop.setMinSize(50,23);
        labelNameTop.setStyle("-fx-background-color: #5FB41B;");
        labelNameTop.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelNameTop.setTextFill(Color.web("#fff"));
        labelNameTop.setAlignment(Pos.CENTER);
        labelNameTop.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        VBox vBoxTop = new VBox();
        //调整上下组件间距
        vBoxTop.setSpacing(5);
        vBoxTop.setPadding(new Insets(0,5,0,10));
        vBoxTop.getChildren().addAll(labelNTop,labelNameTop);

        GridPane topGridTop = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGridTop.add(labelTop,0,0,1,2);
        topGridTop.add(vBoxTop,1,0);
        topGridTop.setPadding(new Insets(5));
        topGridTop.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));
        //lableNum1.prefWidthProperty().bind(topGrid1.widthProperty());

        //位移显示框布局
        lableNum2 = new Label("0.0000");
        lableNum2.setMinSize(140,55);
        lableNum2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum2.setAlignment(Pos.CENTER);
        lableNum2.setStyle("-fx-background-color: #002060;");
        lableNum2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum2.setTextFill(Color.web("#fff"));


        Label labelN2 = new Label("mm");
        labelN2.setMinSize(50,26);
        labelN2.setStyle("-fx-background-color: #002060;");
        labelN2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN2.setTextFill(Color.web("#fff"));
        labelN2.setAlignment(Pos.CENTER);
        labelN2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName2 = new Label(Constants.language.getProperty("displacement"));
        labelName2.setMinSize(50,23);
        labelName2.setStyle("-fx-background-color: #5FB41B;");
        labelName2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName2.setTextFill(Color.web("#fff"));
        labelName2.setAlignment(Pos.CENTER);
        labelName2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        VBox vBox2 = new VBox();
        //调整上下组件间距
        vBox2.setSpacing(5);
        vBox2.setPadding(new Insets(0,5,0,10));
        vBox2.getChildren().addAll(labelN2,labelName2);

        button2 = new Button(Constants.language.getProperty("clear"));
        button2.setMinSize(50,53);

        GridPane topGrid2 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid2.add(lableNum2,0,0,1,2);
        topGrid2.add(vBox2,1,0);
        topGrid2.add(button2,2,0,1,2);
        topGrid2.setPadding(new Insets(5));
        topGrid2.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        //变形显示框布局
        lableNum3 = new Label("0.0000");
        lableNum3.setMinSize(140,55);
        lableNum3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum3.setAlignment(Pos.CENTER);
        lableNum3.setStyle("-fx-background-color: #002060;");
        lableNum3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum3.setTextFill(Color.web("#fff"));


        Label labelN3 = new Label("mm");
        labelN3.setMinSize(50,26);
        labelN3.setStyle("-fx-background-color: #002060;");
        labelN3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN3.setTextFill(Color.web("#fff"));
        labelN3.setAlignment(Pos.CENTER);
        labelN3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName3 = new Label(Constants.language.getProperty("transformation"));
        labelName3.setMinSize(50,23);
        labelName3.setStyle("-fx-background-color: #5FB41B;");
        labelName3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName3.setTextFill(Color.web("#fff"));
        labelName3.setAlignment(Pos.CENTER);
        labelName3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        VBox vBox3 = new VBox();
        //调整上下组件间距
        vBox3.setSpacing(5);
        vBox3.setPadding(new Insets(0,5,0,10));
        vBox3.getChildren().addAll(labelN3,labelName3);

        button3 = new Button(Constants.language.getProperty("clear"));
        button3.setMinSize(50,53);

        GridPane topGrid3 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid3.add(lableNum3,0,0,1,2);
        topGrid3.add(vBox3,1,0);
        topGrid3.add(button3,2,0,1,2);
        topGrid3.setPadding(new Insets(5));
        topGrid3.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        //时间显示框布局
        lableNum4 = new Label("000000");
        lableNum4.setMinSize(140,55);
        lableNum4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum4.setAlignment(Pos.CENTER);
        lableNum4.setStyle("-fx-background-color: #002060;");
        lableNum4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum4.setTextFill(Color.web("#fff"));


        Label labelN4 = new Label("s");
        labelN4.setMinSize(50,26);
        labelN4.setStyle("-fx-background-color: #002060;");
        labelN4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN4.setTextFill(Color.web("#fff"));
        labelN4.setAlignment(Pos.CENTER);
        labelN4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName4 = new Label(Constants.language.getProperty("time"));
        labelName4.setMinSize(50,23);
        labelName4.setStyle("-fx-background-color: #5FB41B;");
        labelName4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName4.setTextFill(Color.web("#fff"));
        labelName4.setAlignment(Pos.CENTER);
        labelName4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        VBox vBox4 = new VBox();
        //调整上下组件间距
        vBox4.setSpacing(5);
        vBox4.setPadding(new Insets(0,5,0,10));
        vBox4.getChildren().addAll(labelN4,labelName4);

//        Button button4 = new Button(Constants.language.getProperty("clear"));
//        button4.setMinSize(50,53);

        GridPane topGrid4 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid4.add(lableNum4,0,0,1,2);
        topGrid4.add(vBox4,1,0);
//        topGrid4.add(button4,2,0,1,2);
        topGrid4.setPadding(new Insets(5));
        topGrid4.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        HBox hBox = new HBox();
        hBox.setSpacing(1);
        hBox.getChildren().addAll(topGrid1,topGridTop,topGrid2,topGrid3,topGrid4);



        //右侧操作按钮
        Label redLabel = new Label();
        redLabel.setMinSize(250,3);
        redLabel.setStyle("-fx-background-color: red");

        Label statusNameLabel = new Label(Constants.language.getProperty("status")+":");
        statusNameLabel.setBorder(null);

        Label statusLable = new Label(Constants.language.getProperty("off-line"));
        Image offLineImg = new Image(getClass().getResourceAsStream("/images/off_line.png"));
        statusLable.setGraphic(new ImageView(offLineImg));
        statusLable.setPadding(new Insets(0,0,0,50));

        Label nullLable = new Label();
        nullLable.setMinSize(250,200);

        HBox speedHBox = new HBox();
        speedTextField.setPrefSize(80,20);
        speedHBox.getChildren().addAll(new Label("速度："),speedTextField,new Label("mm/min"),speedButton);
        speedSlider.setMin(0);
        speedSlider.setMax(500);
        speedSlider.setValue(50);
        speedSlider.setShowTickLabels(false);
        speedSlider.setShowTickMarks(false);
        speedSlider.setStyle("-fx-background-color: #FFF;");
        speedSlider.setMaxWidth(200);

        upBt = new Button(Constants.language.getProperty("up"));
        upBt.setMinSize(80,40);
        upBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        downBt = new Button(Constants.language.getProperty("down"));
        downBt.setMinSize(80,40);
        downBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));

        HBox lineOne = new HBox();
        lineOne.getChildren().addAll(upBt,downBt);
        lineOne.setSpacing(50);

        startBt = new Button(Constants.language.getProperty("start"));
        startBt.setMinSize(80,40);
        startBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        stopBt = new Button(Constants.language.getProperty("end"));
        stopBt.setMinSize(80,40);
        stopBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        HBox lineTwo = new HBox();
        lineTwo.getChildren().addAll(startBt,stopBt);
        lineTwo.setSpacing(50);

        resetBt = new Button("复位");
        resetBt.setMinSize(80,40);
        resetBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
//        clearLoadBt = new Button("力清零");
//        clearLoadBt.setMinSize(80,40);
//        clearLoadBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        HBox lineThree = new HBox();
        lineThree.getChildren().addAll(resetBt);
        lineThree.setSpacing(50);

//        clearPosBt = new Button("位移清零");
//        clearPosBt.setMinSize(80,40);
//        clearPosBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 16));
//        clearTransformBt = new Button("变形清零");
//        clearTransformBt.setMinSize(80,40);
//        clearTransformBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 16));
//        HBox lineFour = new HBox();
//        lineFour.getChildren().addAll(clearPosBt,clearTransformBt);
//        lineFour.setSpacing(50);

        VBox opBtvBox = new VBox();
        opBtvBox.getChildren().addAll(lineOne,lineTwo,lineThree);
        opBtvBox.setSpacing(10);

//        VBox speedVBox = new VBox();
//        speedVBox.getChildren().addAll(speedSlider,speedHBox);
//        speedVBox.setSpacing(10);

        GridPane rightGrid = new GridPane();
        rightGrid.setPadding(new Insets(5));
        rightGrid.setHgap(5);
        rightGrid.setVgap(5);
        rightGrid.setPrefSize(250,500);
        rightGrid.add(redLabel,0,0,2,1);
//        rightGrid.add(statusNameLabel,0,1);
//        rightGrid.add(statusLable,1,1);
        rightGrid.add(nullLable,0,2,2,1);
        rightGrid.add(speedHBox,0,3,2,1);
        rightGrid.add(speedSlider,0,4,2,1);
        rightGrid.add(opBtvBox,0,5,2,1);

        //tabPane
        tabPane.setPrefSize(1000,600);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE,TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE,TabPane.USE_PREF_SIZE);
        //去掉tab3
        tabPane.getTabs().addAll(tab1,tab2,tab4);
        tabPane.setStyle("-fx-tab-min-width: 225px;-fx-tab-max-width: 300px;-fx-tab-min-height: 35px;");
        tabPane.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        tab1.setClosable(false);
        tab1.setStyle("-fx-font-size:20px;");
        tab1.setContent(createTab1());
        tab2.setClosable(false);
        tab2.setStyle("-fx-font-size:20px;");
        tab2.setContent(createTab2());
//        tab3.setClosable(false);
//        tab3.setStyle("-fx-font-size:20px;");
//        tab3.setContent(createTab3());
        tab4.setClosable(false);
        tab4.setStyle("-fx-font-size:20px;");
        tab4.setContent(createTab4());


        //整体布局
        VBox root = new VBox();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        hBox.prefWidthProperty().bind(borderPane.widthProperty());
        borderPane.setRight(rightGrid);
        borderPane.setCenter(tabPane);
        borderPane.setPadding(new Insets(5,0,0,0));
        root.getChildren().addAll(menuBar,borderPane);

        //宽度计算
        menuBar.prefWidthProperty().bind(root.widthProperty());                        //绑定菜单宽度
        borderPane.prefWidthProperty().bind(root.widthProperty());                     //绑定方位布局宽度
        hBox.prefWidthProperty().bind(borderPane.widthProperty());//绑定顶部四个数显布局宽度
        topGrid1.prefWidthProperty().bind(hBox.widthProperty().divide(4));//绑定第1个数显宽度
        topGrid2.prefWidthProperty().bind(hBox.widthProperty().divide(4));//绑定第2个数显宽度
        topGrid3.prefWidthProperty().bind(hBox.widthProperty().divide(4));//绑定第3个数显宽度
        topGrid4.prefWidthProperty().bind(hBox.widthProperty().divide(4));//绑定第4个数显宽度
        lableNum1.prefWidthProperty().bind(hBox.widthProperty().subtract(120));//绑定力显示屏宽度
        lableNum2.prefWidthProperty().bind(hBox.widthProperty().subtract(120));//绑定位移显示屏宽度
        lableNum3.prefWidthProperty().bind(hBox.widthProperty().subtract(120));//绑定变形显示屏宽度
        lableNum4.prefWidthProperty().bind(hBox.widthProperty().subtract(120));//绑定时间显示屏宽度
        tabPane.prefWidthProperty().bind(root.widthProperty().subtract(280));
        tabPane.prefHeightProperty().bind(root.heightProperty().subtract(150));

        intiComp();
        registEvent();

        Scene scene = new Scene(root);
        URL url = this.getClass().getResource("fx.css");
        //加载css
        scene.getStylesheets().add(url.toExternalForm());
        stage.setTitle("拉力试验工具软件");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/soft_name64.png")));
        stage.setScene(scene);
        //控制窗口是否可以缩放
        stage.setResizable(true);
        //全屏
        stage.setMaximized(true);
        stage.show();

    }



    /**
     * 创建菜单并初始化语言变量
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private MenuBar createMenuBar() throws SQLException, ClassNotFoundException, IOException {

        MenuBar menuBar = new MenuBar();

        Menu language = new Menu("语言");
        ToggleGroup languageGroup = new ToggleGroup();

        Connection connection = AccessHelper.getInstance(null).getConnection();
        Statement statement = AccessHelper.getInstance(null).getStatement(connection);
        String sql = "select id,name,file_name,selected from t_language";
        ResultSet rs = AccessHelper.getInstance(null).getResultSet(statement,sql);
        while(rs.next()){
            String languageId = rs.getString("id");
            String fileName = rs.getString("file_name");
            RadioMenuItem item = new RadioMenuItem(rs.getString("name"));
            if(rs.getBoolean("selected")){
                item.setSelected(true);
                setLanguage(fileName);
            }
            item.setToggleGroup(languageGroup);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String updateSql = "update t_language set selected = false";
                    try {
                        statement.executeUpdate(updateSql);
                        updateSql = "update t_language set selected = true where id = " + languageId;
                        statement.executeUpdate(updateSql);
                        //设置语言properties文件
                        setLanguage(fileName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            language.getItems().add(item);
        }


        Menu file = new Menu(Constants.language.getProperty("menu_file"));
        MenuItem createFile = new MenuItem(Constants.language.getProperty("menu_create_file"));
        createFile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/file_create.ico"))));
        MenuItem editFile = new MenuItem(Constants.language.getProperty("menu_edit_file"));
        editFile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/file_edit.ico"))));
        MenuItem delFile = new MenuItem(Constants.language.getProperty("menu_del_file"));
        delFile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/file_del.ico"))));
        MenuItem out = new MenuItem(Constants.language.getProperty("menu_out"));
        out.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/system_out.ico"))));
        //点击退出程序
        out.setOnAction(event -> Platform.exit());
        file.getItems().addAll(createFile,editFile,delFile,new SeparatorMenuItem(),out);


        Menu setMenu = new Menu(Constants.language.getProperty("menu_set"));
        //硬件参数
        MenuItem paramItem = new MenuItem(Constants.language.getProperty("menu_hardware_param"));
        paramItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/hardware_param.ico"))));
        paramItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new UIParamSet().display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setMenu.getItems().addAll(paramItem);

        Menu communicationMenu = new Menu("通讯");
        MenuItem onlineItem = new MenuItem("联机");
        onlineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/on_line.ico"))));
        offlineItem.setDisable(true);
        offlineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/off_line.ico"))));
        communicationMenu.getItems().addAll(onlineItem,offlineItem);

        Menu experimentMenu = new Menu("实验管理");
        MenuItem createExp = new MenuItem("编辑实验方案");
        MenuItem createStandard = new MenuItem("定义标准");
        MenuItem createParam = new MenuItem("定义参数");
        MenuItem createUnit = new MenuItem("单位设定");
        experimentMenu.getItems().addAll(createExp,createStandard,createParam,createUnit);
        createExp.setOnAction(event -> {
            try {
                new UITestProgram().display();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        createStandard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new UIStandard().display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        createParam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new UIParam().display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        createUnit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new UIUnit().display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //联机
        onlineItem.setOnAction(event -> {
            new UIOnline().display();
        });
        //脱机
        offlineItem.setOnAction(event -> {
            SerialPortManager.closePort(UIOnline.mSerialport);
            UIOnline.mCommList = null;
            UIOnline.mSerialport = null;
            offlineItem.setDisable(true);
            //操作按钮初始化状态
            initBt();
            UIOnline.btnOpen.setText("打开串口");
            AlertUtils.alertInfo("关闭串口成功");
        });

        menuBar.getMenus().addAll(file,setMenu,communicationMenu,experimentMenu,language);
        menuBar.setMinHeight(29);
        menuBar.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("/images/menubar_bg.png")),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,null,null)));
        return menuBar;
    }


    /**
     * 创建tab1
     * @return
     */
    private VBox createTab1(){
        VBox vBox = new VBox();

        HBox hBox1 = new HBox();
        Label label1 = new Label("请选择实验方案:");
        label1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 18));
        try {
            programList = programService.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        choiceBoxProgram.converterProperty().set(new StringConverter<Program>() {
            @Override
            public String toString(Program object) {
                return object.getName();
            }

            @Override
            public Program fromString(String string) {
                for(Program p : programList){
                    if(string.equals(p.getName())){
                        return p;
                    }
                }
                return null;
            }
        });
        choiceBoxProgram.setItems(FXCollections.observableArrayList(programList));
        choiceBoxProgram.setMinWidth(150);

        Label label2 = new Label("数据保存名称:");
        label2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 18));

        hBox1.getChildren().addAll(label1,choiceBoxProgram,label2,textFileName);
        hBox1.setPadding(new Insets(20));
        hBox1.setSpacing(50);

        Separator separator = new Separator();
        separator.setMaxHeight(1200);

        HBox hBox2 = new HBox();
        VBox rightVBox = new VBox();
        HBox btnHbox = new HBox();
        btnHbox.getChildren().addAll(btnAddTest,btnDelTest,btnSaveParam,new Label("按回车键保存参数信息"));
        btnHbox.setSpacing(30);
        btnHbox.setPadding(new Insets(10));
        rightVBox.getChildren().addAll(btnHbox,tableView);
        tableView.prefWidthProperty().bind(hBox2.widthProperty().subtract(270));
        hBox2.getChildren().addAll(treeView,rightVBox);
        hBox2.setSpacing(20);
        vBox.getChildren().addAll(hBox1,separator,hBox2);
        vBox.setSpacing(20);

        return vBox;
    }

    /**
     * 创建tab2
     * @return
     */
    private HBox createTab2(){
        HBox main = new HBox();
        mainChart = createChart(xAxisName,yAxisNmae);
        mainChart.prefWidthProperty().bind(tabPane.widthProperty().subtract(200));
        mainChart.prefHeightProperty().bind(tabPane.heightProperty());
        //在此处添加线，在createChart方法中添加不显示线
        series1.getData().add(new XYChart.Data(10,10));
        series1.getData().add(new XYChart.Data(20,20));
        mainChart.setStyle("-fx-stroke: antiquewhite;");
        mainChart.getData().add(series1);
        main.getChildren().add(mainChart);
        return main;
    }

    /**
     * 创建tab3
     * @return
     */
    private HBox createTab3(){
        HBox main = new HBox();
        //图2
        if(selectedProgram!=null){
            twoChart = createChart(selectedProgram.getTwoX(),selectedProgram.getTwoY());
        }else{
            twoChart = createChart(Axis.TIME.getName(),Axis.N.getName());
        }
        twoChart.prefWidthProperty().bind(tabPane.widthProperty().divide(2));
        twoChart.prefHeightProperty().bind(tabPane.heightProperty());
        VBox vBox = new VBox();
        //图3
        if(selectedProgram!=null){
            threeChart = createChart(selectedProgram.getThreeX(),selectedProgram.getThreeY());
        }else{
            threeChart = createChart(Axis.TIME.getName(),Axis.N.getName());
        }
        threeChart.prefWidthProperty().bind(tabPane.widthProperty().divide(2));
        threeChart.prefHeightProperty().bind(tabPane.heightProperty().divide(2));
        //图4
        if(selectedProgram!=null){
            fourChart = createChart(selectedProgram.getFourX(),selectedProgram.getFourY());
        }else{
            fourChart = createChart(Axis.TIME.getName(),Axis.N.getName());
        }
        fourChart.prefWidthProperty().bind(tabPane.widthProperty().divide(2));
        fourChart.prefHeightProperty().bind(tabPane.heightProperty().divide(2));
        vBox.getChildren().addAll(threeChart,fourChart);
        main.getChildren().addAll(twoChart,vBox);
        return main;
    }

    private HBox createTab4(){
        HBox main = new HBox();
//        VBox left = new VBox();
//
//        left.getChildren().addAll(fileSearchBtn,programSearchBtn);
//        left.setSpacing(30);
//        left.setPrefWidth(150);
//        left.setPadding(new Insets(20));
//        left.prefHeightProperty().bind(tabPane.heightProperty());
//        left.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,1,1),Insets.EMPTY)));

        VBox right = new VBox();
        right.prefHeightProperty().bind(tabPane.heightProperty());
        right.prefWidthProperty().bind(tabPane.widthProperty());

        GridPane topPane = new GridPane();
        topPane.add(new Label("请选择实验方案"),0,0);
        programSearchChoiceBox.converterProperty().set(new StringConverter<Program>() {
            @Override
            public String toString(Program object) {
                return object.getName();
            }

            @Override
            public Program fromString(String string) {
                for(Program p : programList){
                    if(string.equals(p.getName())){
                        return p;
                    }
                }
                return null;
            }
        });
        programSearchChoiceBox.getItems().clear();
        programSearchChoiceBox.setItems(FXCollections.observableArrayList(programList));
        programSearchChoiceBox.setMinWidth(150);
        topPane.add(programSearchChoiceBox,1,0);
        topPane.setPadding(new Insets(20));
        programSearchChoiceBox.setPrefSize(200,20);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(topPane);

        HBox btnHbox = new HBox();
        btnHbox.getChildren().addAll(searchBtn,viewLineBtn,reportBtn);
        btnHbox.setPadding(new Insets(0,0,0,30));
        btnHbox.setSpacing(30);

        testTableView.getColumns().clear();
        testColumnNo.setCellValueFactory(new PropertyValueFactory<>("testNum"));
        testColumnProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        testColumnProgram.setPrefWidth(150);
        testColumnFileName.setCellValueFactory(new PropertyValueFactory<>("saveFile"));
        testColumnFileName.setPrefWidth(180);
        testColumnDate.setCellValueFactory(new PropertyValueFactory<>("testTime"));
        testColumnDate.setPrefWidth(120);
        testColumnStand.setCellValueFactory(new PropertyValueFactory<>("standardName"));
        testColumnStand.setPrefWidth(120);
        testColumnShap.setCellValueFactory(new PropertyValueFactory<>("shape"));
        testColumnTransform.setCellValueFactory(new PropertyValueFactory<>("transformSensor"));
        testTableView.getColumns().addAll(testColumnNo,testColumnProgram,testColumnFileName,testColumnDate,testColumnStand,testColumnShap,testColumnTransform);
        right.getChildren().addAll(stackPane,btnHbox,testTableView);
        right.setSpacing(10);

        main.getChildren().addAll(right);
        return main;
    }

    /**
     * 更新坐标轴名称
     * @param axis
     * @param axisType
     * @param name
     */
    private void updateAxisUnit(NumberAxis axis,String axisType,String name){
        if(name==null){
            return;
        }
        String unit= "";
        if(selectedProgram!=null){
            if(Axis.N.getName().equals(name)){
                //力的单位
                unit = "N";
            }
            if(Axis.TIME.getName().equals(name)){
                unit = "ms";
            }
            if(Axis.DISPLACEMENT.getName().equals(name)){
                unit = "mm";
            }
            if(Axis.TRANSFORM.getName().equals(name)){
                unit = "mm";
            }
        }else{
            if(Axis.N.getName().equals(name)){
                //力的单位
                unit = "N";
            }
            if(Axis.TIME.getName().equals(name)){
                unit = "ms";
            }
            if(Axis.DISPLACEMENT.getName().equals(name)){
                unit = "mm";
            }
            if(Axis.TRANSFORM.getName().equals(name)){
                unit = "mm";
            }
        }
        axis.setLabel(name+"("+unit+")");
        if("X".equals(axisType)){
            xAxisName = name;
        }
        if("Y".equals(axisType)){
            yAxisNmae = name;
        }
    }
    /**
     * 坐标轴右键菜单
     * @return
     */
    private ContextMenu axisContextMenu(NumberAxis axis,String axisType){
        ContextMenu contextMenu = new ContextMenu();
        List<String> axisName = Axis.listName();
        for(String name : axisName){
            MenuItem item = new MenuItem(name);
            item.setOnAction(event -> {
                if(startFlag){
                    //实验开始不允许调整坐标轴
                    AlertUtils.alertError("实验已经开始");
                    return ;
                }
                updateAxisUnit(axis,axisType,name);
            });
            contextMenu.getItems().add(item);
        }
        return contextMenu;
    }

    /**
     * 更新折线
     * @param data
     */
    public static void updateSeries(TestData data){
//        if(series1.getData().size()>20){
//            //超过20个点设置右移
//            xAxis.setLowerBound(xAxis.getLowerBound()+1);
//            xAxis.setUpperBound(xAxis.getUpperBound()+1);
//        }
        if(Axis.TIME.getName().equals(xAxisName)){
            //x轴是时间
            if(Axis.N.getName().equals(yAxisNmae)){
                //Y轴是力
                series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getLoadVal1()));
            }
            if(Axis.DISPLACEMENT.getName().equals(yAxisNmae)){
                //Y轴是位移
                series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getPosVal()));
            }
            if(Axis.TRANSFORM.getName().equals(yAxisNmae)){
                //Y轴是变形
                series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getDeformVal()));
            }
        }
        if(Axis.N.getName().equals(xAxisName)){
            //x轴是力
            if(Axis.TIME.getName().equals(yAxisNmae)){
                //Y轴是时间
                series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getTimeValue()));
            }
            if(Axis.DISPLACEMENT.getName().equals(yAxisNmae)){
                //Y轴是位移
                series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getPosVal()));
            }
            if(Axis.TRANSFORM.getName().equals(yAxisNmae)){
                //Y轴是变形
                series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getDeformVal()));
            }
        }
        if(Axis.DISPLACEMENT.getName().equals(xAxisName)){
            //X轴是位移
            if(Axis.TIME.getName().equals(yAxisNmae)){
                //Y轴是时间
                series1.getData().add(new XYChart.Data(data.getPosVal(),data.getTimeValue()));
            }
            if(Axis.N.getName().equals(yAxisNmae)){
                //Y轴是力
                series1.getData().add(new XYChart.Data(data.getPosVal(),data.getLoadVal1()));
            }
            if(Axis.TRANSFORM.getName().equals(yAxisNmae)){
                //Y轴是变形
                series1.getData().add(new XYChart.Data(data.getPosVal(),data.getDeformVal()));
            }
        }
        if(Axis.TRANSFORM.getName().equals(xAxisName)){
            //X轴是变形
            if(Axis.TIME.getName().equals(yAxisNmae)){
                //Y轴是时间
                series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getTimeValue()));
            }
            if(Axis.N.getName().equals(yAxisNmae)){
                //Y轴是力
                series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getLoadVal1()));
            }
            if(Axis.DISPLACEMENT.getName().equals(yAxisNmae)){
                //Y轴是位移
                series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getPosVal()));
            }
        }
    }

    /**
     * 创建char1
     */
    private LineChart createChart(String axisX,String axisY) {
//        NumberAxis xAxis = new NumberAxis();
//        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(axisX);
        xAxis.setAutoRanging(true);
        yAxis.setLabel(axisY);
        yAxis.setAutoRanging(true);

        ContextMenu xMenu = axisContextMenu(xAxis,"X");
        ContextMenu yMenu = axisContextMenu(yAxis,"Y");
        //x轴右键菜单
        xAxis.setOnContextMenuRequested(event -> {
            xMenu.show(xAxis,event.getScreenX(),event.getScreenY());
        });
        //y轴右键菜单
        yAxis.setOnContextMenuRequested(event -> {
            yMenu.show(yAxis,event.getScreenX(),event.getScreenY());
        });
        LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);
        //设置线名称
//        lineChart.setTitle("Stock Monitoring, 2010");
        lineChart.setTitle(null);
        lineChart.setCreateSymbols(true);
        lineChart.setLegendVisible(true);
        //设置图表名称
//        series1.setName("Portfolio 1");
//        ObservableList<Data<Number, Number>> list = FXCollections.observableArrayList(update);
        if(selectedProgram!=null){
            int pointCount = 20;
            int gap = 0; //间隔多少取点，商
            if(dataList!=null){
                gap =  dataList.size()/pointCount;
            }

            //主图
            if(dataList!=null && dataList.size()>0){
                TestData data = dataList.get(Constants.INT_ZERO);
                if(Axis.TIME.getName().equals(axisX)){
                    //x轴是时间
                    if(Axis.N.getName().equals(axisY)){
                        //Y轴是力
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getLoadVal1()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getLoadVal1()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getLoadVal1()));
                    }
                    if(Axis.DISPLACEMENT.getName().equals(axisY)){
                        //Y轴是位移
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getPosVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getPosVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getPosVal()));
                    }
                    if(Axis.TRANSFORM.getName().equals(axisY)){
                        //Y轴是变形
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getDeformVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getDeformVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getTimeValue(),data.getDeformVal()));
                    }
                }
                if(Axis.N.getName().equals(axisX)){
                    //x轴是力
                    if(Axis.TIME.getName().equals(axisY)){
                        //Y轴是时间
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getTimeValue()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getTimeValue()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getTimeValue()));
                    }
                    if(Axis.DISPLACEMENT.getName().equals(axisY)){
                        //Y轴是位移
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getPosVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getPosVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getPosVal()));
                    }
                    if(Axis.TRANSFORM.getName().equals(axisY)){
                        //Y轴是变形
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getDeformVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getDeformVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getLoadVal1(),data.getDeformVal()));
                    }
                }
                if(Axis.DISPLACEMENT.getName().equals(axisX)){
                    //X轴是位移
                    if(Axis.TIME.getName().equals(axisY)){
                        //Y轴是时间
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getTimeValue()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getPosVal(),data.getTimeValue()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getTimeValue()));
                    }
                    if(Axis.N.getName().equals(axisY)){
                        //Y轴是力
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getLoadVal1()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getPosVal(),data.getLoadVal1()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getLoadVal1()));
                    }
                    if(Axis.TRANSFORM.getName().equals(axisY)){
                        //Y轴是变形
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getDeformVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getPosVal(),data.getDeformVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getPosVal(),data.getDeformVal()));
                    }
                }
                if(Axis.TRANSFORM.getName().equals(axisX)){
                    //X轴是变形
                    if(Axis.TIME.getName().equals(axisY)){
                        //Y轴是时间
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getTimeValue()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getTimeValue()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getTimeValue()));
                    }
                    if(Axis.N.getName().equals(axisY)){
                        //Y轴是力
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getLoadVal1()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getLoadVal1()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getLoadVal1()));
                    }
                    if(Axis.DISPLACEMENT.getName().equals(axisY)){
                        //Y轴是位移
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getPosVal()));
                        for(int i=gap;i<dataList.size();i=i+gap){
                            data = dataList.get(i);
                            series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getPosVal()));
                        }
                        data = dataList.get(dataList.size()-1);
                        series1.getData().add(new XYChart.Data(data.getDeformVal(),data.getPosVal()));
                    }
                }
            }

        }
        lineChart.setCreateSymbols(false);
        return lineChart;
    }

    private void intiComp(){
        textFileName.setPrefSize(200,20);
        treeView.setPrefSize(250,500);
        //实验操作按钮置灰
        initBt();
    }
    //实验操作按钮置灰
    public static void initBt(){
        startBt.setDisable(true);
        stopBt.setDisable(true);
        upBt.setDisable(true);
        downBt.setDisable(true);
        resetBt.setDisable(true);
        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
    }

    private void registEvent(){
        //清零按钮

        //速度滑动条
        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
            speedTextField.setText(String.format("%.0f", newVal));
        });
        //速度设置输入框
        speedTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                speedSlider.setValue(Double.parseDouble(newValue));
            }
        });

        choiceBoxProgram.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    //给保存文件名赋值
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                    textFileName.setText(sdf.format(new Date()));
                    //选中的方案
                    selectedProgram = programList.get(newValue.intValue());
                    //刷新数（方案简略信息）
                    refreshTree(selectedProgram);
                    //用户参数
                    try {
                        userParamList = programService.listUserParam(selectedProgram.getID());
                        refreshTable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //设置曲线坐标
                    updateAxisUnit(xAxis,"X",selectedProgram.getOneX());
                    updateAxisUnit(yAxis,"Y",selectedProgram.getOneY());
                }
            }
        });
        //查询页面choiceBox
        programSearchChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                searchSelectProgram = programList.get(newValue.intValue());
                //根据实验方案查询实验
            }
        });
        //查询实验
        searchBtn.setOnAction(event -> {
            if(searchSelectProgram==null){
                return;
            }
            List<Test> list = null;
            try {
                list = testService.listByStandard(searchSelectProgram.getStandard());
                testTableView.getItems().clear();
                testTableView.getItems().addAll(list);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        //出报告
        reportBtn.setOnAction(event -> {

        });
        /**
         * 参数选择改变
         */
        tableView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue()>-1){
                    selectedUserParamIndex = newValue.intValue();
                    selectedUserParam = allData.get(newValue.intValue());
                }
            }
        });
        /**
         * 添加参数
         */
        btnAddTest.setOnAction(event -> {
            if(selectedProgram==null){
                AlertUtils.alertError("请选择实验方案");
                return;
            }
            if(allData==null || allData.size()==0){
                return;
            }
            HashMap<String,String> lastData = allData.get(allData.size()-1);
            if(lastData.containsValue("")){
                AlertUtils.alertError("请先填写参数");
                return;
            }
            editDataRow = new HashMap<>();
            editDataRow.put("序号",allData.size()+1+"");
            editDataRow.put(Constants.TEST_STATUS,Constants.TEST_STATUS_NOSTART);
            for (int i=0;i<userParamList.size();i++){
                ProgramUserParam p = userParamList.get(i);
                editDataRow.put(p.getName(),"");
            }
            allData.add(editDataRow);
            tableView.getItems().clear();
            tableView.getItems().addAll(allData);
        });

        btnDelTest.setOnAction(event -> {
            if(selectedUserParam==null){
                AlertUtils.alertError("未选中参数");
                return;
            }
            allData.remove(selectedUserParam);
            tableView.getItems().remove(selectedUserParam);
            selectedUserParam = null;
        });

        //右侧开始按钮
        startBt.setOnAction(event -> {
            if(selectedProgram==null){
                AlertUtils.alertError("请选择实验方案");
                return;
            }
            if(UIOnline.mSerialport==null){
                AlertUtils.alertError("未联机");
            }
            if(selectedUserParam==null){
                AlertUtils.alertError("请实验条目");
                return;
            }
            if(!(Constants.TEST_STATUS_NOSTART.equals(selectedUserParam.get(Constants.TEST_STATUS)))){
                AlertUtils.alertError("实验状态错误");
                return;
            }

            //数据列表清空,初始化
            dataList.clear();
            //所有数据清零
            clearAllData();
            //创建mdb文件
            String fileName = textFileName.getText();
            if(StringUtils.isNotBlank(fileName)){
                if(!DBUtils.isExit(fileName)){
                    try {
                        //创建数据库
                        Database dataBase= DBUtils.createDBFile(fileName);
                        //创建表
                        DBUtils.createTableTestData(dataBase);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Long maxNum = null;
                try {
                    maxNum = testService.maxNum();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startTest = new Test();
                startTest.setTestNum(maxNum+1);
                startTest.setLoadUnit(selectedProgram.getUnitLoad());
                startTest.setPressUnit(selectedProgram.getUnitN());
                startTest.setTransformUnit(selectedProgram.getUnitTransform());
                startTest.setProgramName(selectedProgram.getName());
                startTest.setSaveFile(fileName);
                startTest.setShape(selectedProgram.getShapeName());
                startTest.setSpeed(selectedProgram.isControl()?0:selectedProgram.getGeneralSpeed());
                startTest.setTransformSensor(selectedProgram.getTransformSensor());
                startTest.setTestTime(new java.sql.Date(new Date().getTime()));
                //实验开始标志置为true
                startFlag = true;
                //设置按钮状态
                startBt.setDisable(true);
                stopBt.setDisable(false);
                Main.startBt.setDisable(true);
                Main.upBt.setDisable(true);
                Main.downBt.setDisable(true);
                Main.resetBt.setDisable(true);
                Main.button1.setDisable(true);
                Main.button2.setDisable(true);
                Main.button3.setDisable(true);
                //发送开始命令
                if(Constants.KQL.equals(selectedProgram.getName())){
                    Float data1 = 1000f;
                    if(Constants.INT_TWO == selectedProgram.getDirect()){
                        data1 = -1000f;
                    }
                    if(selectedProgram.isClearN()){
                        //实验开始清空力
                        String speed = speedTextField.getText().trim();
                        byte[] command = CommandUtils.commandClearLoad(speed);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                        //峰值清零
                        Main.topN = 0F;
                    }
                    if(selectedProgram.isClearDisp()){
                        //实验开始清空位移
                        String speed = speedTextField.getText().trim();
                        byte[] command = CommandUtils.commandClearPos(speed);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                    }
                    if(selectedProgram.isClearTransform()){
                        //实验开始清空变形
                        String speed = speedTextField.getText().trim();
                        byte[] command = CommandUtils.commandClearTransform(speed);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                        SerialPortManager.sendToPort(UIOnline.mSerialport,command);
                    }

                    SerialPortManager.sendToPort(UIOnline.mSerialport,CommandUtils.commandStart(selectedProgram.getDirect(),selectedProgram.getNum(),data1,0f,selectedProgram.getGeneralSpeed()));
                    SerialPortManager.sendToPort(UIOnline.mSerialport,CommandUtils.commandStart(selectedProgram.getDirect(),selectedProgram.getNum(),data1,0f,selectedProgram.getGeneralSpeed()));
                    testName = Constants.KQL;
                }
                //设置实验状态进行中
                allData.get(selectedUserParamIndex).put(Constants.TEST_STATUS,Constants.TEST_STATUS_ING);
                tableView.refresh();
            }

        });
        //右侧停止按钮
        stopBt.setOnAction(event -> {
            byte[] command = CommandUtils.commandStop();
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            stopTest();
        });
        //上升
        upBt.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandUP(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
        });
        //下降
        downBt.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandDown(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
        });
        //复位
        resetBt.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandReset(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
        });
        //力清零
        button1.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandClearLoad(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            //峰值清零
            Main.topN = 0F;
        });
        //位移清零
        button2.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandClearPos(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
        });
        //变形清零
        button3.setOnAction(event -> {
            String speed = speedTextField.getText().trim();
            byte[] command = CommandUtils.commandClearTransform(speed);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
            SerialPortManager.sendToPort(UIOnline.mSerialport,command);
        });
    }


    public static void stopTest(){
        //实验开始标志置为false
        startFlag = false;
        //按钮状态
        startBt.setDisable(false);
        stopBt.setDisable(true);
        Main.startBt.setDisable(false);
        Main.upBt.setDisable(false);
        Main.downBt.setDisable(false);
        Main.resetBt.setDisable(false);
        Main.button1.setDisable(false);
        Main.button2.setDisable(false);
        Main.button3.setDisable(false);

        startTest = null;
        startTime = null;
    }
    /**
     * 实验停止
     */
    public static void stopTestAndSave(){
        //发送实验停止

        //按钮状态
        startBt.setDisable(false);
        stopBt.setDisable(true);
        Main.startBt.setDisable(false);
        Main.upBt.setDisable(false);
        Main.downBt.setDisable(false);
        Main.resetBt.setDisable(false);
        Main.button1.setDisable(false);
        Main.button2.setDisable(false);
        Main.button3.setDisable(false);
        startTest.setRunTime(Main.startTime);
        try {
            //保存实验
            testService.insert(startTest);
            //实验结束保存数据
            DBFileHelper.getInstance(startTest.getSaveFile());
            testService.batchSaveTestData(dataList);
            //保存实验参数
            testService.batchSaveTestParam(startTest.getTestNum(),selectedUserParam);
            //状态显示更新
            allData.get(selectedUserParamIndex).put(Constants.TEST_STATUS,Constants.TEST_STATUS_END);
            //更改状态后刷新表格
            tableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        startTest = null;
        startTime = null;
        //top力值
//        topN = 0F;
        //实验开始标志置为false
        startFlag = false;
    }

    /**
     * 刷新简略信息树
     * @param selectedProgram
     */
    private void refreshTree(Program selectedProgram) {
        treeView.setRoot(treeRoot);
        treeRoot.getChildren().clear();
        treeRoot.setExpanded(true);
        treeRoot.getChildren().add(new TreeItem<>("引用标准："+selectedProgram.getName()));
        treeRoot.getChildren().add(new TreeItem<>("试验方向："+(selectedProgram.getDirect()==0?"拉向":"压向")));
        treeRoot.getChildren().add(new TreeItem<>("试样材料："+selectedProgram.getShapeName()));
        treeRoot.getChildren().add(new TreeItem<>("变形计算："+selectedProgram.getTransformSensor()));
        treeRoot.getChildren().add(new TreeItem<>("变形切换："+(selectedProgram.isTransformChange()?"是":"否")));
        if(selectedProgram.isAutoBreakage()){
            TreeItem<String> itemBreak = new TreeItem<>("判断断裂方式："+(selectedProgram.isAutoBreakage()?"是":"否"));
            treeRoot.getChildren().add(itemBreak);
            if(selectedProgram.isAutoBreakage()){
                if(selectedProgram.getGtForce()!=null && selectedProgram.getGtForce()!=0F){
                    itemBreak.getChildren().add(new TreeItem<>("当力值大于"+selectedProgram.getGtForce()+"N开始判断断裂"));
                }
                if(selectedProgram.getLtRate()!=null && selectedProgram.getLtRate()!=0F){
                    itemBreak.getChildren().add(new TreeItem<>("后前力值之比下于"+selectedProgram.getLtRate()+"%为断裂"));
                }
                if(selectedProgram.getLtMearure()!=null && selectedProgram.getLtMearure()!=0F){
                    itemBreak.getChildren().add(new TreeItem<>("力值小于最大力的"+selectedProgram.getLtMearure()+"%为断裂"));
                }
                itemBreak.setExpanded(true);
            }
        }
        treeRoot.getChildren().add(new TreeItem<>("程控方式："+(selectedProgram.isControl()?"程控":"位移"+selectedProgram.getReturnSpeed()+"mm/min")));
    }

    /**
     * 刷新用户参数
     */
    private void refreshTable(){
        allData.clear();
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getColumns().clear();
        if(userParamList!=null && userParamList.size()>0){
            TableColumn<HashMap<String,String>,String> numColumn = new TableColumn("序号");
            numColumn.setCellValueFactory(new MapValueFactory("序号"));
            numColumn.setSortable(false);
            TableColumn<HashMap<String,String>,String> statusColumn = new TableColumn(Constants.TEST_STATUS);
            statusColumn.setCellValueFactory(new MapValueFactory(Constants.TEST_STATUS));
            statusColumn.setSortable(false);
            tableView.getColumns().add(numColumn);
            tableView.getColumns().add(statusColumn);
            editDataRow = new HashMap<>();
            editDataRow.put("序号","1");
            editDataRow.put(Constants.TEST_STATUS,Constants.TEST_STATUS_NOSTART);
            for (int i=0;i<userParamList.size();i++){
                ProgramUserParam p = userParamList.get(i);
                TableColumn tableColumn = new TableColumn(p.getName()+"\n"+"("+p.getUnit()+")");
                tableColumn.setCellValueFactory(new MapValueFactory<>(p.getName()));
                tableColumn.setCellFactory(TextFieldTableCell.<HashMap<String,String>>forTableColumn());
                tableColumn.setSortable(false);
                tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<HashMap<String,String>,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<HashMap<String, String>,String> event) {
                        String value = event.getNewValue();
                        editDataRow.put(p.getName(),value);
                    }
                });
                tableColumn.setPrefWidth(150);
                tableView.getColumns().add(tableColumn);
                editDataRow.put(p.getName(),"");
            }
            allData.add(editDataRow);
            tableView.getItems().clear();
            tableView.getItems().addAll(allData);
        }
    }



    /**
     * 设置语言文字
     * @param fileName
     * @throws IOException
     */
    private void setLanguage(String fileName) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/language/"+fileName+".properties");
        Constants.language = PropertiesUtils.getProperties(inputStream);
    }


    /**
     * 所有数据归零，包括图表数据清零
     */
    private void clearAllData(){
        topN = 0F;
        startTime = 0L;
        series1.getData().clear();
        lableNum4.setText("00000");
        labelTop.setText("00000");
    }

}


