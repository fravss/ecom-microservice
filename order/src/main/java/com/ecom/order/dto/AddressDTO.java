package com.ecom.order.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
