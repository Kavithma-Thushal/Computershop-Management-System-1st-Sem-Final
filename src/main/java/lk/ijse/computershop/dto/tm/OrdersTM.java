package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersTM {
    private String id;
    private String customerid;
    private Integer qty;
    private String datetime;
}
