package com.ecom.user.dto;

import com.ecom.user.model.UserRole;
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
