package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.SQLException;

public class CustomerModel {

    public static int save(Customer customer) throws SQLException {

        String sql = "INSERT INTO customers VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                customer.getId(),
                customer.getName(),
                customer.getNic(),
                customer.getEmail(),
                customer.getContact(),
                customer.getAddress()
        );

    }
}
