package com.ponray.main;

import com.ponray.utils.FontUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //力 显示框布局
        Label lableNum1 = new Label("0.00");
        lableNum1.setMinSize(165,55);
        lableNum1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 50));
        lableNum1.setAlignment(Pos.CENTER);
        lableNum1.setStyle("-fx-background-color: #002060;");
        lableNum1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));
        lableNum1.setTextFill(Color.web("#fff"));


        Label labelN1 = new Label("N");
        labelN1.setMinSize(68,26);
        labelN1.setStyle("-fx-background-color: #002060;");
        labelN1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN1.setTextFill(Color.web("#fff"));
        labelN1.setAlignment(Pos.CENTER);
        labelN1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        Label labelName1 = new Label("力");
        labelName1.setMinSize(68,23);
        labelName1.setStyle("-fx-background-color: #5FB41B;");
        labelName1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName1.setTextFill(Color.web("#fff"));
        labelName1.setAlignment(Pos.CENTER);
        labelName1.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        VBox vBox1 = new VBox();
        //调整上下组件间距
        vBox1.setSpacing(5);
        vBox1.setPadding(new Insets(0,5,0,10));
        vBox1.getChildren().addAll(labelN1,labelName1);

        Button button1 = new Button("清零");
        button1.setMinSize(34,53);

        GridPane topGrid1 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid1.add(lableNum1,0,0,1,2);
        topGrid1.add(vBox1,1,0);
        topGrid1.add(button1,2,0,1,2);

        //位移显示框布局
        Label lableNum2 = new Label("0.000");
        lableNum2.setMinSize(165,55);
        lableNum2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 50));
        lableNum2.setAlignment(Pos.CENTER);
        lableNum2.setStyle("-fx-background-color: #002060;");
        lableNum2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));
        lableNum2.setTextFill(Color.web("#fff"));


        Label labelN2 = new Label("mm");
        labelN2.setMinSize(68,26);
        labelN2.setStyle("-fx-background-color: #002060;");
        labelN2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN2.setTextFill(Color.web("#fff"));
        labelN2.setAlignment(Pos.CENTER);
        labelN2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        Label labelName2 = new Label("位移");
        labelName2.setMinSize(68,23);
        labelName2.setStyle("-fx-background-color: #5FB41B;");
        labelName2.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName2.setTextFill(Color.web("#fff"));
        labelName2.setAlignment(Pos.CENTER);
        labelName2.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        VBox vBox2 = new VBox();
        //调整上下组件间距
        vBox2.setSpacing(5);
        vBox2.setPadding(new Insets(0,5,0,10));
        vBox2.getChildren().addAll(labelN2,labelName2);

        Button button2 = new Button("清零");
        button2.setMinSize(34,53);

        GridPane topGrid2 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid2.add(lableNum2,0,0,1,2);
        topGrid2.add(vBox2,1,0);
        topGrid2.add(button2,2,0,1,2);

        //变形显示框布局
        Label lableNum3 = new Label("0.00000");
        lableNum3.setMinSize(165,55);
        lableNum3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 50));
        lableNum3.setAlignment(Pos.CENTER);
        lableNum3.setStyle("-fx-background-color: #002060;");
        lableNum3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));
        lableNum3.setTextFill(Color.web("#fff"));


        Label labelN3 = new Label("mm");
        labelN3.setMinSize(68,26);
        labelN3.setStyle("-fx-background-color: #002060;");
        labelN3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN3.setTextFill(Color.web("#fff"));
        labelN3.setAlignment(Pos.CENTER);
        labelN3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        Label labelName3 = new Label("变形");
        labelName3.setMinSize(68,23);
        labelName3.setStyle("-fx-background-color: #5FB41B;");
        labelName3.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName3.setTextFill(Color.web("#fff"));
        labelName3.setAlignment(Pos.CENTER);
        labelName3.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        VBox vBox3 = new VBox();
        //调整上下组件间距
        vBox3.setSpacing(5);
        vBox3.setPadding(new Insets(0,5,0,10));
        vBox3.getChildren().addAll(labelN3,labelName3);

        Button button3 = new Button("清零");
        button3.setMinSize(34,53);

        GridPane topGrid3 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid3.add(lableNum3,0,0,1,2);
        topGrid3.add(vBox3,1,0);
        topGrid3.add(button3,2,0,1,2);

        //时间显示框布局
        Label lableNum4 = new Label("0.00");
        lableNum4.setMinSize(165,55);
        lableNum4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 50));
        lableNum4.setAlignment(Pos.CENTER);
        lableNum4.setStyle("-fx-background-color: #002060;");
        lableNum4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));
        lableNum4.setTextFill(Color.web("#fff"));


        Label labelN4 = new Label("s");
        labelN4.setMinSize(68,26);
        labelN4.setStyle("-fx-background-color: #002060;");
        labelN4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelN4.setTextFill(Color.web("#fff"));
        labelN4.setAlignment(Pos.CENTER);
        labelN4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        Label labelName4 = new Label("时间");
        labelName4.setMinSize(68,23);
        labelName4.setStyle("-fx-background-color: #5FB41B;");
        labelName4.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 20));
        labelName4.setTextFill(Color.web("#fff"));
        labelName4.setAlignment(Pos.CENTER);
        labelName4.setBorder(new Border(new BorderStroke(Color.rgb(160,160,160), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT,new Insets(1,0,0,1))));

        VBox vBox4 = new VBox();
        //调整上下组件间距
        vBox4.setSpacing(5);
        vBox4.setPadding(new Insets(0,5,0,10));
        vBox4.getChildren().addAll(labelN4,labelName4);

        Button button4 = new Button("清零");
        button4.setMinSize(34,53);

        GridPane topGrid4 = new GridPane();
        //是否显示表格边框
        //topGrid.setGridLinesVisible(true);
        topGrid4.add(lableNum4,0,0,1,2);
        topGrid4.add(vBox4,1,0);
        topGrid4.add(button4,2,0,1,2);

        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.getChildren().addAll(topGrid1,topGrid2,topGrid3,topGrid4);
        HBox.setHgrow(topGrid1,Priority.ALWAYS);
        HBox.setHgrow(topGrid2,Priority.ALWAYS);
        HBox.setHgrow(topGrid3,Priority.ALWAYS);
        HBox.setHgrow(topGrid4,Priority.ALWAYS);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setPadding(new Insets(10));

        Scene scene = new Scene(borderPane);
        stage.setTitle("拉力试验工具软件");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/user.jpg")));
        stage.setScene(scene);
        stage.show();
    }
}


