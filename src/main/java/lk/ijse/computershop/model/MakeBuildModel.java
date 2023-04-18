package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.Custombuilds;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MakeBuildModel {

    public static boolean makeBuild(String buildCode, String customerId, String employeeId, List<Custombuilds> buildList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = CustombuildsModel.save(buildCode, customerId, employeeId);     //orders
            if (isSaved) {
                boolean isBuild = BuildDetailsModel.saveBuild(buildCode, buildList, LocalDate.now());      //order_details
                if (isBuild) {
                    boolean isUpdated = ItemModel.updateBuildQty(buildList);     //items update
                    if (isUpdated) {
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
