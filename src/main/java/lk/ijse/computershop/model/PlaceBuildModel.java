package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.Custombuilds;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceBuildModel {

    public static boolean placeOrder(String orderId, String customerId, String employeeId, List<Custombuilds> buildList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = CustombuildsModel.save(orderId, customerId,employeeId);     //orders
            if (isSaved) {
                boolean isUpdated = ItemModel.updateBuildQty(buildList);     //items update
                if (isUpdated) {
                    boolean isBuild = BuildDetailsModel.saveBuild(orderId, buildList, LocalDate.now());      //order_details
                    if (isBuild) {
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
