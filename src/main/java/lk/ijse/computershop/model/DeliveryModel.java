package lk.ijse.computershop.model;

import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryModel {

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
            String[] strings = currentId.split("D00");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "D00" + id;
        }
        return "D001";
    }
}
