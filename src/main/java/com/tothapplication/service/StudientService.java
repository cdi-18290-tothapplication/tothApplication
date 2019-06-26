package com.tothapplication.service;

import com.tothapplication.service.dto.StudientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.Studient}.
 */
public interface StudientService {

    /**
     * Save a studient.
     *
     * @param studientDTO the entity to save.
     * @return the persisted entity.
     */
    StudientDTO save(StudientDTO studientDTO);

    /**
     * Get all the studients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudientDTO> findAll(Pageable pageable);
    /**
     * Get all the StudientDTO where Evaluation is {@code null}.
     *
     * @return the list of entities.
     */
    List<StudientDTO> findAllWhereEvaluationIsNull();


    /**
     * Get the "id" studient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudientDTO> findOne(Long id);

    /**
     * Delete the "id" studient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
