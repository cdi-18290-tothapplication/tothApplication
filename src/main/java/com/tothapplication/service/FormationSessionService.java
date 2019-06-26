package com.tothapplication.service;

import com.tothapplication.service.dto.FormationSessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.FormationSession}.
 */
public interface FormationSessionService {

    /**
     * Save a formationSession.
     *
     * @param formationSessionDTO the entity to save.
     * @return the persisted entity.
     */
    FormationSessionDTO save(FormationSessionDTO formationSessionDTO);

    /**
     * Get all the formationSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormationSessionDTO> findAll(Pageable pageable);

    /**
     * Get all the formationSessions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<FormationSessionDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" formationSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormationSessionDTO> findOne(Long id);

    /**
     * Delete the "id" formationSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
