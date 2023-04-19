package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.dto.*;
import lk.ijse.computershop.dto.tm.DeliveryTM;
import lk.ijse.computershop.model.*;
import lk.ijse.computershop.util.CrudUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagedeliveryFormController implements Initializable {

    @FXML
    private TextField txtDeliveryCode;
    @FXML
    private TextField txtDeliveryDate;
    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<String> cmbEmployeeId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private ComboBox<String> cmbOrderId;
    @FXML
    private TextField txtOrderDescription;
    @FXML
    private TextField txtLocation;
    @FXML
    private TableView tblDelivery;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colCustomerId;
    @FXML
    private TableColumn colEmployeeId;
    @FXML
    private TableColumn colOrderId;
    @FXML
    private TableColumn colLocation;
    @FXML
    private TableColumn colDate;

    private ObservableList<DeliveryTM> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDeliveryDate();
        setCellValueFactory();
        getAll();
        generateNextDeliveryCode();
        loadCustomerIds();
        loadEmployeeIds();
        loadOrderIds();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAll() {
        try {
            ObservableList<DeliveryTM> observableList = FXCollections.observableArrayList();
            List<Delivery> deliveryList = DeliveryModel.getAll();

            for (Delivery delivery : deliveryList) {
                DeliveryTM deliveryTM = new DeliveryTM(
                        delivery.getCode(),
                        delivery.getCustomerId(),
                        delivery.getEmployeeId(),
                        delivery.getOrderId(),
                        delivery.getLocation(),
                        delivery.getDate()
                );
                observableList.add(deliveryTM);
            }
            tblDelivery.setItems(observableList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please try again...!").show();
        }
    }

    private void setDeliveryDate() {
        txtDeliveryDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextDeliveryCode() {
        try {
            String code = DeliveryModel.getNextDeliveryCode();
            txtDeliveryCode.setText(code);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> customerId = CustomerModel.loadIds();

            for (String id : customerId) {
                observableList.add(id);
            }
            cmbCustomerId.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> employeeId = EmployeeModel.loadIds();

            for (String id : employeeId) {
                observableList.add(id);
            }
            cmbEmployeeId.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void loadOrderIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<String> orderId = OrderModel.loadIds();

            for (String id : orderId) {
                observableList.add(id);
            }
            cmbOrderId.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = cmbCustomerId.getValue();
        cmbCustomerId.setDisable(true);

        try {
            Customer customer = CustomerModel.searchById(customerId);
            txtCustomerName.setText(customer.getName());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbEmployeeIdOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();
        cmbEmployeeId.setDisable(true);

        try {
            Employee employee = EmployeeModel.searchById(employeeId);
            txtEmployeeName.setText(employee.getName());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void cmbOrderIdOnAction(ActionEvent event) {

    }

    @FXML
    private void deliverOnAction(ActionEvent event) throws SQLException {
        String deliveryCode = txtDeliveryCode.getText();
        String customerId = cmbCustomerId.getValue();
        String employeeId = cmbEmployeeId.getValue();
        String orderId = cmbOrderId.getValue();
        String location = txtLocation.getText();

        try {
            String sql = "INSERT INTO delivery VALUES(?, ?, ?, ?, ?, ?)";
            int affectedRows = CrudUtil.execute(sql, deliveryCode, customerId, employeeId, orderId, location, String.valueOf(LocalDate.now()));
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Added Successfully...!").show();
                tblDelivery.refresh();
                getAll();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        generateNextDeliveryCode();
    }
}
