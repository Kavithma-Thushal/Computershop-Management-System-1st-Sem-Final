package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Delivery;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModel {

    public static int save(Delivery delivery) throws SQLException {
        String sql = "INSERT INTO delivery VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                delivery.getCode(),
                delivery.getEmployeeid(),
                delivery.getCustomerid(),
                delivery.getOrderid(),
                delivery.getDetails(),
                delivery.getLocation()
        );
    }

    public static Delivery search(String code) throws SQLException {

        String sql = "SELECT * FROM delivery WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Delivery(
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

    public static int update(Delivery delivery) throws SQLException {

        String sql = "UPDATE delivery SET employeeid=? , customerid=? , orderid=? , details=? , location=?  WHERE code=?";
        return CrudUtil.execute(
                sql,
                delivery.getEmployeeid(),
                delivery.getCustomerid(),
                delivery.getOrderid(),
                delivery.getDetails(),
                delivery.getLocation(),
                delivery.getCode()
        );
    }

    public static int delete(String code) throws SQLException {
        String sql = "DELETE FROM delivery WHERE code=?";
        return CrudUtil.execute(sql, code);
    }

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

}
