package com.tothapplication.web.rest;

import com.tothapplication.service.StudientService;
import com.tothapplication.web.rest.errors.BadRequestAlertException;
import com.tothapplication.service.dto.StudientDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.tothapplication.domain.Studient}.
 */
@RestController
@RequestMapping("/api")
public class StudientResource {

    private final Logger log = LoggerFactory.getLogger(StudientResource.class);

    private static final String ENTITY_NAME = "studient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudientService studientService;

    public StudientResource(StudientService studientService) {
        this.studientService = studientService;
    }

    /**
     * {@code POST  /studients} : Create a new studient.
     *
     * @param studientDTO the studientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studientDTO, or with status {@code 400 (Bad Request)} if the studient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/studients")
    public ResponseEntity<StudientDTO> createStudient(@RequestBody StudientDTO studientDTO) throws URISyntaxException {
        log.debug("REST request to save Studient : {}", studientDTO);
        if (studientDTO.getId() != null) {
            throw new BadRequestAlertException("A new studient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudientDTO result = studientService.save(studientDTO);
        return ResponseEntity.created(new URI("/api/studients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /studients} : Updates an existing studient.
     *
     * @param studientDTO the studientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studientDTO,
     * or with status {@code 400 (Bad Request)} if the studientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/studients")
    public ResponseEntity<StudientDTO> updateStudient(@RequestBody StudientDTO studientDTO) throws URISyntaxException {
        log.debug("REST request to update Studient : {}", studientDTO);
        if (studientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudientDTO result = studientService.save(studientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /studients} : get all the studients.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studients in body.
     */
    @GetMapping("/studients")
    public ResponseEntity<List<StudientDTO>> getAllStudients(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("evaluation-is-null".equals(filter)) {
            log.debug("REST request to get all Studients where evaluation is null");
            return new ResponseEntity<>(studientService.findAllWhereEvaluationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Studients");
        Page<StudientDTO> page = studientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /studients/:id} : get the "id" studient.
     *
     * @param id the id of the studientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/studients/{id}")
    public ResponseEntity<StudientDTO> getStudient(@PathVariable Long id) {
        log.debug("REST request to get Studient : {}", id);
        Optional<StudientDTO> studientDTO = studientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studientDTO);
    }

    /**
     * {@code DELETE  /studients/:id} : delete the "id" studient.
     *
     * @param id the id of the studientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/studients/{id}")
    public ResponseEntity<Void> deleteStudient(@PathVariable Long id) {
        log.debug("REST request to delete Studient : {}", id);
        studientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
