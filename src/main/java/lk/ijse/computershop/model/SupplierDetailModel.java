package lk.ijse.computershop.model;

import lk.ijse.computershop.util.CrudUtil;

import java.sql.SQLException;

public class SupplierDetailModel {

    public static boolean save(String supplierId, String itemCode, String supplyQty,String supplyDate) throws SQLException {
        String sql = "INSERT INTO supplier_details VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, supplierId, itemCode, supplyQty, supplyDate);
        return affectedRows > 0;
    }
}
