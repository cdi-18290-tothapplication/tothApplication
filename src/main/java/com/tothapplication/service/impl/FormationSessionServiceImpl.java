package com.tothapplication.service.impl;

import com.tothapplication.service.FormationSessionService;
import com.tothapplication.domain.FormationSession;
import com.tothapplication.repository.FormationSessionRepository;
import com.tothapplication.service.dto.FormationSessionDTO;
import com.tothapplication.service.mapper.FormationSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FormationSession}.
 */
@Service
@Transactional
public class FormationSessionServiceImpl implements FormationSessionService {

    private final Logger log = LoggerFactory.getLogger(FormationSessionServiceImpl.class);

    private final FormationSessionRepository formationSessionRepository;

    private final FormationSessionMapper formationSessionMapper;

    public FormationSessionServiceImpl(FormationSessionRepository formationSessionRepository, FormationSessionMapper formationSessionMapper) {
        this.formationSessionRepository = formationSessionRepository;
        this.formationSessionMapper = formationSessionMapper;
    }

    /**
     * Save a formationSession.
     *
     * @param formationSessionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormationSessionDTO save(FormationSessionDTO formationSessionDTO) {
        log.debug("Request to save FormationSession : {}", formationSessionDTO);
        FormationSession formationSession = formationSessionMapper.toEntity(formationSessionDTO);
        formationSession = formationSessionRepository.save(formationSession);
        return formationSessionMapper.toDto(formationSession);
    }

    /**
     * Get all the formationSessions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormationSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormationSessions");
        return formationSessionRepository.findAll(pageable)
            .map(formationSessionMapper::toDto);
    }

    /**
     * Get all the formationSessions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FormationSessionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return formationSessionRepository.findAllWithEagerRelationships(pageable).map(formationSessionMapper::toDto);
    }
    

    /**
     * Get one formationSession by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormationSessionDTO> findOne(Long id) {
        log.debug("Request to get FormationSession : {}", id);
        return formationSessionRepository.findOneWithEagerRelationships(id)
            .map(formationSessionMapper::toDto);
    }

    /**
     * Delete the formationSession by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormationSession : {}", id);
        formationSessionRepository.deleteById(id);
    }
}
