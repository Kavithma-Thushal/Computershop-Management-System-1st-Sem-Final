package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.CartDTO;
import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static int save(Item item) throws SQLException {
        String sql = "INSERT INTO items VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                item.getCode(),
                item.getDescription(),
                item.getUnitprice(),
                item.getQtyonhand()
        );
    }

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

    public static int update(Item item) throws SQLException {

        String sql = "UPDATE items SET description=? , unitPrice=? , qtyOnHand=? WHERE code=?";
        return CrudUtil.execute(
                sql,
                item.getDescription(),
                item.getUnitprice(),
                item.getQtyonhand(),
                item.getCode()
        );
    }

    public static int delete(String code) throws SQLException {
        String sql = "DELETE FROM items WHERE code=?";
        return CrudUtil.execute(sql, code);
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

    public static List<String> loadCodes() throws SQLException {
        String sql = "SELECT code FROM items";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<String> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Item searchById(String code) throws SQLException {
        String sql = "SELECT * FROM items WHERE code = ?";
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

    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CartDTO dto) throws SQLException {
        String sql = "UPDATE items SET qtyOnHand = (qtyOnHand - ?) WHERE code = ?";
        Integer affectedRows = CrudUtil.execute(sql, dto.getQty(), dto.getCode());
        return affectedRows > 0;
    }
}
