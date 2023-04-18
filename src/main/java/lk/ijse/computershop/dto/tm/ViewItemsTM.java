package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewItemsTM {
    private String code;
    private String description;
    private Double unitprice;
    private Integer qtyonhand;
}
