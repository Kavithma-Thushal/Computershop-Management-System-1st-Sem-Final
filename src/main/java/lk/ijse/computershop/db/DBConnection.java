package lk.ijse.computershop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbconnection;
    private Connection connection;

    private DBConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bashicomputershop", "root", "1234");
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbconnection == null) {
            return new DBConnection();
        } else {
            return dbconnection;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
