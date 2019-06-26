package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.TrainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trainer} and its DTO {@link TrainerDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TrainerMapper extends EntityMapper<TrainerDTO, Trainer> {

    @Mapping(source = "user.id", target = "userId")
    TrainerDTO toDto(Trainer trainer);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "evaluation", ignore = true)
    @Mapping(target = "interventions", ignore = true)
    @Mapping(target = "removeIntervention", ignore = true)
    Trainer toEntity(TrainerDTO trainerDTO);

    default Trainer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trainer trainer = new Trainer();
        trainer.setId(id);
        return trainer;
    }
}
