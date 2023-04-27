package lk.ijse.computershop.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import lk.ijse.computershop.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class ManagereportsFormController {

    @FXML
    private void customerReport(MouseEvent mouseEvent) throws JRException, SQLException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Customer", "Customer");
        InputStream resource = this.getClass().getResourceAsStream("/reports/customerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    private void employeeReport(MouseEvent mouseEvent) throws JRException, SQLException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Employee", "Employee");
        InputStream resource = this.getClass().getResourceAsStream("/reports/employeeReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    private void supplierReport(MouseEvent mouseEvent) throws JRException, SQLException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Employee", "Employee");
        InputStream resource = this.getClass().getResourceAsStream("/reports/supplierReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
