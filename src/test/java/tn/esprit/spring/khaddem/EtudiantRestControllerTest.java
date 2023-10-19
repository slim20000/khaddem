package tn.esprit.spring.khaddem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tn.esprit.spring.khaddem.controllers.EtudiantRestController;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Option;
import tn.esprit.spring.khaddem.services.IEtudiantService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EtudiantRestController.class)
public class EtudiantRestControllerTest {
    @Autowired
private MockMvc mockMvc;
    private static final Logger logger = LoggerFactory.getLogger(EtudiantRestController.class);

    @MockBean
    private IEtudiantService etudiantService;

    @Test
    public void testRetrieveAllEtudiants() throws Exception {
        logger.info("Start test RetrieveAllEtudiants...");

        List<Etudiant> mockEtudiants = Arrays.asList(
                new Etudiant(1, "slim1", "aaa", Option.SAE, null, null, null),
                new Etudiant(2, "slim2", "bbb", Option.GAMIX, null, null, null)
        );

        Mockito.when(etudiantService.retrieveAllEtudiants()).thenReturn(mockEtudiants);

        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/retrieve-all-etudiants")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].prenomE", is("slim1")))
                .andExpect(jsonPath("$[1].prenomE", is("slim2")));
        logger.info("Completed testRetrieveAllEtudiants!");

    }

    @Test
    public void testAddEtudiant() throws Exception {
        Etudiant mockEtudiant = new Etudiant(1, "slim1", "aaa", Option.SAE, null, null, null);

        Mockito.when(etudiantService.addEtudiant(Mockito.any(Etudiant.class))).thenReturn(mockEtudiant);

        String inputJson = new ObjectMapper().writeValueAsString(mockEtudiant);

        mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenomE", is("slim1")));
    }

}