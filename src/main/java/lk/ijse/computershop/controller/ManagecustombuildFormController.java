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
import lk.ijse.computershop.dto.Custombuilds;
import lk.ijse.computershop.dto.tm.CustombuildsTM;
import lk.ijse.computershop.model.CustombuildsModel;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ManagecustombuildFormController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtCustomerid;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDatetime;
    @FXML
    private TableView tblCustombuild;
    @FXML
    private TableColumn colCustomerid;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colDatetime;
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
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDatetime.setCellValueFactory(new PropertyValueFactory<>("datetime"));
    }

    private void getAll() {
        try {
            ObservableList<CustombuildsTM> observableList = FXCollections.observableArrayList();
            List<Custombuilds> custombuildsList = CustombuildsModel.getAll();

            for (Custombuilds custombuilds : custombuildsList) {
                observableList.add(new CustombuildsTM(
                        custombuilds.getCode(),
                        custombuilds.getCustomerid(),
                        custombuilds.getDescription(),
                        custombuilds.getDatetime()
                ));
            }
            tblCustombuild.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Custombuilds custombuilds = new Custombuilds(
                    txtCode.getText(),
                    txtCustomerid.getText(),
                    txtDescription.getText(),
                    txtDatetime.getText()
            );

            if (CustombuildsModel.save(custombuilds) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Custombuilds custombuilds = CustombuildsModel.search(txtCode.getText());
            if (custombuilds != null) {
                txtCustomerid.setText(custombuilds.getCustomerid());
                txtDescription.setText(String.valueOf(custombuilds.getDescription()));
                txtDatetime.setText(String.valueOf(custombuilds.getDatetime()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Custombuilds custombuilds = new Custombuilds(
                    txtCode.getText(),
                    txtCustomerid.getText(),
                    txtDescription.getText(),
                    txtDatetime.getText()
            );

            if (CustombuildsModel.update(custombuilds) > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            if (CustombuildsModel.delete(txtCode.getText()) > 0) {
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
