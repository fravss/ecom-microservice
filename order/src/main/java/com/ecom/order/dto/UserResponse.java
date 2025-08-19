package com.ecom.order.dto;

import com.ecom.order.dto.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
