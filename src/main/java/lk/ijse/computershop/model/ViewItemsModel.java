package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewItemsModel {

    public static Item search(String code) throws SQLException {

        String sql = "SELECT * FROM items WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public static List<Item> getAll() throws SQLException {

        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM items";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
            itemList.add(item);
        }
        return itemList;
    }
}
