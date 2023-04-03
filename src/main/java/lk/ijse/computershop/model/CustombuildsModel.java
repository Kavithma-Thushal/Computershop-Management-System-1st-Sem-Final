package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Custombuilds;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustombuildsModel {

    public static int save(Custombuilds custombuilds) throws SQLException {
        String sql = "INSERT INTO custombuilds VALUES (?,?,?,?)";
        return CrudUtil.execute(
                sql,
                custombuilds.getCode(),
                custombuilds.getCustomerid(),
                custombuilds.getDescription(),
                custombuilds.getDatetime()
        );
    }

    public static Custombuilds search(String code) throws SQLException {

        String sql = "SELECT * FROM custombuilds WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new Custombuilds(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static int update(Custombuilds custombuilds) throws SQLException {

        String sql = "UPDATE custombuilds SET customerid=? , description=? , datetime=? WHERE code=?";
        return CrudUtil.execute(
                sql,
                custombuilds.getCustomerid(),
                custombuilds.getDescription(),
                custombuilds.getDatetime(),
                custombuilds.getCode()
        );
    }

    public static int delete(String code) throws SQLException {
        String sql = "DELETE FROM custombuilds WHERE code=?";
        return CrudUtil.execute(sql, code);
    }

    public static List<Custombuilds> getAll() throws SQLException {

        List<Custombuilds> custombuildsList = new ArrayList<>();
        String sql = "SELECT * FROM custombuilds";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Custombuilds custombuilds= new Custombuilds(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            custombuildsList.add(custombuilds);
        }
        return custombuildsList;
    }
}
