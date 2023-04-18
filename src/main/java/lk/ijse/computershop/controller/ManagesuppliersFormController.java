package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.dto.Supply;
import lk.ijse.computershop.dto.tm.SupplyTM;
import lk.ijse.computershop.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagesuppliersFormController implements Initializable {

    @FXML
    private TextField txtSupplyId;
    @FXML
    private TextField txtSupplyDate;
    @FXML
    private TextField txtSupplyName;
    @FXML
    private TextField txtSupplyContact;
    @FXML
    private TextField txtSupplyAddress;
    @FXML
    private ComboBox<String> cmbItemCode;
    @FXML
    private TextField txtItemDescription;
    @FXML
    private TextField txtQty;
    @FXML
    private TableView tblsupplier;
    @FXML
    private TableColumn colSupplyId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSupplyDate();
        getAll();
        setCellValueFactory();
        generateNextSupplyId();
        loadItemCodes();
    }

    private void setCellValueFactory() {
        colSupplyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void getAll() {
        try {
            ObservableList<SupplyTM> observableList = FXCollections.observableArrayList();
            List<Supply> supplyList = SupplierModel.getAll();

            for (Supply supply : supplyList) {
                SupplyTM supplyTM = new SupplyTM(
                        supply.getId(),
                        supply.getName(),
                        supply.getContact(),
                        supply.getAddress()
                );
                observableList.add(supplyTM);
            }
            tblsupplier.setItems(observableList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    private void setSupplyDate() {
        txtSupplyDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextSupplyId() {
        try {
            String id = SupplierModel.getNextSupplyId();
            txtSupplyId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadItemCodes() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> itemCode = ItemModel.loadCodes();

            for (String code : itemCode) {
                observableList.add(code);
            }
            cmbItemCode.setItems(observableList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbItemCodeOnAction(ActionEvent event) {
        String itemCode = cmbItemCode.getValue();

        try {
            Item item = ItemModel.searchById(itemCode);
            txtItemDescription.setText(item.getDescription());

            //txtQty.requestFocus();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {

    }

    @FXML
    private void supplierAddOnAction(ActionEvent event) {
        String supplierId = txtSupplyId.getText();
        String supplyDate = txtSupplyDate.getText();
        String name = txtSupplyName.getText();
        String contact = txtSupplyContact.getText();
        String address = txtSupplyAddress.getText();
        String itemCode = cmbItemCode.getValue();
        String supplyQty = txtQty.getText();

        boolean isPlaced = false;
        try {
            isPlaced = AddSupplyModel.addSupplier(supplierId, supplyDate, name, contact, address, itemCode, supplyQty);
            if (isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier added...!").show();
                tblsupplier.refresh();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier is not added...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!r").show();
        }
    }
}
