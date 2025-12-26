package com.tc.agency.rental.dto.property;

import com.tc.agency.rental.domain.enums.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class PropertyCreateRequest {

    @NotBlank(message = "Property title is required")
    private String title;
    @NotBlank(message = "Property type is required")
    private PropertyType propertyType;
    @NotBlank(message = "Location is required")
    private String location;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater then Zero")
    private BigDecimal price;
    @NotNull(message = "Owner ID is required")
    private UUID ownerId;
}
