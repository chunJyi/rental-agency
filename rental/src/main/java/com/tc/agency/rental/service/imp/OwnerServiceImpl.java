package com.tc.agency.rental.service.imp;

import com.tc.agency.rental.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OwnerServiceImpl {

    java.util.List<Owner> getAllOwners();

    Page<Owner> getOwners(Pageable pageable);

    Owner getOwner(UUID uuid);

    Owner createOwner(Owner owner);

    Owner updateOwner(UUID uuid, Owner owner);

    void deleteOwner(UUID uuid);
}
