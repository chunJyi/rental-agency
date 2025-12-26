package com.tc.agency.rental.repository;

import com.tc.agency.rental.domain.Property;
import com.tc.agency.rental.domain.enums.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
}
