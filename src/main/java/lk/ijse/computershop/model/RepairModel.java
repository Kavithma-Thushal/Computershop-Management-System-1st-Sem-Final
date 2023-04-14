package lk.ijse.computershop.model;

import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepairModel {

    public static String getNextRepairCode() throws SQLException {
        String sql = "SELECT code FROM repairs ORDER BY code DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitRepairCode(resultSet.getString(1));
        }
        return splitRepairCode(null);
    }

    private static String splitRepairCode(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("R00");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "R00" + id;
        }
        return "R001";
    }
}
