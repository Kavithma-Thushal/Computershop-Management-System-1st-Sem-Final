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
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.dto.tm.EmployeeTM;
import lk.ijse.computershop.model.EmployeeModel;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageemployeeFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtJobrole;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TableView tblemployee;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colJobrole;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colPassword;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern contact = Pattern.compile("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$");
    Pattern jobRole = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern userName = Pattern.compile("^([A-Z a-z 0-9]{4,40})$");
    Pattern password = Pattern.compile("^([A-Za-z0-9\\W]{4,40})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextOrderId();
        disableButtons();
        storeValidations();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJobrole.setCellValueFactory(new PropertyValueFactory<>("jobrole"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void clearAllTxt() {
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtJobrole.clear();
        txtUsername.clear();
        txtPassword.clear();

        disableButtons();
        txtName.requestFocus();
        setBorders(txtId, txtName, txtContact, txtJobrole, txtUsername, txtPassword);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    private void generateNextOrderId() {
        try {
            String id = EmployeeModel.getNextEmployeeId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void storeValidations() {
        map.put(txtName, name);
        map.put(txtContact, contact);
        map.put(txtJobrole, jobRole);
        map.put(txtUsername, userName);
        map.put(txtPassword, password);
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
        }
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> observableList = FXCollections.observableArrayList();
            List<Employee> employeeList = EmployeeModel.getAll();

            for (Employee employee : employeeList) {
                observableList.add(new EmployeeTM(
                        employee.getId(),
                        employee.getName(),
                        employee.getContact(),
                        employee.getJobrole(),
                        employee.getUsername(),
                        employee.getPassword()
                ));
            }
            tblemployee.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {

        try {
            Employee employee = new Employee(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtJobrole.getText(),
                    txtUsername.getText(),
                    txtPassword.getText()
            );

            if (EmployeeModel.save(employee) > 0) {

                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblemployee.refresh();
                getAll();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        generateNextOrderId();
        txtName.requestFocus();
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Employee employee = EmployeeModel.search(txtSearch.getText());
            if (employee != null) {
                txtId.setText(employee.getId());
                txtName.setText(employee.getName());
                txtContact.setText(employee.getContact());
                txtJobrole.setText(employee.getJobrole());
                txtUsername.setText(employee.getUsername());
                txtPassword.setText(employee.getPassword());

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        txtSearch.clear();
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Employee employee = new Employee(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtJobrole.getText(),
                    txtUsername.getText(),
                    txtPassword.getText()
            );

            if (EmployeeModel.update(employee) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblemployee.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        txtSearch.clear();
        generateNextOrderId();
        txtName.requestFocus();
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Are you sure...?", yes, no).showAndWait();

            if (buttonType.orElse(yes) == yes) {
                if (EmployeeModel.delete(txtId.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblemployee.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        txtSearch.clear();
        generateNextOrderId();
        txtName.requestFocus();
    }
}
