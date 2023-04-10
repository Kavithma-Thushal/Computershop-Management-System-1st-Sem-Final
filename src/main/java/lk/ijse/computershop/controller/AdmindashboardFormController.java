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

public class AdmindashboardFormController implements Initializable {

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
    private void manageemployeesOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"manageemployees_form");
    }

    @FXML
    private void managesuppliersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"managesuppliers_form");
    }

    @FXML
    private void managecustomersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"managecustomers_form");
    }

    @FXML
    private void manageitemsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"manageitems_form");
    }

    @FXML
    private void manageordersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"manageorders_form");
    }

    @FXML
    private void managesalaryOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"managesalary_form");
    }

    @FXML
    private void reportsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot,"managereports_form");
    }

    @FXML
    private void logoutOnAction(ActionEvent event) {
        System.exit(0);
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
