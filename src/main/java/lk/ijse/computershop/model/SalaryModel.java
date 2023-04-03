package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Salary;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {

    public static int save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                salary.getCode(),
                salary.getEmployeeid(),
                salary.getAmount(),
                salary.getDatetime()
        );
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

    public static int update(Salary salary) throws SQLException {

        String sql = "UPDATE salary SET employeeid=? , amount=? , datetime=? WHERE code=?";
        return CrudUtil.execute(
                sql,
                salary.getEmployeeid(),
                salary.getAmount(),
                salary.getDatetime(),
                salary.getCode()
        );
    }

    public static int delete(String code) throws SQLException {
        String sql = "DELETE FROM salary WHERE code=?";
        return CrudUtil.execute(sql, code);
    }

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
}
