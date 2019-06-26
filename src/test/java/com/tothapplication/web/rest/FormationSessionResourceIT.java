package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.FormationSession;
import com.tothapplication.repository.FormationSessionRepository;
import com.tothapplication.service.FormationSessionService;
import com.tothapplication.service.dto.FormationSessionDTO;
import com.tothapplication.service.mapper.FormationSessionMapper;
import com.tothapplication.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static com.tothapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FormationSessionResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class FormationSessionResourceIT {

    private static final LocalDate DEFAULT_BEGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FormationSessionRepository formationSessionRepository;

    @Mock
    private FormationSessionRepository formationSessionRepositoryMock;

    @Autowired
    private FormationSessionMapper formationSessionMapper;

    @Mock
    private FormationSessionService formationSessionServiceMock;

    @Autowired
    private FormationSessionService formationSessionService;

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

    private MockMvc restFormationSessionMockMvc;

    private FormationSession formationSession;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormationSessionResource formationSessionResource = new FormationSessionResource(formationSessionService);
        this.restFormationSessionMockMvc = MockMvcBuilders.standaloneSetup(formationSessionResource)
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
    public static FormationSession createEntity(EntityManager em) {
        FormationSession formationSession = new FormationSession()
            .begin(DEFAULT_BEGIN)
            .end(DEFAULT_END);
        return formationSession;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormationSession createUpdatedEntity(EntityManager em) {
        FormationSession formationSession = new FormationSession()
            .begin(UPDATED_BEGIN)
            .end(UPDATED_END);
        return formationSession;
    }

    @BeforeEach
    public void initTest() {
        formationSession = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormationSession() throws Exception {
        int databaseSizeBeforeCreate = formationSessionRepository.findAll().size();

        // Create the FormationSession
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(formationSession);
        restFormationSessionMockMvc.perform(post("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isCreated());

        // Validate the FormationSession in the database
        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeCreate + 1);
        FormationSession testFormationSession = formationSessionList.get(formationSessionList.size() - 1);
        assertThat(testFormationSession.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testFormationSession.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createFormationSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formationSessionRepository.findAll().size();

        // Create the FormationSession with an existing ID
        formationSession.setId(1L);
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(formationSession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationSessionMockMvc.perform(post("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormationSession in the database
        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBeginIsRequired() throws Exception {
        int databaseSizeBeforeTest = formationSessionRepository.findAll().size();
        // set the field null
        formationSession.setBegin(null);

        // Create the FormationSession, which fails.
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(formationSession);

        restFormationSessionMockMvc.perform(post("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isBadRequest());

        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = formationSessionRepository.findAll().size();
        // set the field null
        formationSession.setEnd(null);

        // Create the FormationSession, which fails.
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(formationSession);

        restFormationSessionMockMvc.perform(post("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isBadRequest());

        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormationSessions() throws Exception {
        // Initialize the database
        formationSessionRepository.saveAndFlush(formationSession);

        // Get all the formationSessionList
        restFormationSessionMockMvc.perform(get("/api/formation-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formationSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFormationSessionsWithEagerRelationshipsIsEnabled() throws Exception {
        FormationSessionResource formationSessionResource = new FormationSessionResource(formationSessionServiceMock);
        when(formationSessionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFormationSessionMockMvc = MockMvcBuilders.standaloneSetup(formationSessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFormationSessionMockMvc.perform(get("/api/formation-sessions?eagerload=true"))
        .andExpect(status().isOk());

        verify(formationSessionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFormationSessionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        FormationSessionResource formationSessionResource = new FormationSessionResource(formationSessionServiceMock);
            when(formationSessionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFormationSessionMockMvc = MockMvcBuilders.standaloneSetup(formationSessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFormationSessionMockMvc.perform(get("/api/formation-sessions?eagerload=true"))
        .andExpect(status().isOk());

            verify(formationSessionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFormationSession() throws Exception {
        // Initialize the database
        formationSessionRepository.saveAndFlush(formationSession);

        // Get the formationSession
        restFormationSessionMockMvc.perform(get("/api/formation-sessions/{id}", formationSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formationSession.getId().intValue()))
            .andExpect(jsonPath("$.begin").value(DEFAULT_BEGIN.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormationSession() throws Exception {
        // Get the formationSession
        restFormationSessionMockMvc.perform(get("/api/formation-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormationSession() throws Exception {
        // Initialize the database
        formationSessionRepository.saveAndFlush(formationSession);

        int databaseSizeBeforeUpdate = formationSessionRepository.findAll().size();

        // Update the formationSession
        FormationSession updatedFormationSession = formationSessionRepository.findById(formationSession.getId()).get();
        // Disconnect from session so that the updates on updatedFormationSession are not directly saved in db
        em.detach(updatedFormationSession);
        updatedFormationSession
            .begin(UPDATED_BEGIN)
            .end(UPDATED_END);
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(updatedFormationSession);

        restFormationSessionMockMvc.perform(put("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isOk());

        // Validate the FormationSession in the database
        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeUpdate);
        FormationSession testFormationSession = formationSessionList.get(formationSessionList.size() - 1);
        assertThat(testFormationSession.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testFormationSession.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingFormationSession() throws Exception {
        int databaseSizeBeforeUpdate = formationSessionRepository.findAll().size();

        // Create the FormationSession
        FormationSessionDTO formationSessionDTO = formationSessionMapper.toDto(formationSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationSessionMockMvc.perform(put("/api/formation-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formationSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormationSession in the database
        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormationSession() throws Exception {
        // Initialize the database
        formationSessionRepository.saveAndFlush(formationSession);

        int databaseSizeBeforeDelete = formationSessionRepository.findAll().size();

        // Delete the formationSession
        restFormationSessionMockMvc.perform(delete("/api/formation-sessions/{id}", formationSession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormationSession> formationSessionList = formationSessionRepository.findAll();
        assertThat(formationSessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormationSession.class);
        FormationSession formationSession1 = new FormationSession();
        formationSession1.setId(1L);
        FormationSession formationSession2 = new FormationSession();
        formationSession2.setId(formationSession1.getId());
        assertThat(formationSession1).isEqualTo(formationSession2);
        formationSession2.setId(2L);
        assertThat(formationSession1).isNotEqualTo(formationSession2);
        formationSession1.setId(null);
        assertThat(formationSession1).isNotEqualTo(formationSession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormationSessionDTO.class);
        FormationSessionDTO formationSessionDTO1 = new FormationSessionDTO();
        formationSessionDTO1.setId(1L);
        FormationSessionDTO formationSessionDTO2 = new FormationSessionDTO();
        assertThat(formationSessionDTO1).isNotEqualTo(formationSessionDTO2);
        formationSessionDTO2.setId(formationSessionDTO1.getId());
        assertThat(formationSessionDTO1).isEqualTo(formationSessionDTO2);
        formationSessionDTO2.setId(2L);
        assertThat(formationSessionDTO1).isNotEqualTo(formationSessionDTO2);
        formationSessionDTO1.setId(null);
        assertThat(formationSessionDTO1).isNotEqualTo(formationSessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formationSessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formationSessionMapper.fromId(null)).isNull();
    }
}
