package tn.esprit.fareskhouloud4cce11.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.fareskhouloud4cce11.domain.Paiement;
import tn.esprit.fareskhouloud4cce11.exception.ResourceNotFoundException;
import tn.esprit.fareskhouloud4cce11.Repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {

        return paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement", id)
                );
    }

    @Override
    public List<Paiement> findAll() {

        return paiementRepository.findAll();
    }
}