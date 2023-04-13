package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private String code;
    private Integer qty;
    private Double unitPrice;
}
