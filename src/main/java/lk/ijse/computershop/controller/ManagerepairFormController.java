package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.dto.tm.RepairTM;
import lk.ijse.computershop.model.RepairModel;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerepairFormController implements Initializable {

    @FXML
    private AnchorPane root;
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
    @FXML
    private Label lbldateandtime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd      hh:mm");
        Date date = new Date();
        lbldateandtime.setText(simpleDateFormat.format(date));

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
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Repair repair = RepairModel.search(txtCode.getText());
            if (repair != null) {
                txtEmployeeid.setText(repair.getEmployeeid());
                txtCustomerid.setText(String.valueOf(repair.getCustomerid()));
                txtDetails.setText(String.valueOf(repair.getDetails()));
                txtGetdatetime.setText(String.valueOf(repair.getGetdatetime()));
                txtAcceptdatetime.setText(String.valueOf(repair.getAcceptdatetime()));
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
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            if (RepairModel.delete(txtCode.getText()) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void backOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/cashierdashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Cashier Dashboard");
        stage.setResizable(false);
        stage.show();
    }
}
