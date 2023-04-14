package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairModel {

    public static List<Repair> getAll() throws SQLException {

        List<Repair> repairList = new ArrayList<>();
        String sql = "SELECT * FROM repairs";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Repair repair = new Repair(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            repairList.add(repair);
        }
        return repairList;
    }

    public static Repair search(String code) throws SQLException {

        String sql = "SELECT * FROM repairs WHERE code=?";

        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Repair(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

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
