package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Order;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailModel {

    public static boolean save(String orderId, List<Order> orderList, LocalDate date) throws SQLException {
        for (Order dto : orderList) {
            if (!save(orderId, dto,LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String orderId, Order orderDetails, LocalDate date) throws SQLException {
        String sql = "INSERT INTO order_details VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, orderId, orderDetails.getCode(), orderDetails.getQty(), Date.valueOf(date));
        return affectedRows > 0;
    }
}
