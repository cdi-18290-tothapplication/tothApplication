package com.tothapplication.service.impl;

import com.tothapplication.service.StudientService;
import com.tothapplication.domain.Studient;
import com.tothapplication.repository.StudientRepository;
import com.tothapplication.service.dto.StudientDTO;
import com.tothapplication.service.mapper.StudientMapper;
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
 * Service Implementation for managing {@link Studient}.
 */
@Service
@Transactional
public class StudientServiceImpl implements StudientService {

    private final Logger log = LoggerFactory.getLogger(StudientServiceImpl.class);

    private final StudientRepository studientRepository;

    private final StudientMapper studientMapper;

    public StudientServiceImpl(StudientRepository studientRepository, StudientMapper studientMapper) {
        this.studientRepository = studientRepository;
        this.studientMapper = studientMapper;
    }

    /**
     * Save a studient.
     *
     * @param studientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudientDTO save(StudientDTO studientDTO) {
        log.debug("Request to save Studient : {}", studientDTO);
        Studient studient = studientMapper.toEntity(studientDTO);
        studient = studientRepository.save(studient);
        return studientMapper.toDto(studient);
    }

    /**
     * Get all the studients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Studients");
        return studientRepository.findAll(pageable)
            .map(studientMapper::toDto);
    }



    /**
    *  Get all the studients where Evaluation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<StudientDTO> findAllWhereEvaluationIsNull() {
        log.debug("Request to get all studients where Evaluation is null");
        return StreamSupport
            .stream(studientRepository.findAll().spliterator(), false)
            .filter(studient -> studient.getEvaluation() == null)
            .map(studientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one studient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudientDTO> findOne(Long id) {
        log.debug("Request to get Studient : {}", id);
        return studientRepository.findById(id)
            .map(studientMapper::toDto);
    }

    /**
     * Delete the studient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Studient : {}", id);
        studientRepository.deleteById(id);
    }
}
