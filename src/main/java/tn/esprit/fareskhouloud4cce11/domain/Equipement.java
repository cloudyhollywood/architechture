package tn.esprit.fareskhouloud4cce11.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "vehicules")
@Entity
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    private String libelle;

    // Association Equipement <-> Vehicule
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Vehicule> vehicules = new HashSet<>();
}