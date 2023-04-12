package lk.ijse.computershop.model;

import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.CartDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {

    public static boolean placeOrder(String oId, String cusId, List<CartDTO> cartDTOList) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isSaved = OrderModel.save(oId, cusId);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQty(cartDTOList);
                if (isUpdated) {
                    boolean isOrdered = OrderDetailModel.save(oId, cartDTOList,LocalDate.now());
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
