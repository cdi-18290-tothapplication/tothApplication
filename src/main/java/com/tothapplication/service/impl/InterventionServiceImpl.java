package com.tothapplication.service.impl;

import com.tothapplication.service.InterventionService;
import com.tothapplication.domain.Intervention;
import com.tothapplication.repository.InterventionRepository;
import com.tothapplication.service.dto.InterventionDTO;
import com.tothapplication.service.mapper.InterventionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Intervention}.
 */
@Service
@Transactional
public class InterventionServiceImpl implements InterventionService {

    private final Logger log = LoggerFactory.getLogger(InterventionServiceImpl.class);

    private final InterventionRepository interventionRepository;

    private final InterventionMapper interventionMapper;

    public InterventionServiceImpl(InterventionRepository interventionRepository, InterventionMapper interventionMapper) {
        this.interventionRepository = interventionRepository;
        this.interventionMapper = interventionMapper;
    }

    /**
     * Save a intervention.
     *
     * @param interventionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InterventionDTO save(InterventionDTO interventionDTO) {
        log.debug("Request to save Intervention : {}", interventionDTO);
        Intervention intervention = interventionMapper.toEntity(interventionDTO);
        intervention = interventionRepository.save(intervention);
        return interventionMapper.toDto(intervention);
    }

    /**
     * Get all the interventions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InterventionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interventions");
        return interventionRepository.findAll(pageable)
            .map(interventionMapper::toDto);
    }


    /**
     * Get one intervention by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InterventionDTO> findOne(Long id) {
        log.debug("Request to get Intervention : {}", id);
        return interventionRepository.findById(id)
            .map(interventionMapper::toDto);
    }

    /**
     * Delete the intervention by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intervention : {}", id);
        interventionRepository.deleteById(id);
    }
}
