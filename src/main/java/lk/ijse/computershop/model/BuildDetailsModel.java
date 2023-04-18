package lk.ijse.computershop.model;

import lk.ijse.computershop.dto.Custombuilds;
import lk.ijse.computershop.util.CrudUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BuildDetailsModel {

    public static boolean saveBuild(String buildCode, List<Custombuilds> buildsList, LocalDate date) throws SQLException {
        for (Custombuilds custombuilds : buildsList) {
            if (!saveBuild(buildCode, custombuilds, LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveBuild(String buildCode, Custombuilds custombuilds, LocalDate date) throws SQLException {
        String sql = "INSERT INTO build_details VALUES(?, ?, ?, ?,?)";
        Integer affectedRows = CrudUtil.execute(sql, buildCode, custombuilds.getCode(), custombuilds.getQty(), custombuilds.getTotal(), Date.valueOf(date));
        return affectedRows > 0;
    }
}
