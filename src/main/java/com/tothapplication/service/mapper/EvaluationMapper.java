package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.EvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evaluation} and its DTO {@link EvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudientMapper.class, TrainerMapper.class, FormationSessionMapper.class})
public interface EvaluationMapper extends EntityMapper<EvaluationDTO, Evaluation> {

    @Mapping(source = "studient.id", target = "studientId")
    @Mapping(source = "trainer.id", target = "trainerId")
    @Mapping(source = "formationSession.id", target = "formationSessionId")
    EvaluationDTO toDto(Evaluation evaluation);

    @Mapping(source = "studientId", target = "studient")
    @Mapping(source = "trainerId", target = "trainer")
    @Mapping(source = "formationSessionId", target = "formationSession")
    Evaluation toEntity(EvaluationDTO evaluationDTO);

    default Evaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setId(id);
        return evaluation;
    }
}
