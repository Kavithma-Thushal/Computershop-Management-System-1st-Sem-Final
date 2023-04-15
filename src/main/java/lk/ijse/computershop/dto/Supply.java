package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Supply {
    private String supplierId;
    private String supplyDate;
    private String name;
    private String contact;
    private String address;
    private String itemCode;
    private String itemDescription;
    private String supplyQty;
}
