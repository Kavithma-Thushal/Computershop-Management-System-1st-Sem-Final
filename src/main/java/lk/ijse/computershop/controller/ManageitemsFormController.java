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
import lk.ijse.computershop.dto.tm.ItemTM;
import lk.ijse.computershop.model.ItemModel;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageitemsFormController implements Initializable {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUnitprice;
    @FXML
    private TextField txtQtyonhand;
    @FXML
    private TableView tblItem;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colUnitprice;
    @FXML
    private TableColumn colQtyonhand;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern description = Pattern.compile("^([A-Z a-z 0-9]{4,40})$");
    Pattern unitPrice = Pattern.compile("^([0-9]{1,8}.?[0-9]{0,2})$");
    Pattern qtyOnHand = Pattern.compile("^([0-9]{1,6})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextItemCode();
        disableButtons();
        storeValidations();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colQtyonhand.setCellValueFactory(new PropertyValueFactory<>("qtyonhand"));
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void clearAllTxt() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitprice.clear();
        txtQtyonhand.clear();

        disableButtons();
        txtDescription.requestFocus();
        setBorders(txtCode, txtDescription, txtUnitprice, txtQtyonhand);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    private void generateNextItemCode() {
        try {
            String id = ItemModel.getNextItemCode();
            txtCode.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void storeValidations() {
        map.put(txtDescription, description);
        map.put(txtUnitprice, unitPrice);
        map.put(txtQtyonhand, qtyOnHand);
    }

    @FXML
    private void txtKeyRelease(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtnext = (TextField) response;
                txtnext.requestFocus();
            }
        }
    }

    private void getAll() {
        try {
            ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
            List<Item> itemList = ItemModel.getAll();

            for (Item item : itemList) {
                observableList.add(new ItemTM(
                        item.getCode(),
                        item.getDescription(),
                        item.getUnitprice(),
                        item.getQtyonhand()
                ));
            }
            tblItem.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Item item = new Item(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitprice.getText()),
                    Integer.parseInt(txtQtyonhand.getText())
            );

            if (ItemModel.save(item) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblItem.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        generateNextItemCode();
        txtDescription.requestFocus();

    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Item item = ItemModel.search(txtSearch.getText());
            if (item != null) {
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                txtUnitprice.setText(String.valueOf(item.getUnitprice()));
                txtQtyonhand.setText(String.valueOf(item.getQtyonhand()));

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Item item = new Item(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitprice.getText()),
                    Integer.parseInt(txtQtyonhand.getText())
            );

            if (ItemModel.update(item) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblItem.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        txtSearch.clear();
        generateNextItemCode();
        txtDescription.requestFocus();
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Are you sure...?", yes, no).showAndWait();

            if (buttonType.orElse(yes) == yes) {
                if (ItemModel.delete(txtCode.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblItem.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        clearAllTxt();
        txtSearch.clear();
        generateNextItemCode();
        txtDescription.requestFocus();
    }
}
