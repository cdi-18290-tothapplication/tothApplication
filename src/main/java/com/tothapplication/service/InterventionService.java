package com.tothapplication.service;

import com.tothapplication.service.dto.InterventionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.Intervention}.
 */
public interface InterventionService {

    /**
     * Save a intervention.
     *
     * @param interventionDTO the entity to save.
     * @return the persisted entity.
     */
    InterventionDTO save(InterventionDTO interventionDTO);

    /**
     * Get all the interventions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InterventionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" intervention.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterventionDTO> findOne(Long id);

    /**
     * Delete the "id" intervention.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
