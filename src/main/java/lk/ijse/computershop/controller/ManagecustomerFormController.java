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
import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.tm.CustomerTM;
import lk.ijse.computershop.model.CustomerModel;
import lk.ijse.computershop.util.Validation;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManagecustomerFormController implements Initializable {

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
    private TableColumn colId;
    @FXML
    private TableColumn colName;
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

    private LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^([A-Z a-z]{4,40})$");
    Pattern nic = Pattern.compile("^([0-9]{12}|[0-9]{9}V)$");
    Pattern email = Pattern.compile("^[A-Z a-z 0-9 !#$%&'*+/=?^_`{|}~-]+@gmail\\.[A-Z a-z]+$");
    Pattern contact = Pattern.compile("^(07(0|1|2|4|5|6|7|8)|091)[0-9]{7}$");
    Pattern address = Pattern.compile("^([A-Z a-z]{4,40})$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
        generateNextOrderId();
        storeValidations();
        disableButtons();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void disableButtons() {
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void clearAllTxt() {
        txtId.clear();
        txtName.clear();
        txtNic.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();

        disableButtons();
        txtName.requestFocus();
        setBorders(txtId, txtName, txtNic, txtEmail, txtContact, txtAddress);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: transparent");
        }
    }

    private void generateNextOrderId() {
        try {
            String id = CustomerModel.getNextCustomerId();
            txtId.setText(id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void storeValidations() {
        map.put(txtName, name);
        map.put(txtNic, nic);
        map.put(txtEmail, email);
        map.put(txtContact, contact);
        map.put(txtAddress, address);
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
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
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
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully...!").show();
                //tblCustomer.refresh();
                getAll();
                clearAllTxt();
                txtName.requestFocus();
                generateNextOrderId();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
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

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
        txtSearch.clear();
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
        clearAllTxt();
        txtSearch.clear();
        generateNextOrderId();
        txtName.requestFocus();
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
        clearAllTxt();
        txtSearch.clear();
        generateNextOrderId();
        txtName.requestFocus();
    }
}
