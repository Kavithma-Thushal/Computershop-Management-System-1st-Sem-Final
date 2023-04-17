package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.computershop.util.DateAndTime;
import lk.ijse.computershop.util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierdashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane manageFormsRoot;
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
        UILoader.loadUiDashBoard(manageFormsRoot, "managecustomers_form");
    }

    @FXML
    private void manageitemsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "Viewitems_form");
    }

    @FXML
    private void manageordersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "manageorders_form");
    }

    @FXML
    private void deliveryOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managedelivery_form");
    }

    @FXML
    private void custombuildOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managecustombuild_form");
    }

    @FXML
    private void repairOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managerepair_form");
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
