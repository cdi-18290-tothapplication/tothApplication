package com.tothapplication.service;

import com.tothapplication.service.dto.TrainerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.Trainer}.
 */
public interface TrainerService {

    /**
     * Save a trainer.
     *
     * @param trainerDTO the entity to save.
     * @return the persisted entity.
     */
    TrainerDTO save(TrainerDTO trainerDTO);

    /**
     * Get all the trainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainerDTO> findAll(Pageable pageable);
    /**
     * Get all the TrainerDTO where Evaluation is {@code null}.
     *
     * @return the list of entities.
     */
    List<TrainerDTO> findAllWhereEvaluationIsNull();


    /**
     * Get the "id" trainer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainerDTO> findOne(Long id);

    /**
     * Delete the "id" trainer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
