package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.InterventionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Intervention} and its DTO {@link InterventionDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrainerMapper.class, FormationSessionMapper.class})
public interface InterventionMapper extends EntityMapper<InterventionDTO, Intervention> {

    @Mapping(source = "trainer.id", target = "trainerId")
    @Mapping(source = "formationSession.id", target = "formationSessionId")
    InterventionDTO toDto(Intervention intervention);

    @Mapping(source = "trainerId", target = "trainer")
    @Mapping(source = "formationSessionId", target = "formationSession")
    Intervention toEntity(InterventionDTO interventionDTO);

    default Intervention fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intervention intervention = new Intervention();
        intervention.setId(id);
        return intervention;
    }
}
