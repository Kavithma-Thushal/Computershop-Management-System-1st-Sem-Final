package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaryTM {
    private String code;
    private String employeeid;
    private Double amount;
    private String datetime;
}
