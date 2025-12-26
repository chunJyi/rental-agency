package com.tc.agency.rental.controller;

import com.tc.agency.rental.domain.Property;
import com.tc.agency.rental.dto.property.PropertyCreateRequest;
import com.tc.agency.rental.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<Property> createProperty(@Valid @RequestBody PropertyCreateRequest request) {
        Property created = propertyService.createProperty(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Property>> listAvailableProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> result = propertyService.listAvailableProperties(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable UUID id) {
        Property property = propertyService.getProperty(id);
        return ResponseEntity.ok(property);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
