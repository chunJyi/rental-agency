package com.tc.agency.rental.mapper;

import com.tc.agency.rental.domain.Owner;
import com.tc.agency.rental.dto.owner.OwnerCreateRequest;
import com.tc.agency.rental.dto.owner.OwnerResponse;
import com.tc.agency.rental.dto.owner.OwnerUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnerMapper {

    public Owner toEntity(OwnerCreateRequest req) {
        if (req == null) return null;
        Owner o = new Owner();
        o.setName(req.getName());
        o.setPhone(req.getPhone());
        o.setAddress(req.getAddress());
        return o;
    }

    public OwnerResponse toResponse(Owner o) {
        if (o == null) return null;
        OwnerResponse r = new OwnerResponse();
        r.setId(o.getId());
        r.setName(o.getName());
        r.setPhone(o.getPhone());
        r.setAddress(o.getAddress());
        r.setCreatedAt(o.getCreatedAt());
        r.setUpdatedAt(o.getUpdatedAt());
        return r;
    }

    public void updateEntityFromDto(OwnerUpdateRequest dto, Owner entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
    }
}
