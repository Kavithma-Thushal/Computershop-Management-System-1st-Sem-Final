package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewItems {
    private String code;
    private String description;
    private Double unitprice;
    private Integer qtyonhand;
}
