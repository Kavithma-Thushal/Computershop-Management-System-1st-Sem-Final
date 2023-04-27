package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.dto.Supply;
import lk.ijse.computershop.dto.tm.SupplyTM;
import lk.ijse.computershop.model.*;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    private Button btnAdd;

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern address = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern contact = Pattern.compile("^(07(0|1|2|4|5|6|7|8)|091)[0-9]{7}$");
    Pattern supplyQty = Pattern.compile("^([0-9]{1,6})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        setSupplyDate();
        btnAdd.setDisable(true);
        generateNextSupplyId();
        loadItemCodes();
        storeValidations();
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

    private void clearAllTxt() {
        txtSupplyName.clear();
        txtSupplyAddress.clear();
        txtSupplyContact.clear();
        txtQty.clear();
        txtItemDescription.clear();

        btnAdd.setDisable(true);
        txtSupplyName.requestFocus();
        setBorders(txtSupplyName, txtSupplyAddress, txtSupplyContact, txtQty);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    private void storeValidations() {
        map.put(txtSupplyName, name);
        map.put(txtSupplyAddress, address);
        map.put(txtSupplyContact, contact);
        map.put(txtQty, supplyQty);
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
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
        clearAllTxt();
        generateNextSupplyId();
    }
}
