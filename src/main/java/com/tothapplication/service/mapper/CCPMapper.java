package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.CCPDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCP} and its DTO {@link CCPDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface CCPMapper extends EntityMapper<CCPDTO, CCP> {


    @Mapping(target = "removeDocuments", ignore = true)

    default CCP fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCP cCP = new CCP();
        cCP.setId(id);
        return cCP;
    }
}
