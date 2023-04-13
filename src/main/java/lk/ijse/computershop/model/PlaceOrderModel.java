package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {

    public static boolean placeOrder(String oId, String cusId, List<Order> orderList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = OrderModel.save(oId, cusId);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQty(orderList);
                if (isUpdated) {
                    boolean isOrdered = OrderDetailModel.save(oId, orderList,LocalDate.now());
                    if (isOrdered) {
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception er) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }
}
