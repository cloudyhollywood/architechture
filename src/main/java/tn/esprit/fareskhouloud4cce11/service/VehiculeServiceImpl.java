package tn.esprit.fareskhouloud4cce11.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.fareskhouloud4cce11.domain.Vehicule;
import tn.esprit.fareskhouloud4cce11.exception.ResourceNotFoundException;
import tn.esprit.fareskhouloud4cce11.Repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {

        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant"
            );
        }

        if (vehicule.getTarifJournal() != null
                && vehicule.getTarifJournal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {

        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id)
                );
    }

    @Override
    public List<Vehicule> findAll() {

        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {

        Vehicule existant = findById(id);

        if (vehicule.getTarifJournal() != null
                && vehicule.getTarifJournal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif"
            );
        }

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorieVehicule(vehicule.getCategorieVehicule());
        existant.setTarifJournal(vehicule.getTarifJournal());
        existant.setStatut(vehicule.getStatut());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {

        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }
}