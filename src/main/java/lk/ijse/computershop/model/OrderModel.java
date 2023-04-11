package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Orders;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    public static int save(Orders orders) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?,?)";
        return CrudUtil.execute(
                sql,
                orders.getId(),
                orders.getCustomerid(),
                orders.getDate()
        );
    }

    public static List<Orders> getAll() throws SQLException {

        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Orders orders = new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            ordersList.add(orders);
        }
        return ordersList;
    }

    public static String getNextOrderId() throws SQLException {
        String sql = "SELECT id FROM Orders ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("Or0");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "Or0" + id;
        }
        return "Or01";
    }
}
