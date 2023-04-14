package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.DeliveryModel;
import lk.ijse.computershop.model.EmployeeModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagedeliveryFormController implements Initializable {

    @FXML
    private TextField txtDeliveryCode;
    @FXML
    private TextField txtDeliveryDate;
    @FXML
    private ComboBox cmbCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox cmbEmployeeId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private ComboBox cmbOrderId;
    @FXML
    private TextField txtOrderDescription;
    @FXML
    private TextField txtLocation;
    @FXML
    private TableView tblDelivery;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colCustomerId;
    @FXML
    private TableColumn colEmployeeId;
    @FXML
    private TableColumn colOrderId;
    @FXML
    private TableColumn colLocation;
    @FXML
    private TableColumn colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDeliveryDate();
        generateNextDeliveryCode();
        loadCustomerIds();
        loadEmployeeIds();
    }

    private void setDeliveryDate() {
        txtDeliveryDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextDeliveryCode() {
        try {
            String code = DeliveryModel.getNextDeliveryCode();
            txtDeliveryCode.setText(code);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> customerId = CustomerModel.loadIds();

            for (String id : customerId) {
                observableList.add(id);
            }
            cmbCustomerId.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> employeeId = EmployeeModel.loadIds();

            for (String id : employeeId) {
                observableList.add(id);
            }
            cmbEmployeeId.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbCustomerIdOnAction(ActionEvent event) {

    }

    @FXML
    private void cmbEmployeeIdOnAction(ActionEvent event) {

    }

    @FXML
    private void cmbOrderIdOnAction(ActionEvent event) {

    }

    @FXML
    private void deliverOnAction(ActionEvent event) {

    }
}
