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
import lk.ijse.computershop.dto.Delivery;
import lk.ijse.computershop.dto.tm.DeliveryTM;
import lk.ijse.computershop.model.DeliveryModel;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ManagedeliveryFormController implements Initializable {

    @FXML
    private AnchorPane root;
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
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Delivery delivery = DeliveryModel.search(txtCode.getText());
            if (delivery != null) {
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
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            if (DeliveryModel.delete(txtCode.getText()) > 0) {
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
