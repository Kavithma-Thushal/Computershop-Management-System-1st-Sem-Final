package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Customer;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static Customer search(String id) throws SQLException {

        String sql = "SELECT * FROM customers WHERE id=?";

        ResultSet resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new Customer(
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

    public static int update(Customer customer) throws SQLException {

        String sql = "UPDATE customers SET name=? , nic=? , email=? , contact=? , address=? WHERE id=?";

        return CrudUtil.execute(
                sql,
                customer.getName(),
                customer.getNic(),
                customer.getEmail(),
                customer.getContact(),
                customer.getAddress(),
                customer.getId()
        );
    }

    public static int delete(String id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id=?";
        return CrudUtil.execute(sql, id);
    }

    public static List<Customer> getAll() throws SQLException {

        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            customerList.add(customer);
        }
        return customerList;
    }

    public static List<String> loadIds() throws SQLException {
        String sql = "SELECT id FROM customers ORDER BY id ASC";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<String> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}
