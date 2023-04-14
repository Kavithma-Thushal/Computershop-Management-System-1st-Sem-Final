package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.dto.tm.RepairTM;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.EmployeeModel;
import lk.ijse.computershop.model.RepairModel;
import lk.ijse.computershop.util.CrudUtil;

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
    private TableColumn colGettingDate;
    @FXML
    private TableColumn colAcceptingDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRepairDate();
        generateNextRepairCode();
        getAll();
        setCellValueFactory();
        loadCustomerIds();
        loadEmployeeIds();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colGettingDate.setCellValueFactory(new PropertyValueFactory<>("gettingDate"));
        colAcceptingDate.setCellValueFactory(new PropertyValueFactory<>("acceptingDate"));
    }

    private void getAll() {
        try {
            ObservableList<RepairTM> observableList = FXCollections.observableArrayList();
            List<Repair> repairList = RepairModel.getAll();

            for (Repair repair : repairList) {
                RepairTM repairTM = new RepairTM(
                        repair.getCode(),
                        repair.getCustomerId(),
                        repair.getEmployeeId(),
                        repair.getDetails(),
                        repair.getGettingDate(),
                        repair.getAcceptingDate()
                );
                observableList.add(repairTM);
            }
            tblRepair.setItems(observableList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    private void setRepairDate() {
        txtRepairDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextRepairCode() {
        try {
            String code = RepairModel.getNextDeliveryCode();
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
        try {
            Repair repair = RepairModel.search(txtSearch.getText());
            if (repair != null) {
                txtRepairCode.setText(repair.getCode());
                txtCustomerName.setText(repair.getCustomerId());
                txtEmployeeName.setText(repair.getEmployeeId());
                txtDetails.setText(repair.getDetails());
                txtRepairDate.setText(repair.getGettingDate());
                txtAcceptingDate.setText(repair.getAcceptingDate());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void repairOnAction(ActionEvent event) {
        String repairCode = txtRepairCode.getText();
        String customerId = cmbCustomerId.getValue();
        String employeeId = cmbEmployeeId.getValue();
        String details = txtDetails.getText();
        String acceptDate = txtAcceptingDate.getText();

        try {
            String sql = "INSERT INTO repairs VALUES(?, ?, ?, ?, ?, ?)";
            int affectedRows = CrudUtil.execute(sql, repairCode, customerId, employeeId, details, String.valueOf(LocalDate.now()), acceptDate);
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Added Successfully...!").show();
                tblRepair.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
