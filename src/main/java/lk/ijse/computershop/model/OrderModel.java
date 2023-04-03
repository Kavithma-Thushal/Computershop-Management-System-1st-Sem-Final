package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Orders;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    public static int save(Orders orders) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                orders.getId(),
                orders.getCustomerid(),
                orders.getQty(),
                orders.getDatetime()
        );
    }

    public static Orders search(String id) throws SQLException {

        String sql = "SELECT * FROM orders WHERE id=?";
        ResultSet resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static int update(Orders orders) throws SQLException {

        String sql = "UPDATE orders SET customerid=? , qty=? , datetime=? WHERE id=?";
        return CrudUtil.execute(
                sql,
                orders.getCustomerid(),
                orders.getQty(),
                orders.getDatetime(),
                orders.getId()
        );
    }

    public static int delete(String id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id=?";
        return CrudUtil.execute(sql, id);
    }

    public static List<Orders> getAll() throws SQLException {

        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Orders orders = new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)
            );
            ordersList.add(orders);
        }
        return ordersList;
    }
}
