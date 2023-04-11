package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Orders;
import lk.ijse.computershop.dto.tm.OrdersTM;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.OrderModel;


import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageordersFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtDate;
    @FXML
    private ComboBox<String> cmbCustomerID;
    @FXML
    private TableView tblOrders;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colCustomerid;
    @FXML
    private TableColumn colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextOrderId();
        setOrderDate();
        loadCustomerIds();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAll() {
        try {
            ObservableList<OrdersTM> observableList = FXCollections.observableArrayList();
            List<Orders> ordersList = OrderModel.getAll();

            for (Orders orders : ordersList) {
                observableList.add(new OrdersTM(
                        orders.getId(),
                        orders.getCustomerid(),
                        orders.getDate()
                ));
            }
            tblOrders.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Orders orders = new Orders(
                    txtId.getText(),
                    cmbCustomerID.getSelectionModel().getSelectedItem(),
                    txtDate.getText()
            );

            if (OrderModel.save(orders) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblOrders.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void setOrderDate() {
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> customerid = CustomerModel.loadIds();

            for (String id : customerid) {
                observableList.add(id);
            }
            cmbCustomerID.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
