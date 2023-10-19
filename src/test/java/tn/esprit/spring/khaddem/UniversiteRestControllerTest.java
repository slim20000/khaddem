package tn.esprit.spring.khaddem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.khaddem.controllers.UniversiteRestController;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.services.IUniversiteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UniversiteRestController.class)
public class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(UniversiteRestControllerTest.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UniversiteRestController(universiteService)).build();
    }

    @Test
    public void testAddUniversite() throws Exception {
        Universite mockInputUniversite = new Universite("University C", new ArrayList<>());
        Universite mockSavedUniversite = new Universite("University C", new ArrayList<>());
        mockSavedUniversite.setIdUniversite(1);

        when(universiteService.addUniversite(any(Universite.class))).thenReturn(mockSavedUniversite);

        mockMvc.perform(post("/universite/add-universite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idUniversite\": 0,\"nomUniv\": \"String\",\"departements\": []}"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniv").value("University C"))
                .andExpect(jsonPath("$.idUniversite").value(1));
    }

    @Test
    public void testGetUniversites() throws Exception {
        logger.info("Starting testGetUniversites...");

        List<Departement> departementsListForUniA = new ArrayList<>();
        List<Departement> departementsListForUniB = new ArrayList<>();

        List<Universite> mockUniversites = Arrays.asList(
                new Universite(1, "University A", departementsListForUniA),
                new Universite(2, "University B", departementsListForUniB)
        );

        when(universiteService.retrieveAllUniversites()).thenReturn(mockUniversites);

        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nomUniv", is("University A")))
                .andExpect(jsonPath("$[1].nomUniv", is("University B")));

        logger.info("testGetUniversites completed successfully.");
    }

    @Test
    public void testRetrieveUniversite() throws Exception {
        logger.info("Starting testRetrieveUniversite...");

        List<Departement> departementsListForUniA = new ArrayList<>();

        Universite mockUniversite = new Universite(1, "University A", departementsListForUniA);

        when(universiteService.retrieveUniversite(1)).thenReturn(mockUniversite);

        mockMvc.perform(get("/universite/retrieve-universite/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniv", is("University A")));

        logger.info("testRetrieveUniversite completed successfully.");
    }

}
