package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.FormationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Formation} and its DTO {@link FormationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CCPMapper.class})
public interface FormationMapper extends EntityMapper<FormationDTO, Formation> {


    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "removeSessions", ignore = true)
    @Mapping(target = "removeCcp", ignore = true)
    Formation toEntity(FormationDTO formationDTO);

    default Formation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Formation formation = new Formation();
        formation.setId(id);
        return formation;
    }
}
