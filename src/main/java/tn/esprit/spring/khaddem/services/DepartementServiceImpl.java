package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Service
public class DepartementServiceImpl implements IDepartementService{
    @Autowired
    DepartementRepository departementRepository;
    @Autowired
    UniversiteRepository universiteRepository;
    @Override
    public List<Departement> retrieveAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public Departement addDepartement(Departement d) {
        departementRepository.save(d);
        return d;
    }

    @Override
    public Departement updateDepartement(Departement d) {
        departementRepository.save(d);
        return d;
    }

    @Override
    public Departement retrieveDepartement(Integer idDepart) {
        return departementRepository.findById(idDepart).get();
    }

    @Override
    public List<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).get();
        return universite.getDepartements();
    }
}
