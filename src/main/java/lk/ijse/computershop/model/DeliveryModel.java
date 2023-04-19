package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Delivery;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModel {

    public static List<Delivery> getAll() throws SQLException {

        List<Delivery> deliveryList = new ArrayList<>();
        String sql = "SELECT * FROM delivery";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Delivery delivery = new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

    public static String getNextDeliveryCode() throws SQLException {
        String sql = "SELECT code FROM delivery ORDER BY code DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitDeliveryCode(resultSet.getString(1));
        }
        return splitDeliveryCode(null);
    }

    private static String splitDeliveryCode(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("D");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "D" + id;
        }
        return "D1";
    }
}
