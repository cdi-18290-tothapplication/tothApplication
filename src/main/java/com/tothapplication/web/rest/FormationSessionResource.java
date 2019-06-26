package com.tothapplication.web.rest;

import com.tothapplication.service.FormationSessionService;
import com.tothapplication.web.rest.errors.BadRequestAlertException;
import com.tothapplication.service.dto.FormationSessionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tothapplication.domain.FormationSession}.
 */
@RestController
@RequestMapping("/api")
public class FormationSessionResource {

    private final Logger log = LoggerFactory.getLogger(FormationSessionResource.class);

    private static final String ENTITY_NAME = "formationSession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormationSessionService formationSessionService;

    public FormationSessionResource(FormationSessionService formationSessionService) {
        this.formationSessionService = formationSessionService;
    }

    /**
     * {@code POST  /formation-sessions} : Create a new formationSession.
     *
     * @param formationSessionDTO the formationSessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formationSessionDTO, or with status {@code 400 (Bad Request)} if the formationSession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formation-sessions")
    public ResponseEntity<FormationSessionDTO> createFormationSession(@Valid @RequestBody FormationSessionDTO formationSessionDTO) throws URISyntaxException {
        log.debug("REST request to save FormationSession : {}", formationSessionDTO);
        if (formationSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new formationSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormationSessionDTO result = formationSessionService.save(formationSessionDTO);
        return ResponseEntity.created(new URI("/api/formation-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formation-sessions} : Updates an existing formationSession.
     *
     * @param formationSessionDTO the formationSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formationSessionDTO,
     * or with status {@code 400 (Bad Request)} if the formationSessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formationSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formation-sessions")
    public ResponseEntity<FormationSessionDTO> updateFormationSession(@Valid @RequestBody FormationSessionDTO formationSessionDTO) throws URISyntaxException {
        log.debug("REST request to update FormationSession : {}", formationSessionDTO);
        if (formationSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormationSessionDTO result = formationSessionService.save(formationSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formationSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formation-sessions} : get all the formationSessions.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formationSessions in body.
     */
    @GetMapping("/formation-sessions")
    public ResponseEntity<List<FormationSessionDTO>> getAllFormationSessions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of FormationSessions");
        Page<FormationSessionDTO> page;
        if (eagerload) {
            page = formationSessionService.findAllWithEagerRelationships(pageable);
        } else {
            page = formationSessionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formation-sessions/:id} : get the "id" formationSession.
     *
     * @param id the id of the formationSessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formationSessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formation-sessions/{id}")
    public ResponseEntity<FormationSessionDTO> getFormationSession(@PathVariable Long id) {
        log.debug("REST request to get FormationSession : {}", id);
        Optional<FormationSessionDTO> formationSessionDTO = formationSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formationSessionDTO);
    }

    /**
     * {@code DELETE  /formation-sessions/:id} : delete the "id" formationSession.
     *
     * @param id the id of the formationSessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formation-sessions/{id}")
    public ResponseEntity<Void> deleteFormationSession(@PathVariable Long id) {
        log.debug("REST request to delete FormationSession : {}", id);
        formationSessionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
