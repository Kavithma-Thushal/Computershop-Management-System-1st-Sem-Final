package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {

    public static boolean placeOrder(String orderId, String customerId, List<Order> orderList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = OrderModel.save(orderId, customerId);     //orders
            if (isSaved) {
                boolean isOrdered = OrderDetailModel.save(orderId, orderList, LocalDate.now());      //order_details
                if (isOrdered) {
                    boolean isUpdated = ItemModel.updateQty(orderList);     //items update
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
