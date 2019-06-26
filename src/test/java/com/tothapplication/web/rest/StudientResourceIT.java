package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.Studient;
import com.tothapplication.repository.StudientRepository;
import com.tothapplication.service.StudientService;
import com.tothapplication.service.dto.StudientDTO;
import com.tothapplication.service.mapper.StudientMapper;
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
 * Integration tests for the {@Link StudientResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class StudientResourceIT {

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudientRepository studientRepository;

    @Autowired
    private StudientMapper studientMapper;

    @Autowired
    private StudientService studientService;

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

    private MockMvc restStudientMockMvc;

    private Studient studient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudientResource studientResource = new StudientResource(studientService);
        this.restStudientMockMvc = MockMvcBuilders.standaloneSetup(studientResource)
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
    public static Studient createEntity(EntityManager em) {
        Studient studient = new Studient()
            .birthdate(DEFAULT_BIRTHDATE);
        return studient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Studient createUpdatedEntity(EntityManager em) {
        Studient studient = new Studient()
            .birthdate(UPDATED_BIRTHDATE);
        return studient;
    }

    @BeforeEach
    public void initTest() {
        studient = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudient() throws Exception {
        int databaseSizeBeforeCreate = studientRepository.findAll().size();

        // Create the Studient
        StudientDTO studientDTO = studientMapper.toDto(studient);
        restStudientMockMvc.perform(post("/api/studients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studientDTO)))
            .andExpect(status().isCreated());

        // Validate the Studient in the database
        List<Studient> studientList = studientRepository.findAll();
        assertThat(studientList).hasSize(databaseSizeBeforeCreate + 1);
        Studient testStudient = studientList.get(studientList.size() - 1);
        assertThat(testStudient.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
    }

    @Test
    @Transactional
    public void createStudientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studientRepository.findAll().size();

        // Create the Studient with an existing ID
        studient.setId(1L);
        StudientDTO studientDTO = studientMapper.toDto(studient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudientMockMvc.perform(post("/api/studients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Studient in the database
        List<Studient> studientList = studientRepository.findAll();
        assertThat(studientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudients() throws Exception {
        // Initialize the database
        studientRepository.saveAndFlush(studient);

        // Get all the studientList
        restStudientMockMvc.perform(get("/api/studients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studient.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudient() throws Exception {
        // Initialize the database
        studientRepository.saveAndFlush(studient);

        // Get the studient
        restStudientMockMvc.perform(get("/api/studients/{id}", studient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studient.getId().intValue()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudient() throws Exception {
        // Get the studient
        restStudientMockMvc.perform(get("/api/studients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudient() throws Exception {
        // Initialize the database
        studientRepository.saveAndFlush(studient);

        int databaseSizeBeforeUpdate = studientRepository.findAll().size();

        // Update the studient
        Studient updatedStudient = studientRepository.findById(studient.getId()).get();
        // Disconnect from session so that the updates on updatedStudient are not directly saved in db
        em.detach(updatedStudient);
        updatedStudient
            .birthdate(UPDATED_BIRTHDATE);
        StudientDTO studientDTO = studientMapper.toDto(updatedStudient);

        restStudientMockMvc.perform(put("/api/studients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studientDTO)))
            .andExpect(status().isOk());

        // Validate the Studient in the database
        List<Studient> studientList = studientRepository.findAll();
        assertThat(studientList).hasSize(databaseSizeBeforeUpdate);
        Studient testStudient = studientList.get(studientList.size() - 1);
        assertThat(testStudient.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudient() throws Exception {
        int databaseSizeBeforeUpdate = studientRepository.findAll().size();

        // Create the Studient
        StudientDTO studientDTO = studientMapper.toDto(studient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudientMockMvc.perform(put("/api/studients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Studient in the database
        List<Studient> studientList = studientRepository.findAll();
        assertThat(studientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudient() throws Exception {
        // Initialize the database
        studientRepository.saveAndFlush(studient);

        int databaseSizeBeforeDelete = studientRepository.findAll().size();

        // Delete the studient
        restStudientMockMvc.perform(delete("/api/studients/{id}", studient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Studient> studientList = studientRepository.findAll();
        assertThat(studientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Studient.class);
        Studient studient1 = new Studient();
        studient1.setId(1L);
        Studient studient2 = new Studient();
        studient2.setId(studient1.getId());
        assertThat(studient1).isEqualTo(studient2);
        studient2.setId(2L);
        assertThat(studient1).isNotEqualTo(studient2);
        studient1.setId(null);
        assertThat(studient1).isNotEqualTo(studient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudientDTO.class);
        StudientDTO studientDTO1 = new StudientDTO();
        studientDTO1.setId(1L);
        StudientDTO studientDTO2 = new StudientDTO();
        assertThat(studientDTO1).isNotEqualTo(studientDTO2);
        studientDTO2.setId(studientDTO1.getId());
        assertThat(studientDTO1).isEqualTo(studientDTO2);
        studientDTO2.setId(2L);
        assertThat(studientDTO1).isNotEqualTo(studientDTO2);
        studientDTO1.setId(null);
        assertThat(studientDTO1).isNotEqualTo(studientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studientMapper.fromId(null)).isNull();
    }
}
