package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.services.EquipeServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class EquipeServiceImplTest {

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private ContratRepository contratRepository;


//    @Test
//     void testEvoluerEquipes() {
//        Etudiant etudiant = new Etudiant();
//        etudiant.setIdEtudiant(1);
//
//        Contrat oldContract = new Contrat();
//        oldContract.setDateDebutContrat(getDateMinusYears(2));
//        oldContract.setDateFinContrat(new Date());
//        oldContract.setArchived(false);
//        oldContract.setEtudiant(etudiant);
//
//        Equipe juniorTeam = new Equipe();
//        juniorTeam.setIdEquipe(1);
//        juniorTeam.setNiveau(Niveau.JUNIOR);
//        juniorTeam.setEtudiants(Arrays.asList(etudiant, etudiant, etudiant));
//
//        Equipe seniorTeam = new Equipe();
//        seniorTeam.setIdEquipe(2);
//        seniorTeam.setNiveau(Niveau.SENIOR);
//        seniorTeam.setEtudiants(Arrays.asList(etudiant, etudiant, etudiant));
//
//        when(equipeRepository.findAll()).thenReturn(Arrays.asList(juniorTeam, seniorTeam));
//        when(contratRepository.findByEtudiantIdEtudiant(anyInt())).thenReturn(Arrays.asList(oldContract));
//
//        equipeService.evoluerEquipes();
//
//        assertEquals(Niveau.SENIOR, juniorTeam.getNiveau());
//        assertEquals(Niveau.EXPERT, seniorTeam.getNiveau());
//        verify(equipeRepository, times(2)).save(any(Equipe.class));
//    }

    private Date getDateMinusYears(int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -years);
        return calendar.getTime();
    }
    @Test
     void retrieveAllEquipesTest() {
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(new Equipe(), new Equipe()));

        List<Equipe> equipes = equipeService.retrieveAllEquipes();

        assertEquals(2, equipes.size());
        verify(equipeRepository, times(1)).findAll();
    }
    @Test
     void addEquipeTest() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe returnedEquipe = equipeService.addEquipe(equipe);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
     void updateEquipeTest() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe returnedEquipe = equipeService.updateEquipe(equipe);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
     void retrieveEquipeTest() {
        Integer id = 1;
        Equipe equipe = new Equipe();
        when(equipeRepository.findById(id)).thenReturn(Optional.of(equipe));

        Equipe returnedEquipe = equipeService.retrieveEquipe(id);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).findById(id);
    }
}
