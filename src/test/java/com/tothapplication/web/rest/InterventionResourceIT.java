package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.Intervention;
import com.tothapplication.repository.InterventionRepository;
import com.tothapplication.service.InterventionService;
import com.tothapplication.service.dto.InterventionDTO;
import com.tothapplication.service.mapper.InterventionMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.tothapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InterventionResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class InterventionResourceIT {

    private static final LocalDate DEFAULT_BEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private InterventionMapper interventionMapper;

    @Autowired
    private InterventionService interventionService;

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

    private MockMvc restInterventionMockMvc;

    private Intervention intervention;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InterventionResource interventionResource = new InterventionResource(interventionService);
        this.restInterventionMockMvc = MockMvcBuilders.standaloneSetup(interventionResource)
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
    public static Intervention createEntity(EntityManager em) {
        Intervention intervention = new Intervention()
            .begin(DEFAULT_BEGIN)
            .end(DEFAULT_END);
        return intervention;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intervention createUpdatedEntity(EntityManager em) {
        Intervention intervention = new Intervention()
            .begin(UPDATED_BEGIN)
            .end(UPDATED_END);
        return intervention;
    }

    @BeforeEach
    public void initTest() {
        intervention = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntervention() throws Exception {
        int databaseSizeBeforeCreate = interventionRepository.findAll().size();

        // Create the Intervention
        InterventionDTO interventionDTO = interventionMapper.toDto(intervention);
        restInterventionMockMvc.perform(post("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isCreated());

        // Validate the Intervention in the database
        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeCreate + 1);
        Intervention testIntervention = interventionList.get(interventionList.size() - 1);
        assertThat(testIntervention.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testIntervention.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createInterventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interventionRepository.findAll().size();

        // Create the Intervention with an existing ID
        intervention.setId(1L);
        InterventionDTO interventionDTO = interventionMapper.toDto(intervention);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterventionMockMvc.perform(post("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intervention in the database
        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBeginIsRequired() throws Exception {
        int databaseSizeBeforeTest = interventionRepository.findAll().size();
        // set the field null
        intervention.setBegin(null);

        // Create the Intervention, which fails.
        InterventionDTO interventionDTO = interventionMapper.toDto(intervention);

        restInterventionMockMvc.perform(post("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isBadRequest());

        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = interventionRepository.findAll().size();
        // set the field null
        intervention.setEnd(null);

        // Create the Intervention, which fails.
        InterventionDTO interventionDTO = interventionMapper.toDto(intervention);

        restInterventionMockMvc.perform(post("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isBadRequest());

        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInterventions() throws Exception {
        // Initialize the database
        interventionRepository.saveAndFlush(intervention);

        // Get all the interventionList
        restInterventionMockMvc.perform(get("/api/interventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intervention.getId().intValue())))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getIntervention() throws Exception {
        // Initialize the database
        interventionRepository.saveAndFlush(intervention);

        // Get the intervention
        restInterventionMockMvc.perform(get("/api/interventions/{id}", intervention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intervention.getId().intValue()))
            .andExpect(jsonPath("$.begin").value(DEFAULT_BEGIN.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIntervention() throws Exception {
        // Get the intervention
        restInterventionMockMvc.perform(get("/api/interventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntervention() throws Exception {
        // Initialize the database
        interventionRepository.saveAndFlush(intervention);

        int databaseSizeBeforeUpdate = interventionRepository.findAll().size();

        // Update the intervention
        Intervention updatedIntervention = interventionRepository.findById(intervention.getId()).get();
        // Disconnect from session so that the updates on updatedIntervention are not directly saved in db
        em.detach(updatedIntervention);
        updatedIntervention
            .begin(UPDATED_BEGIN)
            .end(UPDATED_END);
        InterventionDTO interventionDTO = interventionMapper.toDto(updatedIntervention);

        restInterventionMockMvc.perform(put("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isOk());

        // Validate the Intervention in the database
        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeUpdate);
        Intervention testIntervention = interventionList.get(interventionList.size() - 1);
        assertThat(testIntervention.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testIntervention.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingIntervention() throws Exception {
        int databaseSizeBeforeUpdate = interventionRepository.findAll().size();

        // Create the Intervention
        InterventionDTO interventionDTO = interventionMapper.toDto(intervention);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterventionMockMvc.perform(put("/api/interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interventionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intervention in the database
        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntervention() throws Exception {
        // Initialize the database
        interventionRepository.saveAndFlush(intervention);

        int databaseSizeBeforeDelete = interventionRepository.findAll().size();

        // Delete the intervention
        restInterventionMockMvc.perform(delete("/api/interventions/{id}", intervention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intervention> interventionList = interventionRepository.findAll();
        assertThat(interventionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intervention.class);
        Intervention intervention1 = new Intervention();
        intervention1.setId(1L);
        Intervention intervention2 = new Intervention();
        intervention2.setId(intervention1.getId());
        assertThat(intervention1).isEqualTo(intervention2);
        intervention2.setId(2L);
        assertThat(intervention1).isNotEqualTo(intervention2);
        intervention1.setId(null);
        assertThat(intervention1).isNotEqualTo(intervention2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterventionDTO.class);
        InterventionDTO interventionDTO1 = new InterventionDTO();
        interventionDTO1.setId(1L);
        InterventionDTO interventionDTO2 = new InterventionDTO();
        assertThat(interventionDTO1).isNotEqualTo(interventionDTO2);
        interventionDTO2.setId(interventionDTO1.getId());
        assertThat(interventionDTO1).isEqualTo(interventionDTO2);
        interventionDTO2.setId(2L);
        assertThat(interventionDTO1).isNotEqualTo(interventionDTO2);
        interventionDTO1.setId(null);
        assertThat(interventionDTO1).isNotEqualTo(interventionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(interventionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(interventionMapper.fromId(null)).isNull();
    }
}
