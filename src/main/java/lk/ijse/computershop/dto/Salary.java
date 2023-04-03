package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Salary {
    private String code;
    private String employeeid;
    private Double amount;
    private String datetime;
}
