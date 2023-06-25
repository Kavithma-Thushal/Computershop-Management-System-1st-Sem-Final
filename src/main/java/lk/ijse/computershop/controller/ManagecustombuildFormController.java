package lk.ijse.computershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.*;
import lk.ijse.computershop.dto.tm.CustombuildsTM;
import lk.ijse.computershop.model.*;
import lk.ijse.computershop.util.EmailSend;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ManagecustombuildFormController implements Initializable {

    @FXML
    private TextField txtBuildCode;
    @FXML
    private TextField txtBuildDate;
    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<String> cmbEmployeeId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private ComboBox<String> cmbItemCode;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtQtyOnHand;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtNetTotal;
    @FXML
    private TableView tblCustomBuild;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colQty;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TableColumn colUpdate;
    @FXML
    private TableColumn colRemove;

    private ObservableList<CustombuildsTM> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBuildDate();
        setCellValueFactory();
        generateNextBuildCode();
        loadCustomerIds();
        loadEmployeeIds();
        loadItemCodes();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("update"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void setBuildDate() {
        txtBuildDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextBuildCode() {
        try {
            String code = CustombuildsModel.getNextBuildCode();
            txtBuildCode.setText(code);
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
    private void cmbItemCodeOnAction(ActionEvent event) {
        String itemCode = cmbItemCode.getValue();

        try {
            Item item = ItemModel.searchById(itemCode);
            fillItemFields(item);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void fillItemFields(Item item) {
        txtDescription.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    @FXML
    private void addToCartOnAction(ActionEvent event) {
        try {
            String code = cmbItemCode.getValue();
            String description = txtDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());

            double total = qty * unitPrice;

            Button btnUpdate = new Button("Update");
            btnUpdate.setCursor(Cursor.HAND);
            setUpdateBtnOnAction(btnUpdate);

            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(btnRemove);

            if (!observableList.isEmpty()) {
                for (int i = 0; i < tblCustomBuild.getItems().size(); i++) {
                    if (colCode.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        observableList.get(i).setQty(qty);
                        observableList.get(i).setTotal(total);

                        tblCustomBuild.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            CustombuildsTM tm = new CustombuildsTM(code, description, qty, unitPrice, total, btnUpdate, btnRemove);
            observableList.add(tm);
            tblCustomBuild.setItems(observableList);
            calculateNetTotal();

            txtQty.clear();
            txtQty.requestFocus();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    private void setUpdateBtnOnAction(Button update) {
        update.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to update?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblCustomBuild.getSelectionModel().getSelectedIndex();
                observableList.remove(index + 1);

                tblCustomBuild.refresh();
                calculateNetTotal();
            }

        });
    }

    private void setRemoveBtnOnAction(Button remove) {
        remove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblCustomBuild.getSelectionModel().getSelectedIndex();
                observableList.remove(index + 1);

                tblCustomBuild.refresh();
                calculateNetTotal();
            }

        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblCustomBuild.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        txtNetTotal.setText(String.valueOf(netTotal));
    }

    private void printBills() throws JRException, SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want a quotation?", yes, no).showAndWait();

        if (buttonType.orElse(yes) == yes) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("CustomBuild", "CustomBuild");
            InputStream resource = this.getClass().getResourceAsStream("/reports/customBuild.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        }
    }

    private void makeBuildReset(){
        txtCustomerName.clear();
        txtEmployeeName.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtNetTotal.clear();
        tblCustomBuild.getItems().clear();
        //cmbCustomerId.setValue("");
        //cmbEmployeeId.setValue("");
        //cmbItemCode.setValue("");
    }

    @FXML
    private void makeBuildOnAction(ActionEvent events) {
        String buildCode = txtBuildCode.getText();
        String customerId = cmbCustomerId.getValue();
        String employeeId = cmbEmployeeId.getValue();

        List<Custombuilds> custombuildsList = new ArrayList<>();

        for (int i = 0; i < tblCustomBuild.getItems().size(); i++) {
            CustombuildsTM custombuildsTM = observableList.get(i);

            Custombuilds custombuildsDetails = new Custombuilds(
                    custombuildsTM.getCode(),
                    custombuildsTM.getQty(),
                    custombuildsTM.getTotal()
            );
            custombuildsList.add(custombuildsDetails);
        }

        boolean isPlaced = false;
        try {
            isPlaced = MakeBuildModel.makeBuild(buildCode, customerId, employeeId, custombuildsList);
            if (isPlaced) {
                Alert makeBuildAlert=new Alert(Alert.AlertType.INFORMATION, "your build is in progress...!");
                makeBuildAlert.show();
                EmailSend.mail("Your Build is in Progress...!");

                makeBuildAlert.setOnHidden(event -> {
                    try {
                        printBills();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "please try again...!").show();
                    }
                });

            } else {
                new Alert(Alert.AlertType.ERROR, "your build is not in progress...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
        makeBuildReset();
        generateNextBuildCode();
    }
}
