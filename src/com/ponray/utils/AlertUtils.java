package com.ponray.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * alert弹框提示工具类
 */
public class AlertUtils {
    /**
     * 提示信息
     * @param msg
     */
    public static void alertInfo(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }

    /**
     * 警告信息
     * @param msg
     */
    public static void alertWarn(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("警告");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }

    /**
     * 错误信息
     * @param msg
     */
    public static void alertError(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.titleProperty().set("错误");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }

    /**
     * 确认框
     * @param msg
     * @return
     */
    public static boolean alertConfirm(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.titleProperty().set("确认");
        alert.headerTextProperty().set(msg);
        Optional result =  alert.showAndWait();
        if(result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }


}
