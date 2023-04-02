package lk.ijse.computershop.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTM {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String contact;
    private String address;
}
