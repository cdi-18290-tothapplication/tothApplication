package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.Trainer;
import com.tothapplication.repository.TrainerRepository;
import com.tothapplication.service.TrainerService;
import com.tothapplication.service.dto.TrainerDTO;
import com.tothapplication.service.mapper.TrainerMapper;
import com.tothapplication.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tothapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TrainerResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class TrainerResourceIT {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTrainerMockMvc;

    private Trainer trainer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainerResource trainerResource = new TrainerResource(trainerService);
        this.restTrainerMockMvc = MockMvcBuilders.standaloneSetup(trainerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createEntity(EntityManager em) {
        Trainer trainer = new Trainer();
        return trainer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createUpdatedEntity(EntityManager em) {
        Trainer trainer = new Trainer();
        return trainer;
    }

    @BeforeEach
    public void initTest() {
        trainer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainer() throws Exception {
        int databaseSizeBeforeCreate = trainerRepository.findAll().size();

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);
        restTrainerMockMvc.perform(post("/api/trainers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate + 1);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
    }

    @Test
    @Transactional
    public void createTrainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainerRepository.findAll().size();

        // Create the Trainer with an existing ID
        trainer.setId(1L);
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainerMockMvc.perform(post("/api/trainers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrainers() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList
        restTrainerMockMvc.perform(get("/api/trainers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainer.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get the trainer
        restTrainerMockMvc.perform(get("/api/trainers/{id}", trainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainer() throws Exception {
        // Get the trainer
        restTrainerMockMvc.perform(get("/api/trainers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer
        Trainer updatedTrainer = trainerRepository.findById(trainer.getId()).get();
        // Disconnect from session so that the updates on updatedTrainer are not directly saved in db
        em.detach(updatedTrainer);
        TrainerDTO trainerDTO = trainerMapper.toDto(updatedTrainer);

        restTrainerMockMvc.perform(put("/api/trainers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainerMockMvc.perform(put("/api/trainers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeDelete = trainerRepository.findAll().size();

        // Delete the trainer
        restTrainerMockMvc.perform(delete("/api/trainers/{id}", trainer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trainer.class);
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(trainer1.getId());
        assertThat(trainer1).isEqualTo(trainer2);
        trainer2.setId(2L);
        assertThat(trainer1).isNotEqualTo(trainer2);
        trainer1.setId(null);
        assertThat(trainer1).isNotEqualTo(trainer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainerDTO.class);
        TrainerDTO trainerDTO1 = new TrainerDTO();
        trainerDTO1.setId(1L);
        TrainerDTO trainerDTO2 = new TrainerDTO();
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
        trainerDTO2.setId(trainerDTO1.getId());
        assertThat(trainerDTO1).isEqualTo(trainerDTO2);
        trainerDTO2.setId(2L);
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
        trainerDTO1.setId(null);
        assertThat(trainerDTO1).isNotEqualTo(trainerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trainerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trainerMapper.fromId(null)).isNull();
    }
}
