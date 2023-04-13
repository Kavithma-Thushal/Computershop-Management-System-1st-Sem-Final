package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Order;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailModel {

    public static boolean save(String oId, List<Order> orderList, LocalDate date) throws SQLException {
        for (Order dto : orderList) {
            if (!save(oId, dto,LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, Order dto, LocalDate date) throws SQLException {
        String sql = "INSERT INTO order_details VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, oId, dto.getCode(), dto.getQty(), Date.valueOf(date));
        return affectedRows > 0;
    }
}
