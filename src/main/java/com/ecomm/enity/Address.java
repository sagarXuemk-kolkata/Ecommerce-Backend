package com.ecomm.enity;

import com.ecomm.enity.enums.AddressType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
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



    // ------------------ Relationship --------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address(Long id, String street, String city, String state, String pinCode,
                   String country, AddressType addressType, Boolean isDefault, User user) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.country = country;
        this.addressType = addressType;
        this.isDefault = isDefault != null ? isDefault : false;
        this.user = user;
    }
}
