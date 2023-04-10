package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Supplier;
import lk.ijse.computershop.dto.tm.SupplierTM;
import lk.ijse.computershop.model.SupplierModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagesuppliersFormController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAddress;
    @FXML
    private TableView tblsupplier;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void getAll() {
        try {
            ObservableList<SupplierTM> observableList = FXCollections.observableArrayList();
            List<Supplier> supplierList = SupplierModel.getAll();

            for (Supplier supplier : supplierList) {
                observableList.add(new SupplierTM(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getContact(),
                        supplier.getAddress()
                ));
            }
            tblsupplier.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Supplier supplier = new Supplier(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );

            if (SupplierModel.save(supplier) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblsupplier.refresh();
                getAll();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Supplier supplier = SupplierModel.search(txtSearch.getText());
            if (supplier != null) {
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtContact.setText(supplier.getContact());
                txtAddress.setText(supplier.getAddress());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Supplier supplier = new Supplier(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );

            if (SupplierModel.update(supplier) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblsupplier.refresh();
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
                if (SupplierModel.delete(txtId.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblsupplier.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }


}
