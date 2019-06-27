package com.tothapplication.service;

import com.tothapplication.service.dto.DocumentDTO;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.tothapplication.domain.Document}.
 */
public interface DocumentService {

    /**
     * Save a document.
     *
     * @param documentDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentDTO save(DocumentDTO documentDTO);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" document.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentDTO> findOne(Long id);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Stores de file relative to the "id" document.
     *
     * @param id the id of the entity.
     * @param file bytearray recieved
     */
    String storeFile(Long id, MultipartFile file);


    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    Resource loadFileAsResource(Long id);
}
