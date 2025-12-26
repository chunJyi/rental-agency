package com.tc.agency.rental.service;

import com.tc.agency.rental.domain.Property;
import com.tc.agency.rental.dto.property.PropertyCreateRequest;
import com.tc.agency.rental.repository.OwnerRepository;
import com.tc.agency.rental.repository.PropertyRepository;
import com.tc.agency.rental.service.imp.PropertyServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService implements PropertyServiceImpl {

    private final PropertyRepository propertyRepository;

    private final OwnerRepository ownerRepository;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public Property createProperty(PropertyCreateRequest request) {
        ownerRepository.findById(request.get)
        return null;
    }

    @Override
    public Page<Property> listAvailableProperties(Pageable pageable) {
        return null;
    }

    @Override
    public Property getProperty(UUID uuid) {
        return null;
    }

    @Override
    public void deleteProperty(UUID uuid) {

    }
}
