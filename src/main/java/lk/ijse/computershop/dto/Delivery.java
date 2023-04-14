package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Delivery {
    private String code;
    private String customerId;
    private String employeeId;
    private String orderId;
    private String location;
    private String date;
}
