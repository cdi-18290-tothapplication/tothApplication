package com.tothapplication.service;

import com.tothapplication.service.dto.FormationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.Formation}.
 */
public interface FormationService {

    /**
     * Save a formation.
     *
     * @param formationDTO the entity to save.
     * @return the persisted entity.
     */
    FormationDTO save(FormationDTO formationDTO);

    /**
     * Get all the formations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormationDTO> findAll(Pageable pageable);

    /**
     * Get all the formations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<FormationDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" formation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormationDTO> findOne(Long id);

    /**
     * Delete the "id" formation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
