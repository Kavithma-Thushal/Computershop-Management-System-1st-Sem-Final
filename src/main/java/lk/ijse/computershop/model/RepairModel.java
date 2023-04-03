package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Repair;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairModel {

    public static int save(Repair repair) throws SQLException {
        String sql = "INSERT INTO repairs VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                repair.getCode(),
                repair.getEmployeeid(),
                repair.getCustomerid(),
                repair.getDetails(),
                repair.getGetdatetime(),
                repair.getAcceptdatetime()
        );
    }

    public static Repair search(String code) throws SQLException {

        String sql = "SELECT * FROM repairs WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Repair(
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

    public static int update(Repair repair) throws SQLException {

        String sql = "UPDATE repairs SET employeeid=? , customerid=? , details=? , getdatetime=? , acceptdatetime=?  WHERE code=?";
        return CrudUtil.execute(
                sql,
                repair.getEmployeeid(),
                repair.getCustomerid(),
                repair.getDetails(),
                repair.getGetdatetime(),
                repair.getAcceptdatetime(),
                repair.getCode()
        );
    }

    public static int delete(String code) throws SQLException {
        String sql = "DELETE FROM repairs WHERE code=?";
        return CrudUtil.execute(sql, code);
    }

    public static List<Repair> getAll() throws SQLException {

        List<Repair> repairList = new ArrayList<>();
        String sql = "SELECT * FROM repairs";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Repair repair = new Repair(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            repairList.add(repair);
        }
        return repairList;
    }

}
