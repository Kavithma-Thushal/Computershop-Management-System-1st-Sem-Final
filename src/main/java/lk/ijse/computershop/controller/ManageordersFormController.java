package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.dto.Orders;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.model.ItemModel;
import lk.ijse.computershop.model.OrderModel;


import java.net.URL;
import java.sql.SQLException;
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
    private ComboBox<String> cmbItemCode;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtItemDescription;
    @FXML
    private TextField txtItemQty;
    @FXML
    private TextField txtItemUnitPrice;
    @FXML
    private TextField txtOrderedQty;
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
        generateNextOrderId();
        setOrderDate();
        loadCustomerIds();
        loadItemCodes();
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

    private void loadItemCodes() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> itemcodes = ItemModel.loadCodes();

            for (String code : itemcodes) {
                observableList.add(code);
            }
            cmbItemCode.setItems(observableList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbCustomerIDOnAction(ActionEvent event) {
        String id = cmbCustomerID.getValue();

        try {
            Customer customer = CustomerModel.searchById(id);
            txtCustomerName.setText(customer.getName());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbItemCodeOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();

        try {
            Item item = ItemModel.searchById(code);
            fillItemFields(item);

            txtOrderedQty.requestFocus();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void fillItemFields(Item item) {
        txtItemDescription.setText(item.getDescription());
        txtItemUnitPrice.setText(String.valueOf(item.getUnitprice()));
        txtItemQty.setText(String.valueOf(item.getQtyonhand()));
    }
}
