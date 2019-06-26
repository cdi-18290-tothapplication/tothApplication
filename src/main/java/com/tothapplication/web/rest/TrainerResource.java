package com.tothapplication.web.rest;

import com.tothapplication.service.TrainerService;
import com.tothapplication.web.rest.errors.BadRequestAlertException;
import com.tothapplication.service.dto.TrainerDTO;

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
 * REST controller for managing {@link com.tothapplication.domain.Trainer}.
 */
@RestController
@RequestMapping("/api")
public class TrainerResource {

    private final Logger log = LoggerFactory.getLogger(TrainerResource.class);

    private static final String ENTITY_NAME = "trainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainerService trainerService;

    public TrainerResource(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    /**
     * {@code POST  /trainers} : Create a new trainer.
     *
     * @param trainerDTO the trainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainerDTO, or with status {@code 400 (Bad Request)} if the trainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainers")
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) throws URISyntaxException {
        log.debug("REST request to save Trainer : {}", trainerDTO);
        if (trainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainerDTO result = trainerService.save(trainerDTO);
        return ResponseEntity.created(new URI("/api/trainers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainers} : Updates an existing trainer.
     *
     * @param trainerDTO the trainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainerDTO,
     * or with status {@code 400 (Bad Request)} if the trainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainers")
    public ResponseEntity<TrainerDTO> updateTrainer(@RequestBody TrainerDTO trainerDTO) throws URISyntaxException {
        log.debug("REST request to update Trainer : {}", trainerDTO);
        if (trainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainerDTO result = trainerService.save(trainerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trainers} : get all the trainers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainers in body.
     */
    @GetMapping("/trainers")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("evaluation-is-null".equals(filter)) {
            log.debug("REST request to get all Trainers where evaluation is null");
            return new ResponseEntity<>(trainerService.findAllWhereEvaluationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Trainers");
        Page<TrainerDTO> page = trainerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainers/:id} : get the "id" trainer.
     *
     * @param id the id of the trainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable Long id) {
        log.debug("REST request to get Trainer : {}", id);
        Optional<TrainerDTO> trainerDTO = trainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainerDTO);
    }

    /**
     * {@code DELETE  /trainers/:id} : delete the "id" trainer.
     *
     * @param id the id of the trainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainers/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        log.debug("REST request to delete Trainer : {}", id);
        trainerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
