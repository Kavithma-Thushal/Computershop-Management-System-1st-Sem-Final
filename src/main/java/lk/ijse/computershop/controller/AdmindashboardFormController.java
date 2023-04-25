package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computershop.util.DateAndTime;
import lk.ijse.computershop.util.RandomImages;
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
    private AnchorPane random;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblSubtitle;

    private RandomImages randomImages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateAndTime.loadDateAndTime(lblDate, lblTime);
        randomImages = new RandomImages(random);
    }

    @FXML
    private void manageemployeesOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "manageemployee_form");
        lblSubtitle.setText("Manage Employees");
    }

    @FXML
    private void managesuppliersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managesuppliers_form");
        lblSubtitle.setText("Manage Suppliers");
    }

    @FXML
    private void manageitemsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "manageitem_form");
        lblSubtitle.setText("Manage Store");
    }

    @FXML
    private void manageordersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "manageorders_form");
        lblSubtitle.setText("Manage Orders");
    }

    @FXML
    private void managesalaryOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managesalary_form");
        lblSubtitle.setText("Manage Salary");
    }

    @FXML
    private void reportsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managereports_form");
        lblSubtitle.setText("Reports");
    }

    @FXML
    private void backOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.BtnLogOut(root, "login_form");
    }

    @FXML
    private void minimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logoutOnAction(MouseEvent event) {
        System.exit(0);
    }
}
