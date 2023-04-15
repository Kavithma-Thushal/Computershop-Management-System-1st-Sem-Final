package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class AddSupplyModel {

    public static boolean addSupplier(String supplierId,String supplyDate,String name,String contact,String address,String itemCode,String itemDescription,String supplyQty) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = SupplierModel.save(supplierId, name,contact,address);     //orders
            if (isSaved) {
                boolean isUpdated = ItemModel.updateSupplyQty(itemCode,supplyQty);     //items update
                if (isUpdated) {
                    boolean isOrdered = SupplierDetailModel.save(supplierId, itemCode, supplyQty,supplyDate);      //order_details
                    if (isOrdered) {
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
