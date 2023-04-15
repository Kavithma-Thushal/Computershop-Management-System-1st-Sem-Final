package lk.ijse.computershop.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplyTM {
    private String supplierId;
    private String supplyDate;
    private String name;
    private String contact;
    private String address;
    private String itemCode;
    private String itemDescription;
    private String supplyQty;
}
