package com.tc.agency.rental.domain;

import com.tc.agency.rental.domain.enums.PropertyStatus;
import com.tc.agency.rental.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "properties")
@Getter
@Setter
public class Property extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    private String location;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.AVAILABLE;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Owner owner;
}
