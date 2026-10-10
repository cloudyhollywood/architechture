package tn.esprit.fareskhouloud4cce11.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "agence")
@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEmploye role;

    // Association Employe -> Agence
    @ManyToOne(fetch = FetchType.LAZY)
    private Agence agence;
}