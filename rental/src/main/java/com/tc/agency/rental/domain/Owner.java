package com.tc.agency.rental.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "owners")
@Getter
@Setter
public class Owner extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String phone;
    private String address;
}
