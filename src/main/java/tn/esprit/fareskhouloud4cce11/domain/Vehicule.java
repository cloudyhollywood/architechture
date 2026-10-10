package tn.esprit.fareskhouloud4cce11.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"agence", "equipements", "reservations", "maintenances"})
@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;

    private String marque;

    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorieVehicule;

    private BigDecimal tarifJournal;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;


    // 1. Association Vehicule -> Agence
    @ManyToOne(fetch = FetchType.LAZY)
    private Agence agence;


    // 2. Association Vehicule <-> Equipement
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipement")
    )
    @Builder.Default
    private Set<Equipement> equipements = new HashSet<>();


    // 3. Association Vehicule -> Reservation
    @OneToMany(
            mappedBy = "vehicule",
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();


    // 4. Association Vehicule -> Maintenance
    @OneToMany(
            mappedBy = "vehicule",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Maintenance> maintenances = new ArrayList<>();
}