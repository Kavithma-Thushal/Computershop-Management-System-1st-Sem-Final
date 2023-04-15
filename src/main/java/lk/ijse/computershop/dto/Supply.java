package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Supply {
    private String id;
    private String name;
    private String contact;
    private String address;
}
