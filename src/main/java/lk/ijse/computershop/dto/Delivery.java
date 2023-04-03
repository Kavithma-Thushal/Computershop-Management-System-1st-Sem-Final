package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Delivery {
    private String code;
    private String employeeid;
    private String customerid;
    private String orderid;
    private String details;
    private String location;
}
