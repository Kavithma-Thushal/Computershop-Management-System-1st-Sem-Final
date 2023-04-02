package lk.ijse.computershop.util;

import lk.ijse.computershop.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... args) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject((i + 1), args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) preparedStatement.executeQuery();    //resultset
        } else {
            return (T) (Integer) preparedStatement.executeUpdate();     //integer
        }

    }
}