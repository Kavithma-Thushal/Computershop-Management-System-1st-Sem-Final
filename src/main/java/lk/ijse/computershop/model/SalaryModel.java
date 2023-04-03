package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Item;
import lk.ijse.computershop.dto.Salary;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.SQLException;

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
}
