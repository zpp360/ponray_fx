package com.ponray.main;

import com.ponray.utils.FontUtil;
import com.sun.scenario.effect.impl.prism.ps.PPSOneSamplerPeer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label lable1 = new Label("0.00");
        lable1.setMinSize(165,55);
        lable1.setFont(Font.font(FontUtil.FANGSONG, FontWeight.NORMAL, 50));
        lable1.setAlignment(Pos.CENTER);
        lable1.setStyle("-fx-background-color: #002060;-fx-border:1 0 0 1 solid");
        lable1.setTextFill(Color.web("#fff"));
        Label label2 = new Label("N");
        label2.setMinSize(46,23);
        label2.setStyle("-fx-background-color: #002060;");
        label2.setTextFill(Color.web("#fff"));
        label2.setAlignment(Pos.CENTER);
        label2.setPadding(new Insets(0,0,0,10));

        Label label3 = new Label("力");
        Button button1 = new Button("清零");


        GridPane topGrid = new GridPane();
        topGrid.setGridLinesVisible(true);
        topGrid.setMinWidth(980);
        topGrid.setMinHeight(680);
        topGrid.add(lable1,0,0,1,2);
        topGrid.add(label2,1,0);
        topGrid.add(label3,1,1);
        topGrid.add(button1,2,0,1,2);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topGrid);
        borderPane.setPadding(new Insets(10));

        Scene scene = new Scene(borderPane);
        stage.setTitle("拉力试验工具软件");
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }
}
