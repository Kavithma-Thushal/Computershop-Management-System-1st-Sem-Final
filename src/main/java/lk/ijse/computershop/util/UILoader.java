package lk.ijse.computershop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class UILoader {

    public static void CloseStage(AnchorPane anchorPane) throws IOException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public static void loadUiHalfDashBoard(AnchorPane anchorPane, String location) throws IOException {
        anchorPane.getChildren().add(FXMLLoader.load(UILoader.class.getResource("/view/" + location + ".fxml")));
    }

    public static void aboutOnAction(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("/view/" + location + ".fxml"))));
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        stage.setX(390);
        stage.setY(252);
    }

    public static void LoginOnAction(AnchorPane anchorPane, String location) throws IOException, SQLException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("/view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public static void loadUiDashBoard(AnchorPane anchorPane, String location) throws IOException {
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(UILoader.class.getResource("/view/" + location + ".fxml")));
    }

    public static void BtnLogOut(AnchorPane anchorPane, String location) throws IOException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(UILoader.class.getResource("/view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}