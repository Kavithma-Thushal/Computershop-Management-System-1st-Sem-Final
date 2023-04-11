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
import lk.ijse.computershop.model.OrderModel;


import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageordersFormController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtCustomerid;
    @FXML
    private TextField txtDate;
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
                    txtCustomerid.getText(),
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

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Orders orders = OrderModel.search(txtSearch.getText());
            if (orders != null) {
                txtId.setText(orders.getId());
                txtCustomerid.setText(orders.getCustomerid());
                txtDate.setText(String.valueOf(orders.getDate()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Orders orders = new Orders(
                    txtId.getText(),
                    txtCustomerid.getText(),
                    txtDate.getText()
            );

            if (OrderModel.update(orders) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblOrders.refresh();
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
                if (OrderModel.delete(txtId.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblOrders.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }
}
