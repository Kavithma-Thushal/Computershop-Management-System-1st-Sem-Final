package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.dto.tm.RepairTM;
import lk.ijse.computershop.model.RepairModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerepairFormController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtEmployeeid;
    @FXML
    private TextField txtCustomerid;
    @FXML
    private TextField txtDetails;
    @FXML
    private TextField txtGetdatetime;
    @FXML
    private TextField txtAcceptdatetime;
    @FXML
    private TableView tblRepair;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colEmployeeid;
    @FXML
    private TableColumn colCustomerid;
    @FXML
    private TableColumn colDetails;
    @FXML
    private TableColumn colGetdatetime;
    @FXML
    private TableColumn colAcceptdatetime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colGetdatetime.setCellValueFactory(new PropertyValueFactory<>("getdatetime"));
        colAcceptdatetime.setCellValueFactory(new PropertyValueFactory<>("acceptdatetime"));
    }

    private void getAll() {
        try {
            ObservableList<RepairTM> observableList = FXCollections.observableArrayList();
            List<Repair> repairList = RepairModel.getAll();

            for (Repair repair : repairList) {
                observableList.add(new RepairTM(
                        repair.getCode(),
                        repair.getEmployeeid(),
                        repair.getCustomerid(),
                        repair.getDetails(),
                        repair.getGetdatetime(),
                        repair.getAcceptdatetime()
                ));
            }
            tblRepair.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Repair repair = new Repair(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtDetails.getText(),
                    txtGetdatetime.getText(),
                    txtAcceptdatetime.getText()
            );

            if (RepairModel.save(repair) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblRepair.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Repair repair = RepairModel.search(txtSearch.getText());
            if (repair != null) {
                txtCode.setText(repair.getCode());
                txtEmployeeid.setText(repair.getEmployeeid());
                txtCustomerid.setText(repair.getCustomerid());
                txtDetails.setText(repair.getDetails());
                txtGetdatetime.setText(repair.getGetdatetime());
                txtAcceptdatetime.setText(repair.getAcceptdatetime());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Repair repair = new Repair(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtDetails.getText(),
                    txtGetdatetime.getText(),
                    txtAcceptdatetime.getText()
            );

            if (RepairModel.update(repair) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblRepair.refresh();
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
                if (RepairModel.delete(txtCode.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblRepair.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }
}
