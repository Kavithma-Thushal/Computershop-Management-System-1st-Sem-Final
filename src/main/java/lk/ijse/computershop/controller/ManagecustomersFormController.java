package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.tm.CustomerTM;
import lk.ijse.computershop.model.CustomerModel;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagecustomersFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNic;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAddress;
    @FXML
    private TableView tblCustomer;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNic;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colAddress;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextOrderId();
        //disableButtons();
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void generateNextOrderId() {
        try {
            String id = CustomerModel.getNextCustomerId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> observableList = FXCollections.observableArrayList();
            List<Customer> customerList = CustomerModel.getAll();

            for (Customer customer : customerList) {
                CustomerTM customerTM = new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getNic(),
                        customer.getEmail(),
                        customer.getContact(),
                        customer.getAddress()
                );
                observableList.add(customerTM);
            }
            tblCustomer.setItems(observableList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    private void allTextClear() {
        txtId.clear();
        txtName.clear();
        txtNic.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        try {
            Customer customer = null;
            if (!txtName.getText().isEmpty() && !txtNic.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtContact.getText().isEmpty() && !txtAddress.getText().isEmpty()) {
                customer = new Customer(
                        txtId.getText(),
                        txtName.getText(),
                        txtNic.getText(),
                        txtEmail.getText(),
                        txtContact.getText(),
                        txtAddress.getText()
                );
            }

            if (CustomerModel.save(customer) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully...!").show();
                tblCustomer.refresh();
                getAll();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        allTextClear();
        generateNextOrderId();
        txtName.requestFocus();
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        try {
            Customer customer = CustomerModel.search(txtSearch.getText());
            if (customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtNic.setText(customer.getNic());
                txtEmail.setText(customer.getEmail());
                txtContact.setText(customer.getContact());
                txtAddress.setText(customer.getAddress());
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    @FXML
    private void updateOnAction(ActionEvent event) {

        try {
            Customer customer = new Customer(
                    txtId.getText(),
                    txtName.getText(),
                    txtNic.getText(),
                    txtEmail.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );

            if (CustomerModel.update(customer) > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully...!").show();
                tblCustomer.refresh();
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
                if (CustomerModel.delete(txtId.getText()) > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully...!").show();
                    tblCustomer.refresh();
                    getAll();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
