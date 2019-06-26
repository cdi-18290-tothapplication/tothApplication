package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.CCP;
import com.tothapplication.repository.CCPRepository;
import com.tothapplication.service.CCPService;
import com.tothapplication.service.dto.CCPDTO;
import com.tothapplication.service.mapper.CCPMapper;
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
import java.util.ArrayList;
import java.util.List;

import static com.tothapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CCPResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class CCPResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private CCPRepository cCPRepository;

    @Mock
    private CCPRepository cCPRepositoryMock;

    @Autowired
    private CCPMapper cCPMapper;

    @Mock
    private CCPService cCPServiceMock;

    @Autowired
    private CCPService cCPService;

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

    private MockMvc restCCPMockMvc;

    private CCP cCP;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CCPResource cCPResource = new CCPResource(cCPService);
        this.restCCPMockMvc = MockMvcBuilders.standaloneSetup(cCPResource)
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
    public static CCP createEntity(EntityManager em) {
        CCP cCP = new CCP()
            .title(DEFAULT_TITLE);
        return cCP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCP createUpdatedEntity(EntityManager em) {
        CCP cCP = new CCP()
            .title(UPDATED_TITLE);
        return cCP;
    }

    @BeforeEach
    public void initTest() {
        cCP = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCP() throws Exception {
        int databaseSizeBeforeCreate = cCPRepository.findAll().size();

        // Create the CCP
        CCPDTO cCPDTO = cCPMapper.toDto(cCP);
        restCCPMockMvc.perform(post("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCPDTO)))
            .andExpect(status().isCreated());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeCreate + 1);
        CCP testCCP = cCPList.get(cCPList.size() - 1);
        assertThat(testCCP.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createCCPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCPRepository.findAll().size();

        // Create the CCP with an existing ID
        cCP.setId(1L);
        CCPDTO cCPDTO = cCPMapper.toDto(cCP);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCPMockMvc.perform(post("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCPRepository.findAll().size();
        // set the field null
        cCP.setTitle(null);

        // Create the CCP, which fails.
        CCPDTO cCPDTO = cCPMapper.toDto(cCP);

        restCCPMockMvc.perform(post("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCPDTO)))
            .andExpect(status().isBadRequest());

        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCPS() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        // Get all the cCPList
        restCCPMockMvc.perform(get("/api/ccps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCP.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCCPSWithEagerRelationshipsIsEnabled() throws Exception {
        CCPResource cCPResource = new CCPResource(cCPServiceMock);
        when(cCPServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCCPMockMvc = MockMvcBuilders.standaloneSetup(cCPResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCCPMockMvc.perform(get("/api/ccps?eagerload=true"))
        .andExpect(status().isOk());

        verify(cCPServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCCPSWithEagerRelationshipsIsNotEnabled() throws Exception {
        CCPResource cCPResource = new CCPResource(cCPServiceMock);
            when(cCPServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCCPMockMvc = MockMvcBuilders.standaloneSetup(cCPResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCCPMockMvc.perform(get("/api/ccps?eagerload=true"))
        .andExpect(status().isOk());

            verify(cCPServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCCP() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        // Get the cCP
        restCCPMockMvc.perform(get("/api/ccps/{id}", cCP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cCP.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCCP() throws Exception {
        // Get the cCP
        restCCPMockMvc.perform(get("/api/ccps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCP() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        int databaseSizeBeforeUpdate = cCPRepository.findAll().size();

        // Update the cCP
        CCP updatedCCP = cCPRepository.findById(cCP.getId()).get();
        // Disconnect from session so that the updates on updatedCCP are not directly saved in db
        em.detach(updatedCCP);
        updatedCCP
            .title(UPDATED_TITLE);
        CCPDTO cCPDTO = cCPMapper.toDto(updatedCCP);

        restCCPMockMvc.perform(put("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCPDTO)))
            .andExpect(status().isOk());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeUpdate);
        CCP testCCP = cCPList.get(cCPList.size() - 1);
        assertThat(testCCP.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCP() throws Exception {
        int databaseSizeBeforeUpdate = cCPRepository.findAll().size();

        // Create the CCP
        CCPDTO cCPDTO = cCPMapper.toDto(cCP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCPMockMvc.perform(put("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCP() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        int databaseSizeBeforeDelete = cCPRepository.findAll().size();

        // Delete the cCP
        restCCPMockMvc.perform(delete("/api/ccps/{id}", cCP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCP.class);
        CCP cCP1 = new CCP();
        cCP1.setId(1L);
        CCP cCP2 = new CCP();
        cCP2.setId(cCP1.getId());
        assertThat(cCP1).isEqualTo(cCP2);
        cCP2.setId(2L);
        assertThat(cCP1).isNotEqualTo(cCP2);
        cCP1.setId(null);
        assertThat(cCP1).isNotEqualTo(cCP2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCPDTO.class);
        CCPDTO cCPDTO1 = new CCPDTO();
        cCPDTO1.setId(1L);
        CCPDTO cCPDTO2 = new CCPDTO();
        assertThat(cCPDTO1).isNotEqualTo(cCPDTO2);
        cCPDTO2.setId(cCPDTO1.getId());
        assertThat(cCPDTO1).isEqualTo(cCPDTO2);
        cCPDTO2.setId(2L);
        assertThat(cCPDTO1).isNotEqualTo(cCPDTO2);
        cCPDTO1.setId(null);
        assertThat(cCPDTO1).isNotEqualTo(cCPDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cCPMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cCPMapper.fromId(null)).isNull();
    }
}
