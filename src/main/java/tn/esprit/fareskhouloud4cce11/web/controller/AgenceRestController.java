package tn.esprit.fareskhouloud4cce11.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.fareskhouloud4cce11.domain.Agence;
import tn.esprit.fareskhouloud4cce11.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceRestController {

    private final IAgenceService agenceService;

    // 1. Afficher toutes les agences
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // 2. Afficher une agence par son ID
    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    // 3. Ajouter une agence
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    // 4. Modifier une agence
    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id,
                         @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    // 5. Supprimer une agence
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}