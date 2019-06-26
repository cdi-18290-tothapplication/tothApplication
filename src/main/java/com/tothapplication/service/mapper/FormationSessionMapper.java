package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.FormationSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormationSession} and its DTO {@link FormationSessionDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudientMapper.class, DocumentMapper.class, FormationMapper.class})
public interface FormationSessionMapper extends EntityMapper<FormationSessionDTO, FormationSession> {

    @Mapping(source = "formation.id", target = "formationId")
    FormationSessionDTO toDto(FormationSession formationSession);

    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "removeEvaluation", ignore = true)
    @Mapping(target = "removeStudients", ignore = true)
    @Mapping(target = "removeDocuments", ignore = true)
    @Mapping(source = "formationId", target = "formation")
    @Mapping(target = "interventions", ignore = true)
    @Mapping(target = "removeIntervention", ignore = true)
    FormationSession toEntity(FormationSessionDTO formationSessionDTO);

    default FormationSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormationSession formationSession = new FormationSession();
        formationSession.setId(id);
        return formationSession;
    }
}
