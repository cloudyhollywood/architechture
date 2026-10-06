package tn.esprit.fareskhouloud4cce11.domain;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity

public class vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    @Enumerated(EnumType.STRING)
    private StatutVehicule statutVehicule;
    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorieVehicule;
    private BigDecimal tarifJournal;
    private StatutVehicule statut;


}
