package tn.esprit.fareskhouloud4cce11.service;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.fareskhouloud4cce11.domain.Maintenance;
import tn.esprit.fareskhouloud4cce11.exception.ResourceNotFoundException;
import tn.esprit.fareskhouloud4cce11.Repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {

        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle maintenance ne doit pas avoir d'identifiant"
            );
        }

        verifierDates(
                maintenance.getDateDebut(),
                maintenance.getDateFin()
        );

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {

        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id)
                );
    }

    @Override
    public List<Maintenance> findAll() {

        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {

        Maintenance existante = findById(id);

        verifierDates(
                maintenance.getDateDebut(),
                maintenance.getDateFin()
        );

        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());

        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }

        maintenanceRepository.deleteById(id);
    }

    private void verifierDates(LocalDate dateDebut, LocalDate dateFin) {

        if (dateDebut != null
                && dateFin != null
                && dateFin.isBefore(dateDebut)) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}