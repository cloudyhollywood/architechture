package tn.esprit.fareskhouloud4cce11.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.fareskhouloud4cce11.domain.Contrat;
import tn.esprit.fareskhouloud4cce11.domain.Reservation;

import tn.esprit.fareskhouloud4cce11.exception.ResourceNotFoundException;

import tn.esprit.fareskhouloud4cce11.Repository.IContratRepository;
import tn.esprit.fareskhouloud4cce11.Repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;

    // 1. CREATE : Créer un contrat
    @Override
    public Contrat create(Contrat contrat) {

        // Vérifier que le nouveau contrat n'a pas d'identifiant
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant"
            );
        }

        // Vérifier que le montant n'est pas négatif
        if (contrat.getMontantTotal() != null
                && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif"
            );
        }

        // Vérifier et associer la réservation
        if (contrat.getReservation() != null) {

            Long idReservation = contrat.getReservation().getIdReservation();

            if (idReservation == null) {
                throw new IllegalArgumentException(
                        "L'identifiant de la réservation est obligatoire"
                );
            }

            Reservation reservation = reservationRepository
                    .findById(idReservation)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Reservation", idReservation
                            )
                    );

            contrat.setReservation(reservation);
        }

        // Enregistrer le contrat
        return contratRepository.save(contrat);
    }

    // 2. READ : Rechercher un contrat par ID
    @Override
    public Contrat findById(Long id) {

        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id)
                );
    }

    // 3. READ : Lister tous les contrats
    @Override
    public List<Contrat> findAll() {

        return contratRepository.findAll();
    }

    // 4. UPDATE : Modifier un contrat
    @Override
    public Contrat update(Long id, Contrat contrat) {

        // Vérifier que le contrat existe
        Contrat existant = findById(id);

        // Vérifier que le montant n'est pas négatif
        if (contrat.getMontantTotal() != null
                && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif"
            );
        }

        // Modifier les informations
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());

        // Enregistrer les modifications
        return contratRepository.save(existant);
    }

    // 5. DELETE : Supprimer un contrat
    @Override
    public void deleteById(Long id) {

        // Vérifier que le contrat existe
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }

        // Supprimer le contrat
        contratRepository.deleteById(id);
    }
}