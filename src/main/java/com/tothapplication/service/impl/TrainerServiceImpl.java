package com.tothapplication.service.impl;

import com.tothapplication.service.TrainerService;
import com.tothapplication.domain.Trainer;
import com.tothapplication.repository.TrainerRepository;
import com.tothapplication.service.dto.TrainerDTO;
import com.tothapplication.service.mapper.TrainerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Trainer}.
 */
@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final Logger log = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;

    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    /**
     * Save a trainer.
     *
     * @param trainerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrainerDTO save(TrainerDTO trainerDTO) {
        log.debug("Request to save Trainer : {}", trainerDTO);
        Trainer trainer = trainerMapper.toEntity(trainerDTO);
        trainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(trainer);
    }

    /**
     * Get all the trainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainers");
        return trainerRepository.findAll(pageable)
            .map(trainerMapper::toDto);
    }



    /**
    *  Get all the trainers where Evaluation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TrainerDTO> findAllWhereEvaluationIsNull() {
        log.debug("Request to get all trainers where Evaluation is null");
        return StreamSupport
            .stream(trainerRepository.findAll().spliterator(), false)
            .filter(trainer -> trainer.getEvaluation() == null)
            .map(trainerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainerDTO> findOne(Long id) {
        log.debug("Request to get Trainer : {}", id);
        return trainerRepository.findById(id)
            .map(trainerMapper::toDto);
    }

    /**
     * Delete the trainer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trainer : {}", id);
        trainerRepository.deleteById(id);
    }
}
