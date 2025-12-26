package com.tc.agency.rental.service.imp;

import com.tc.agency.rental.domain.Property;
import com.tc.agency.rental.dto.property.PropertyCreateRequest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.UUID;

public interface PropertyServiceImpl {

    Property createProperty(PropertyCreateRequest request);

    Page<Property> listAvailableProperties(Pageable pageable);

    Property getProperty(UUID uuid);

    void deleteProperty(UUID uuid);
}
