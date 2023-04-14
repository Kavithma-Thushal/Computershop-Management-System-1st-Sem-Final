package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Repair {
    private String code;
    private String customerId;
    private String employeeId;
    private String details;
    private String gettingDate;
    private String acceptingDate;
}
