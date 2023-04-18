package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.dto.tm.ItemTM;
import lk.ijse.computershop.model.ViewItemsModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewitemsFormController implements Initializable {

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
    private TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colQtyonhand.setCellValueFactory(new PropertyValueFactory<>("qtyonhand"));
    }

    private void getAll() {
        try {
            ObservableList<ItemTM> observableList = FXCollections.observableArrayList();
            List<Item> itemList = ViewItemsModel.getAll();

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
    private void searchOnAction(ActionEvent event) {
        try {
            Item item = ViewItemsModel.search(txtSearch.getText());
            if (item != null) {
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                txtUnitprice.setText(String.valueOf(item.getUnitprice()));
                txtQtyonhand.setText(String.valueOf(item.getQtyonhand()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        txtSearch.clear();
    }
}
