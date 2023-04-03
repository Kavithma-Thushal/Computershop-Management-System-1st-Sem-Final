package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Repair {
    private String code;
    private String employeeid;
    private String customerid;
    private String details;
    private String getdatetime;
    private String acceptdatetime;
}
