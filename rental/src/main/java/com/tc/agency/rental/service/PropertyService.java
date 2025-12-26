package com.tc.agency.rental.service;

import com.tc.agency.rental.domain.Owner;
import com.tc.agency.rental.domain.Property;
import com.tc.agency.rental.domain.enums.PropertyStatus;
import com.tc.agency.rental.dto.property.PropertyCreateRequest;
import com.tc.agency.rental.exception.BusinessException;
import com.tc.agency.rental.repository.OwnerRepository;
import com.tc.agency.rental.repository.PropertyRepository;
import com.tc.agency.rental.service.imp.PropertyServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
        Owner owner = ownerRepository.findById(request.getOwnerId()).orElseThrow(() -> new BusinessException("Owner not found"));
        Property property = new Property();
        property.setTitle(request.getTitle());
        // map DTO propertyType to domain enum
        property.setPropertyType(request.getPropertyType());
        property.setLocation(request.getLocation());
        property.setPrice(request.getPrice());
        property.setOwner(owner);
        property.setStatus(PropertyStatus.AVAILABLE);
        return propertyRepository.save(property);
    }

    @Override
    public Page<Property> listAvailableProperties(Pageable pageable) {
        return propertyRepository.findByStatus(PropertyStatus.AVAILABLE, pageable);
    }

    @Override
    public Property getProperty(UUID uuid) {
        return propertyRepository.findById(uuid).orElseThrow(() -> new BusinessException("Property not found"));
    }

    @Override
    public void deleteProperty(UUID uuid) {
        Property property = propertyRepository.findById(uuid).orElseThrow(() -> new BusinessException("Property not found"));
        propertyRepository.delete(property);
    }
}
