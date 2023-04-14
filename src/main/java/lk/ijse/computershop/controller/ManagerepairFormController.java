package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.EmployeeModel;
import lk.ijse.computershop.model.RepairModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerepairFormController implements Initializable {

    @FXML
    private TextField txtRepairCode;
    @FXML
    private TextField txtRepairDate;
    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<String> cmbEmployeeId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private TextField txtDetails;
    @FXML
    private TextField txtAcceptingDate;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView tblRepair;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colCustomerId;
    @FXML
    private TableColumn colEmployeeId;
    @FXML
    private TableColumn colDetails;
    @FXML
    private TableColumn colDatetime;
    @FXML
    private TableColumn colAccentuated;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRepairDate();
        generateNextRepairCode();
        loadCustomerIds();
        loadEmployeeIds();
    }

    private void setRepairDate() {
        txtRepairDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextRepairCode() {
        try {
            String code = RepairModel.getNextRepairCode();
            txtRepairCode.setText(code);
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
        String customerId = cmbCustomerId.getValue();
        cmbCustomerId.setDisable(true);

        try {
            Customer customer = CustomerModel.searchById(customerId);
            txtCustomerName.setText(customer.getName());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbEmployeeIdOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();
        cmbEmployeeId.setDisable(true);

        try {
            Employee employee = EmployeeModel.searchById(employeeId);
            txtEmployeeName.setText(employee.getName());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {

    }

    @FXML
    private void repairOnAction(ActionEvent event) {
        
    }
}
