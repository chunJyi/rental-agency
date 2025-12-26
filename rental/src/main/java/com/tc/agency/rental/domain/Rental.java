package com.tc.agency.rental.domain;

import com.tc.agency.rental.domain.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Getter
@Setter
public class Rental extends BaseEntity {


    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.ACTIVE;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tenant tenant;
}
