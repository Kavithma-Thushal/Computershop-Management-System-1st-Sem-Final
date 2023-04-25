package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lk.ijse.computershop.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class ManagereportsFormController {

    @FXML
    private void customerReportOnAction(ActionEvent event) throws SQLException, JRException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Customer", "Thushal");
        //map.put("Table Name",txt.getText());

        InputStream resource = this.getClass().getResourceAsStream("/reports/customerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DBConnection.getInstance().getConnection());
        //JasperPrintManager.printReport(jasperPrint,true);
        JasperViewer.viewReport(jasperPrint, false);
    }
}
