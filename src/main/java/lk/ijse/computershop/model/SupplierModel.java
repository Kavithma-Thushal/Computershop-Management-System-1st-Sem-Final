package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Supply;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static String getNextSupplyId() throws SQLException {
        String sql = "SELECT id FROM suppliers ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitSupplyId(resultSet.getString(1));
        }
        return splitSupplyId(null);
    }

    private static String splitSupplyId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("S00");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "S00" + id;
        }
        return "S001";
    }

    public static boolean save(String supplierId, String name,String contact,String address) throws SQLException {
        String sql = "INSERT INTO suppliers VALUES(?, ?,?,?)";
        Integer affectedRows = CrudUtil.execute(sql, supplierId, name,contact,address);
        return affectedRows > 0;
    }

    public static List<Supply> getAll() throws SQLException {

        List<Supply> supplyList = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Supply supply = new Supply(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            supplyList.add(supply);
        }
        return supplyList;
    }
}
