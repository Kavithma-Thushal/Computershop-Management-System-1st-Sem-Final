package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Supplier;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static int save(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO suppliers VALUES (?,?,?,?)";

        return CrudUtil.execute(
                sql,
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress()
        );
    }

    public static Supplier search(String id) throws SQLException {

        String sql = "SELECT * FROM suppliers WHERE id=?";

        ResultSet resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static int update(Supplier supplier) throws SQLException {

        String sql = "UPDATE suppliers SET name=? , contact=? , address=? WHERE id=?";

        return CrudUtil.execute(
                sql,
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress(),
                supplier.getId()
        );
    }

    public static int delete(String id) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE id=?";
        return CrudUtil.execute(sql, id);
    }

    public static List<Supplier> getAll() throws SQLException {

        List<Supplier> supplierList = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            Supplier supplier= new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            supplierList.add(supplier);
        }
        return supplierList;
    }
}
