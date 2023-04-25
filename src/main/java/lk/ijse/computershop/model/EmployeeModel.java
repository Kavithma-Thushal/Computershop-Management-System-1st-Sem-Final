package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Employee;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static int save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                employee.getId(),
                employee.getName(),
                employee.getContact(),
                employee.getJobRole(),
                employee.getUsername(),
                employee.getPassword()
        );

    }

    public static Employee search(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id=?";

        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()) {
            return new Employee(
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

    public static int update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name=? , contact=? , jobRole=? , username=? , password=? WHERE id=?";

        return CrudUtil.execute(
                sql,
                employee.getName(),
                employee.getContact(),
                employee.getJobRole(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getId()
        );
    }

    public static int delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE id=?";
        return CrudUtil.execute(sql, id);
    }

    public static List<Employee> getAll() throws SQLException {

        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static String getNextEmployeeId() throws SQLException {
        String sql = "SELECT id FROM employee ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private static String splitEmployeeId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("E");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "E" + String.format("%02d", id);
        }
        return "E01";
    }

    public static List<String> loadIds() throws SQLException {
        String sql = "SELECT id FROM employee ORDER BY id ASC";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<String> data = new ArrayList<>();
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Employee searchById(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, employeeId);

        if (resultSet.next()) {
            return new Employee(
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
}
