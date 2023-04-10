package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Salary;
import lk.ijse.computershop.dto.tm.SalaryTM;
import lk.ijse.computershop.model.SalaryModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagesalaryFormController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtEmployeeid;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDatetime.setCellValueFactory(new PropertyValueFactory<>("datetime"));
    }

    private void getAll() {
        try {
            ObservableList<SalaryTM> observableList = FXCollections.observableArrayList();
            List<Salary> salaryList = SalaryModel.getAll();

            for (Salary salary : salaryList) {
                observableList.add(new SalaryTM(
                        salary.getCode(),
                        salary.getEmployeeid(),
                        salary.getAmount(),
                        salary.getDatetime()
                ));
            }
            tblSalary.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Salary salary = new Salary(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDatetime.getText()
            );

            if (SalaryModel.save(salary) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblSalary.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Salary salary = SalaryModel.search(txtSearch.getText());
            if (salary != null) {
                txtCode.setText(salary.getCode());
                txtEmployeeid.setText(salary.getEmployeeid());
                txtAmount.setText(String.valueOf(salary.getAmount()));
                txtDatetime.setText(String.valueOf(salary.getDatetime()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Salary salary = new Salary(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    Double.parseDouble(txtAmount.getText()),
                    txtDatetime.getText()
            );

            if (SalaryModel.update(salary) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblSalary.refresh();
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
                if (SalaryModel.delete(txtCode.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblSalary.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }
}
