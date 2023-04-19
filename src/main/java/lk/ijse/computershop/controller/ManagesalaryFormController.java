package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.dto.Salary;
import lk.ijse.computershop.dto.tm.SalaryTM;
import lk.ijse.computershop.model.EmployeeModel;
import lk.ijse.computershop.model.SalaryModel;
import lk.ijse.computershop.util.CrudUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagesalaryFormController implements Initializable {

    @FXML
    private TextField txtCode;
    @FXML
    private ComboBox<String> cmbEmployeeId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtDatetime;
    @FXML
    private TableView tblSalary;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colEmployeeid;
    @FXML
    private TableColumn colAmount;
    @FXML
    private TableColumn colDatetime;
    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        setSalaryDate();
        generateNextSalaryCode();
        loadEmployeeIds();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDatetime.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setSalaryDate() {
        txtDatetime.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextSalaryCode() {
        try {
            String code = SalaryModel.getNextSalaryCode();
            txtCode.setText(code);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void getAll() {
        try {
            ObservableList<SalaryTM> observableList = FXCollections.observableArrayList();
            List<Salary> salaryList = SalaryModel.getAll();

            for (Salary salary : salaryList) {
                observableList.add(new SalaryTM(
                        salary.getCode(),
                        salary.getEmployeeName(),
                        salary.getAmount(),
                        salary.getDate()
                ));
            }
            tblSalary.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
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
            Salary salary = SalaryModel.search(txtSearch.getText());
            if (salary != null) {
                txtCode.setText(salary.getCode());
                txtEmployeeName.setText(salary.getEmployeeName());
                txtAmount.setText(String.valueOf(salary.getAmount()));
                txtDatetime.setText(String.valueOf(salary.getDate()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void payOnAction(ActionEvent event) {
        String salaryCode = txtCode.getText();
        String employeeId = cmbEmployeeId.getValue();
        String amount = txtAmount.getText();

        try {
            String sql = "INSERT INTO salary VALUES(?, ?, ?, ?)";
            int affectedRows = CrudUtil.execute(sql, salaryCode, employeeId, amount, String.valueOf(LocalDate.now()));
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Paid Successfully...!").show();
                tblSalary.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        generateNextSalaryCode();
    }
}
