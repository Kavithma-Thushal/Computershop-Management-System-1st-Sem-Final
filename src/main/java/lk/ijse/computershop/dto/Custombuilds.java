package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Custombuilds {
    private String code;
    private String customerid;
    private String description;
    private String datetime;
}
