package lk.ijse.computershop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Customer {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String contact;
    private String address;
}
