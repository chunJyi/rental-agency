package com.tc.agency.rental.controller;

import com.tc.agency.rental.domain.Owner;
import com.tc.agency.rental.dto.owner.OwnerCreateRequest;
import com.tc.agency.rental.dto.owner.OwnerResponse;
import com.tc.agency.rental.dto.owner.OwnerUpdateRequest;
import com.tc.agency.rental.mapper.OwnerMapper;
import com.tc.agency.rental.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {


    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping("/all")
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        List<OwnerResponse> list = ownerService.getAllOwners()
                .stream()
                .map(ownerMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<Page<OwnerResponse>> listOwners(Pageable pageable) {
        Page<Owner> page = ownerService.getOwners(pageable);
        Page<OwnerResponse> respPage = page.map(ownerMapper::toResponse);
        return ResponseEntity.ok(respPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getOwner(@PathVariable UUID id) {
        Owner o = ownerService.getOwner(id);
        return ResponseEntity.ok(ownerMapper.toResponse(o));
    }

    @PostMapping
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody OwnerCreateRequest request) {
        Owner entity = ownerMapper.toEntity(request);
        Owner created = ownerService.createOwner(entity);
        OwnerResponse resp = ownerMapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/owners/" + resp.getId())).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable UUID id, @Valid @RequestBody OwnerUpdateRequest request) {
        Owner o = ownerService.getOwner(id);
        // reuse mapper update
        ownerMapper.updateEntityFromDto(request, o);
        Owner updated = ownerService.updateOwner(id, o);
        return ResponseEntity.ok(ownerMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable UUID id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
