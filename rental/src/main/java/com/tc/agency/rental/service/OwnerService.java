package com.tc.agency.rental.service;

import com.tc.agency.rental.domain.Owner;
import com.tc.agency.rental.exception.BusinessException;
import com.tc.agency.rental.repository.OwnerRepository;
import com.tc.agency.rental.service.imp.OwnerServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService implements OwnerServiceImpl {

    private final OwnerRepository ownerRepository;

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Page<Owner> getOwners(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    @Override
    public Owner getOwner(UUID uuid) {
        return ownerRepository.findById(uuid).orElseThrow(() -> new BusinessException("Owner not found"));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public Owner createOwner(Owner owner) {
        // simple create, additional validation can be added
        return ownerRepository.save(owner);
    }

    @Override
    public Owner updateOwner(UUID uuid, Owner owner) {
        Owner existing = ownerRepository.findById(uuid).orElseThrow(() -> new BusinessException("Owner not found"));
        existing.setName(owner.getName());
        existing.setPhone(owner.getPhone());
        existing.setAddress(owner.getAddress());
        return ownerRepository.save(existing);
    }

    @Override
    public void deleteOwner(UUID uuid) {
        Owner existing = ownerRepository.findById(uuid).orElseThrow(() -> new BusinessException("Owner not found"));
        ownerRepository.delete(existing);
    }
}
