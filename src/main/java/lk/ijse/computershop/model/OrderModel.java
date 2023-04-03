package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Orders;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.SQLException;

public class OrderModel {

    public static int save(Orders orders) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                orders.getId(),
                orders.getCustomerid(),
                orders.getDescription(),
                orders.getQty(),
                orders.getDatetime()
        );
    }
}
