package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.dto.tm.RepairTM;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.EmployeeModel;
import lk.ijse.computershop.model.RepairModel;
import lk.ijse.computershop.model.SalaryModel;
import lk.ijse.computershop.util.CrudUtil;
import lk.ijse.computershop.util.EmailSend;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    @FXML
    private Button btnRepair;

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern details = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern acceptingDate = Pattern.compile("^\\d{4}[-./](0[1-9]|1[012])[-./](0[1-9]|[12][0-9]|3[01])$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRepairDate();
        generateNextRepairCode();
        getAll();
        setCellValueFactory();
        loadCustomerIds();
        loadEmployeeIds();
        storeValidations();
        btnRepair.setDisable(true);
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colGettingDate.setCellValueFactory(new PropertyValueFactory<>("gettingDate"));
        colAcceptingDate.setCellValueFactory(new PropertyValueFactory<>("acceptingDate"));
    }

    private void storeValidations() {
        map.put(txtDetails, details);
        map.put(txtAcceptingDate, acceptingDate);
    }

    private void clearAllTxt() {
        txtCustomerName.clear();
        txtEmployeeName.clear();
        txtDetails.clear();
        txtAcceptingDate.clear();

        btnRepair.setDisable(true);
        setBorders(txtDetails,txtAcceptingDate);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnRepair);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
        }
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
        txtSearch.clear();
    }

    @FXML
    private void repairOnAction(ActionEvent event) {
        String repairCode = txtRepairCode.getText();
        String customerId = cmbCustomerId.getValue();
        String employeeId = cmbEmployeeId.getValue();
        String details = txtDetails.getText();
        String acceptDate = txtAcceptingDate.getText();

        try {
            if(!txtCustomerName.getText().isEmpty() && !txtEmployeeName.getText().isEmpty() && !txtDetails.getText().isEmpty() && !txtAcceptingDate.getText().isEmpty()){
                String sql = "INSERT INTO repairs VALUES(?, ?, ?, ?, ?, ?)";
                int affectedRows = CrudUtil.execute(sql, repairCode, customerId, employeeId, details, String.valueOf(LocalDate.now()), acceptDate);
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Added Successfully...!").show();
                    //EmailSend.mail("New Repair Available...!");
                    getAll();
                    clearAllTxt();
                    txtDetails.requestFocus();
                    generateNextRepairCode();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "please try again...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        generateNextRepairCode();
    }
}
