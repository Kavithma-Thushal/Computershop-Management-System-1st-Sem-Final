package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.dto.tm.EmployeeTM;
import lk.ijse.computershop.model.EmployeeModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageemployeesFormController implements Initializable {

    @FXML
    private TextField txtSearch;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextOrderId();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJobrole.setCellValueFactory(new PropertyValueFactory<>("jobrole"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void generateNextOrderId() {
        try {
            String id = EmployeeModel.getNextEmployeeId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
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
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
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
    }
}
