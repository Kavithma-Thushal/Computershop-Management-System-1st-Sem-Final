package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Custombuilds;
import lk.ijse.computershop.dto.tm.CustombuildsTM;
import lk.ijse.computershop.model.CustombuildsModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagecustombuildFormController implements Initializable {

    @FXML
    private TextField txtSearch;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblCustombuild.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Custombuilds custombuilds = CustombuildsModel.search(txtSearch.getText());
            if (custombuilds != null) {
                txtCode.setText(custombuilds.getCode());
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
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblCustombuild.refresh();
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
                if (CustombuildsModel.delete(txtCode.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblCustombuild.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }
}
