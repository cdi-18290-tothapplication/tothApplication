package com.tothapplication.service;

import com.tothapplication.service.dto.CCPDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.CCP}.
 */
public interface CCPService {

    /**
     * Save a cCP.
     *
     * @param cCPDTO the entity to save.
     * @return the persisted entity.
     */
    CCPDTO save(CCPDTO cCPDTO);

    /**
     * Get all the cCPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CCPDTO> findAll(Pageable pageable);

    /**
     * Get all the cCPS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CCPDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" cCP.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CCPDTO> findOne(Long id);

    /**
     * Delete the "id" cCP.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
