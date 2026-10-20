package tn.esprit.fareskhouloud4cce11.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.fareskhouloud4cce11.domain.Contrat;
import tn.esprit.fareskhouloud4cce11.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratRestController {

    private final IContratService contratService;

    // GET : Lister tous les contrats
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // GET : Rechercher un contrat par ID
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    // POST : Créer un contrat
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // PUT : Modifier un contrat
    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contrat) {

        return contratService.update(id, contrat);
    }

    // DELETE : Supprimer un contrat
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}