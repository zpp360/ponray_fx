package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.utils.AccessHelper;
import com.ponray.utils.FontUtil;
import com.ponray.utils.PropertiesUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends Application {

    private static Stage stage = null;


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

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab(Constants.language.getProperty("user_param"));
        Tab tab2 = new Tab(Constants.language.getProperty("single_picture"));
        Tab tab3 = new Tab(Constants.language.getProperty("more_picture"));
        Tab tab4 = new Tab(Constants.language.getProperty("search"));

        //力 显示框布局
        Label lableNum1 = new Label("0.00000");
        lableNum1.setMinSize(165,55);
        lableNum1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum1.setAlignment(Pos.CENTER);
        lableNum1.setStyle("-fx-background-color: #002060;");
        lableNum1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum1.setTextFill(Color.web("#fff"));


        //力峰值
        Label labelTop = new Label("0.00");
        labelTop.setMinSize(65,55);
        lableNum1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum1.setAlignment(Pos.CENTER);
        lableNum1.setStyle("-fx-background-color: #002060;");
        lableNum1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0,0,1,1),Insets.EMPTY)));
        lableNum1.setTextFill(Color.web("#fff"));



        Label labelN1 = new Label("N");
        labelN1.setMinSize(68,26);
        labelN1.setStyle("-fx-background-color: #002060;");
        labelN1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN1.setTextFill(Color.web("#fff"));
        labelN1.setAlignment(Pos.CENTER);
        labelN1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName1 = new Label(Constants.language.getProperty("n"));
        labelName1.setMinSize(68,23);
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

        Button button1 = new Button(Constants.language.getProperty("clear"));
        button1.setMinSize(40,53);

        GridPane topGrid1 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid1.add(lableNum1,0,0,1,2);
        topGrid1.add(vBox1,1,0);
        topGrid1.add(button1,2,0,1,2);
        topGrid1.setPadding(new Insets(5));
        topGrid1.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));
        //lableNum1.prefWidthProperty().bind(topGrid1.widthProperty());


        //位移显示框布局
        Label lableNum2 = new Label("0.00000");
        lableNum2.setMinSize(165,55);
        lableNum2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum2.setAlignment(Pos.CENTER);
        lableNum2.setStyle("-fx-background-color: #002060;");
        lableNum2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum2.setTextFill(Color.web("#fff"));


        Label labelN2 = new Label("mm");
        labelN2.setMinSize(68,26);
        labelN2.setStyle("-fx-background-color: #002060;");
        labelN2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN2.setTextFill(Color.web("#fff"));
        labelN2.setAlignment(Pos.CENTER);
        labelN2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName2 = new Label(Constants.language.getProperty("displacement"));
        labelName2.setMinSize(68,23);
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

        Button button2 = new Button(Constants.language.getProperty("clear"));
        button2.setMinSize(40,53);

        GridPane topGrid2 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid2.add(lableNum2,0,0,1,2);
        topGrid2.add(vBox2,1,0);
        topGrid2.add(button2,2,0,1,2);
        topGrid2.setPadding(new Insets(5));
        topGrid2.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        //变形显示框布局
        Label lableNum3 = new Label("0.00000");
        lableNum3.setMinSize(165,55);
        lableNum3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum3.setAlignment(Pos.CENTER);
        lableNum3.setStyle("-fx-background-color: #002060;");
        lableNum3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum3.setTextFill(Color.web("#fff"));


        Label labelN3 = new Label("mm");
        labelN3.setMinSize(68,26);
        labelN3.setStyle("-fx-background-color: #002060;");
        labelN3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN3.setTextFill(Color.web("#fff"));
        labelN3.setAlignment(Pos.CENTER);
        labelN3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName3 = new Label(Constants.language.getProperty("transformation"));
        labelName3.setMinSize(68,23);
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

        Button button3 = new Button(Constants.language.getProperty("clear"));
        button3.setMinSize(40,53);

        GridPane topGrid3 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid3.add(lableNum3,0,0,1,2);
        topGrid3.add(vBox3,1,0);
        topGrid3.add(button3,2,0,1,2);
        topGrid3.setPadding(new Insets(5));
        topGrid3.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        //时间显示框布局
        Label lableNum4 = new Label("0.00000");
        lableNum4.setMinSize(165,55);
        lableNum4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 40));
        lableNum4.setAlignment(Pos.CENTER);
        lableNum4.setStyle("-fx-background-color: #002060;");
        lableNum4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));
        lableNum4.setTextFill(Color.web("#fff"));


        Label labelN4 = new Label("s");
        labelN4.setMinSize(68,26);
        labelN4.setStyle("-fx-background-color: #002060;");
        labelN4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN4.setTextFill(Color.web("#fff"));
        labelN4.setAlignment(Pos.CENTER);
        labelN4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        Label labelName4 = new Label(Constants.language.getProperty("time"));
        labelName4.setMinSize(68,23);
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

        Button button4 = new Button(Constants.language.getProperty("clear"));
        button4.setMinSize(40,53);

        GridPane topGrid4 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid4.add(lableNum4,0,0,1,2);
        topGrid4.add(vBox4,1,0);
        topGrid4.add(button4,2,0,1,2);
        topGrid4.setPadding(new Insets(5));
        topGrid4.setBorder(new Border(new BorderStroke(Color.rgb(213,223,229), BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT,new Insets(10))));

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(topGrid1,topGrid2,topGrid3,topGrid4);



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

        Button upBt = new Button(Constants.language.getProperty("up"));
        upBt.setMinSize(80,40);
        upBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        Button dropBt = new Button(Constants.language.getProperty("down"));
        dropBt.setMinSize(80,40);
        dropBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));

        HBox lineOne = new HBox();
        lineOne.getChildren().addAll(upBt,dropBt);
        lineOne.setSpacing(50);

        Button startBt = new Button(Constants.language.getProperty("start"));
        startBt.setMinSize(80,40);
        startBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        Button stopBt = new Button(Constants.language.getProperty("end"));
        stopBt.setMinSize(80,40);
        stopBt.setFont(Font.font(FontUtil.FANGSONG, FontWeight.LIGHT, 20));
        HBox lineTwo = new HBox();
        lineTwo.getChildren().addAll(startBt,stopBt);
        lineTwo.setSpacing(50);

        VBox opBtvBox = new VBox();
        opBtvBox.getChildren().addAll(lineOne,lineTwo);
        opBtvBox.setSpacing(30);



        GridPane rightGrid = new GridPane();
        rightGrid.setPadding(new Insets(5));
        rightGrid.setHgap(5);
        rightGrid.setVgap(5);
        rightGrid.setPrefSize(250,500);
        rightGrid.add(redLabel,0,0,2,1);
        rightGrid.add(statusNameLabel,0,1);
        rightGrid.add(statusLable,1,1);
        rightGrid.add(nullLable,0,2,2,1);
        rightGrid.add(opBtvBox,0,3,2,1);

        //tabPane
        tabPane.setPrefSize(1000,600);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE,TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE,TabPane.USE_PREF_SIZE);
        tabPane.getTabs().addAll(tab1,tab2,tab3,tab4);
        tabPane.setStyle("-fx-tab-min-width: 225px;-fx-tab-max-width: 300px;-fx-tab-min-height: 35px;");
        tabPane.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,Insets.EMPTY)));

        tab1.setClosable(false);
        tab1.setStyle("-fx-font-size:20px;");
        tab1.setContent(createTab1());
        tab2.setClosable(false);
        tab2.setStyle("-fx-font-size:20px;");
        tab3.setClosable(false);
        tab3.setStyle("-fx-font-size:20px;");
        tab4.setClosable(false);
        tab4.setStyle("-fx-font-size:20px;");


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


        Scene scene = new Scene(root);
        stage.setTitle("拉力试验工具软件");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/soft_name64.png")));
        stage.setScene(scene);
        //控制窗口是否可以缩放
        stage.setResizable(true);
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

        Connection connection = AccessHelper.getInstance().getConnection();
        Statement statement = AccessHelper.getInstance().getStatement(connection);
        String sql = "select id,name,file_name,selected from t_language";
        ResultSet rs = AccessHelper.getInstance().getResultSet(statement,sql);
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
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        setMenu.getItems().addAll(paramItem);

        Menu communicationMenu = new Menu("通讯");
        MenuItem onlineItem = new MenuItem("联机");
        onlineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/on_line.ico"))));
        MenuItem offlineItem = new MenuItem("脱机");
        offlineItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/off_line.ico"))));
        communicationMenu.getItems().addAll(onlineItem,offlineItem);

        Menu experimentMenu = new Menu("实验管理");
        MenuItem createExp = new MenuItem("编辑实验方案");
        experimentMenu.getItems().add(createExp);

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
        ChoiceBox cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList(
                "New Document", "Open ",
                new Separator(), "Save", "Save as")
        );
        cb.setMinWidth(150);

        Label label2 = new Label("数据保存名称:");
        label2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 18));
        TextField textField = new TextField(System.currentTimeMillis()+"");

        hBox1.getChildren().addAll(label1,cb,label2,textField);
        hBox1.setPadding(new Insets(20));
        hBox1.setSpacing(50);

        Separator separator = new Separator();
        separator.setMaxHeight(1200);



        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(separator);
        vBox.setSpacing(20);

        return vBox;
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



}


