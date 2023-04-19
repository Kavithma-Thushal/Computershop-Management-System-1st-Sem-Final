package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Salary;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {

    public static List<Salary> getAll() throws SQLException {

        List<Salary> salaryList = new ArrayList<>();
        String sql = "SELECT * FROM salary";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Salary salary= new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            salaryList.add(salary);
        }
        return salaryList;
    }

    public static Salary search(String code) throws SQLException {

        String sql = "SELECT * FROM salary WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static String getNextSalaryCode() throws SQLException {
        String sql = "SELECT code FROM salary ORDER BY code DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitSalaryCode(resultSet.getString(1));
        }
        return splitSalaryCode(null);
    }

    private static String splitSalaryCode(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("S");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "S" + id;
        }
        return "S1";
    }
}
