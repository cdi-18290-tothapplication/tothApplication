package com.tothapplication.service.mapper;

import com.tothapplication.domain.*;
import com.tothapplication.service.dto.StudientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Studient} and its DTO {@link StudientDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DocumentMapper.class})
public interface StudientMapper extends EntityMapper<StudientDTO, Studient> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "photo.id", target = "photoId")
    StudientDTO toDto(Studient studient);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "photoId", target = "photo")
    @Mapping(target = "evaluation", ignore = true)
    Studient toEntity(StudientDTO studientDTO);

    default Studient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Studient studient = new Studient();
        studient.setId(id);
        return studient;
    }
}
