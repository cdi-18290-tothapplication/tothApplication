package com.tothapplication.service.impl;

import com.tothapplication.service.CCPService;
import com.tothapplication.domain.CCP;
import com.tothapplication.repository.CCPRepository;
import com.tothapplication.service.dto.CCPDTO;
import com.tothapplication.service.mapper.CCPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCP}.
 */
@Service
@Transactional
public class CCPServiceImpl implements CCPService {

    private final Logger log = LoggerFactory.getLogger(CCPServiceImpl.class);

    private final CCPRepository cCPRepository;

    private final CCPMapper cCPMapper;

    public CCPServiceImpl(CCPRepository cCPRepository, CCPMapper cCPMapper) {
        this.cCPRepository = cCPRepository;
        this.cCPMapper = cCPMapper;
    }

    /**
     * Save a cCP.
     *
     * @param cCPDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CCPDTO save(CCPDTO cCPDTO) {
        log.debug("Request to save CCP : {}", cCPDTO);
        CCP cCP = cCPMapper.toEntity(cCPDTO);
        cCP = cCPRepository.save(cCP);
        return cCPMapper.toDto(cCP);
    }

    /**
     * Get all the cCPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CCPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCPS");
        return cCPRepository.findAll(pageable)
            .map(cCPMapper::toDto);
    }

    /**
     * Get all the cCPS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CCPDTO> findAllWithEagerRelationships(Pageable pageable) {
        return cCPRepository.findAllWithEagerRelationships(pageable).map(cCPMapper::toDto);
    }
    

    /**
     * Get one cCP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CCPDTO> findOne(Long id) {
        log.debug("Request to get CCP : {}", id);
        return cCPRepository.findOneWithEagerRelationships(id)
            .map(cCPMapper::toDto);
    }

    /**
     * Delete the cCP by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCP : {}", id);
        cCPRepository.deleteById(id);
    }
}
