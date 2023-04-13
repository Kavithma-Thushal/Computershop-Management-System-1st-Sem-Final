package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Custombuilds;
import lk.ijse.computershop.dto.Order;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BuildDetailsModel {

    public static boolean save(String orderId, List<Order> orderList, LocalDate date) throws SQLException {
        for (Order orderDetails : orderList) {
            if (!save(orderId, orderDetails,LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String orderId, Order orderDetails, LocalDate date) throws SQLException {
        String sql = "INSERT INTO order_details VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, orderId, orderDetails.getCode(), orderDetails.getQty(), Date.valueOf(date));
        return affectedRows > 0;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean saveBuild(String orderId, List<Custombuilds> custombuildsList, LocalDate date) throws SQLException {
        for (Custombuilds custombuilds : custombuildsList) {
            if (!saveBuild(orderId, custombuilds,LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveBuild(String orderId, Custombuilds custombuilds, LocalDate date) throws SQLException {
        String sql = "INSERT INTO build_details VALUES(?, ?, ?, ?)";
        Integer affectedRows = CrudUtil.execute(sql, orderId, custombuilds.getCode(), custombuilds.getQty(), Date.valueOf(date));
        return affectedRows > 0;
    }
}
