package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersTM {
    private String id;
    private String customerid;
    private String description;
    private Integer qty;
    private String datetime;
}
