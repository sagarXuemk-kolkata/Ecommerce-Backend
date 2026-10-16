package com.ecomm.enity;

import com.ecomm.enity.enums.AddressType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private AddressType addressType;
    private Boolean isDefault=false;


}
