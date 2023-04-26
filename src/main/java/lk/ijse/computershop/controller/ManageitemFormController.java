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
import javafx.scene.input.MouseEvent;
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

public class ManageitemFormController implements Initializable {

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtQtyOnHand;
    @FXML
    private TableView tblItem;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colQtyOnHand;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern description = Pattern.compile("^([A-Z a-z 0-9 \\W]{4,40})$");
    Pattern unitPrice = Pattern.compile("^(?!00)[0-9]{2,8}(?:\\.[0-9]{2})?$");
    Pattern qtyOnHand = Pattern.compile("^([0-9]{1,6})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextItemCode();
        storeValidations();
        disableButtons();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void storeValidations() {
        map.put(txtDescription, description);
        map.put(txtUnitPrice, unitPrice);
        map.put(txtQtyOnHand, qtyOnHand);
    }

    private void clearAllTxt() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        disableButtons();
        txtDescription.requestFocus();
        setBorders(txtCode, txtDescription, txtUnitPrice, txtQtyOnHand);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    @FXML
    private void reset(MouseEvent mouseEvent) {
        clearAllTxt();
        generateNextItemCode();
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

    private void generateNextItemCode() {
        try {
            String id = ItemModel.getNextItemCode();
            txtCode.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
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
                        item.getUnitPrice(),
                        item.getQtyOnHand()
                ));
            }
            tblItem.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Item item = null;
            if (!txtDescription.getText().isEmpty() && !txtUnitPrice.getText().isEmpty() && !txtQtyOnHand.getText().isEmpty()) {
                item = new Item(
                        txtCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQtyOnHand.getText())
                );
            }

            if (ItemModel.save(item) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                getAll();
                clearAllTxt();
                txtDescription.requestFocus();
                generateNextItemCode();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Item item = ItemModel.search(txtSearch.getText());
            if (item != null) {
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Input...!").show();
                clearAllTxt();
                generateNextItemCode();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        txtSearch.clear();
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        try {
            Item item = new Item(
                    txtCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            );

            if (ItemModel.update(item) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                getAll();
                clearAllTxt();
                txtDescription.requestFocus();
                generateNextItemCode();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
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
                    getAll();
                    clearAllTxt();
                    txtDescription.requestFocus();
                    generateNextItemCode();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
