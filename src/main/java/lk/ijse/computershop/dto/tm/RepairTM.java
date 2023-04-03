package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepairTM {
    private String code;
    private String employeeid;
    private String customerid;
    private String details;
    private String getdatetime;
    private String acceptdatetime;
}
