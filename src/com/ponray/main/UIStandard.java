package com.ponray.main;

import com.ponray.constans.Constants;
import com.ponray.entity.Standard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UIStandard {

    public void display() throws SQLException, ClassNotFoundException {
        Stage window = new Stage();
        window.setTitle(Constants.language.getProperty("menu_hardware_param"));
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
        VBox main = new VBox();
        main.setPadding(new Insets(10));
        ObservableList<Standard> data = FXCollections.observableArrayList();
        TableView tableView = new TableView();
        tableView.setItems(data);
        TableColumn numColumn = new TableColumn("序号");
        TableColumn codeColumn = new TableColumn("标准代码");
        TableColumn nameColumn = new TableColumn("标准名称");
        TableColumn remarkColumn = new TableColumn("备注");
        tableView.getColumns().addAll(numColumn,codeColumn,nameColumn,remarkColumn);
        main.getChildren().add(tableView);

        return main;
    }
}
