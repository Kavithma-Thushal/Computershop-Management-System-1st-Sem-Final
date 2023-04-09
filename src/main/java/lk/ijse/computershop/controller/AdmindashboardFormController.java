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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdmindashboardFormController implements Initializable {

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
    private void manageemployeesOnAction(ActionEvent event) throws IOException {
        setUI("manageemployees_form");
    }

    @FXML
    private void managesuppliersOnAction(ActionEvent event) throws IOException {
        setUI("managesuppliers_form");
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
    private void managesalaryOnAction(ActionEvent event) throws IOException {
        setUI("managesalary_form");
    }

    @FXML
    private void reportsOnAction(ActionEvent event) throws IOException {
        setUI("managereports_form");
    }

    @FXML
    private void logoutOnAction(ActionEvent event) {
        System.exit(0);
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
