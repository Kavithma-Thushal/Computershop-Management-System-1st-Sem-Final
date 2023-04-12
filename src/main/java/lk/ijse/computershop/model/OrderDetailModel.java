package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.CartDTO;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public static boolean save(String oId, List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            if (!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, CartDTO dto) throws SQLException {
        String sql = "INSERT INTO order_details(orderId, itemCode, qty, unitPrice)" + "VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, oId, dto.getCode(), dto.getQty(), dto.getUnitPrice());
        return affectedRows > 0;
    }
}
