package tn.esprit.fareskhouloud4cce11.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.fareskhouloud4cce11.domain.Vehicule;
import tn.esprit.fareskhouloud4cce11.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    // 1. Afficher tous les véhicules
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    // 2. Afficher un véhicule par son ID
    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    // 3. Ajouter un véhicule
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    // 4. Modifier un véhicule
    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id,
                           @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    // 5. Supprimer un véhicule
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}