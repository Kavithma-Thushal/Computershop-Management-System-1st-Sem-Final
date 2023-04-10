package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Delivery;
import lk.ijse.computershop.dto.tm.DeliveryTM;
import lk.ijse.computershop.model.DeliveryModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagedeliveryFormController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtEmployeeid;
    @FXML
    private TextField txtCustomerid;
    @FXML
    private TextField txtOrderid;
    @FXML
    private TextField txtDetails;
    @FXML
    private TextField txtLocation;
    @FXML
    private TableView tblDelivery;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colEmployeeid;
    @FXML
    private TableColumn colCustomerid;
    @FXML
    private TableColumn colOrderid;
    @FXML
    private TableColumn colDetails;
    @FXML
    private TableColumn colLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colEmployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colOrderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void getAll() {
        try {
            ObservableList<DeliveryTM> observableList = FXCollections.observableArrayList();
            List<Delivery> deliveryList = DeliveryModel.getAll();

            for (Delivery delivery : deliveryList) {
                observableList.add(new DeliveryTM(
                        delivery.getCode(),
                        delivery.getEmployeeid(),
                        delivery.getCustomerid(),
                        delivery.getOrderid(),
                        delivery.getDetails(),
                        delivery.getLocation()
                ));
            }
            tblDelivery.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Delivery delivery = new Delivery(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtOrderid.getText(),
                    txtDetails.getText(),
                    txtLocation.getText()
            );

            if (DeliveryModel.save(delivery) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblDelivery.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Delivery delivery = DeliveryModel.search(txtSearch.getText());
            if (delivery != null) {
                txtCode.setText(delivery.getCode());
                txtEmployeeid.setText(delivery.getEmployeeid());
                txtCustomerid.setText(delivery.getCustomerid());
                txtOrderid.setText(delivery.getOrderid());
                txtDetails.setText(delivery.getDetails());
                txtLocation.setText(delivery.getLocation());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Delivery delivery = new Delivery(
                    txtCode.getText(),
                    txtEmployeeid.getText(),
                    txtCustomerid.getText(),
                    txtOrderid.getText(),
                    txtDetails.getText(),
                    txtLocation.getText()
            );

            if (DeliveryModel.update(delivery) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblDelivery.refresh();
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
                if (DeliveryModel.delete(txtCode.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblDelivery.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }
}
