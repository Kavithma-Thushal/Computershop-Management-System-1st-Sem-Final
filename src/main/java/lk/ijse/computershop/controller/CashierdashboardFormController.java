package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computershop.util.DateAndTime;
import lk.ijse.computershop.util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierdashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateAndTime.loadDateAndTime(lblDate, lblTime);
    }

    @FXML
    private void managecustomersOnAction(ActionEvent event) throws IOException {
        setUI("managecustomers_form");
    }

    @FXML
    private void manageitemsOnAction(ActionEvent event) throws IOException {
        setUI("manageitems_form");
    }

    @FXML
    private void manageordersOnAction(ActionEvent event) throws IOException {
        setUI("manageorders_form");
    }

    @FXML
    private void deliveryOnAction(ActionEvent event) throws IOException {
        setUI("managedelivery_form");
    }

    @FXML
    private void custombuildOnAction(ActionEvent event) throws IOException {
        setUI("managecustombuild_form");
    }

    @FXML
    private void repairOnAction(ActionEvent event) throws IOException {
        setUI("managerepair_form");
    }

    private void setUI(String URL) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/" + URL + ".fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    private void backOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.BtnLogOut(root, "login_form");
    }

    @FXML
    private void logoutOnAction(MouseEvent event) {
        System.exit(0);
    }
}
