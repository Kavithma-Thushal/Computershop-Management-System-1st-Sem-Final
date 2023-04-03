package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Orders {
    private String id;
    private String customerid;
    private Integer qty;
    private String datetime;
}
