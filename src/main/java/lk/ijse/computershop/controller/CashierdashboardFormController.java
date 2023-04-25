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

public class CashierdashboardFormController implements Initializable {

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
        lblSubtitle.setText("Welcome!");
    }

    @FXML
    private void managecustomersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managecustomer_form");
        lblSubtitle.setText("Manage Customers");
    }

    @FXML
    private void manageordersOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "manageorders_form");
        lblSubtitle.setText("Manage Orders");
    }

    @FXML
    private void manageitemsOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "Viewitem_form");
        lblSubtitle.setText("View  Store");
    }

    @FXML
    private void deliveryOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managedelivery_form");
        lblSubtitle.setText("Delivery");
    }

    @FXML
    private void repairOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managerepair_form");
        lblSubtitle.setText("Repair");
    }

    @FXML
    private void custombuildOnAction(ActionEvent event) throws IOException {
        UILoader.loadUiDashBoard(manageFormsRoot, "managecustombuild_form");
        lblSubtitle.setText("Custom Build");
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
