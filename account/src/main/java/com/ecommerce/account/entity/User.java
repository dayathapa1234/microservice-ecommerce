package com.ecommerce.account.entity;

import com.ecommerce.account.enums.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @OneToMany
    private Set<Address> addresses =  new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_coupons", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "coupon_id")
    private Set<Long> usedCouponIds = new HashSet<>();

}
