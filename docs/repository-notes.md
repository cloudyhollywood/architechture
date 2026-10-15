# Atelier 3 — Spring Data JPA et Repository

## 1. Choix des interfaces Repository

| Interface | Étend | Justification |
|-----------|-------|---------------|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet |
| IClientRepository | JpaRepository<Client, Long> | CRUD complet |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD, List et saveAndFlush |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD complet |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD complet |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD complet |
| IPaiementRepository | JpaRepository<Paiement, Long> | Lecture des paiements |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD complet |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD, tri et pagination |

## 2. Justification du choix de JpaRepository

JpaRepository a été choisi car il fournit :

- Les opérations CRUD : save(), findById(), findAll(), deleteById().
- La récupération des données sous forme de List.
- Le tri et la pagination.
- Les méthodes flush() et saveAndFlush().

Spring Data JPA génère automatiquement
l'implémentation des interfaces Repository.

## 3. Analyse SonarQube for IDE

### Anomalies détectées et corrigées

| Anomalie | Règle | Correction apportée |
|----------|-------|---------------------|
| 3 constantes mal nommées dans ModePaiement.java | java:S115 | Renommage en CARTE, ESPECES et VIREMENT |
| 4 constantes mal nommées dans StatutReservation.java | java:S115 | Renommage en EN_ATTENTE, CONFIRMEE, ANNULEE et TERMINEE |
| Convention de nommage non respectée pour une constante de StatutReservation | java:S115 | Application de la convention UPPER_SNAKE_CASE |

### Résultat de l'analyse

Avant correction :
7 anomalies détectées dans 2 fichiers.

Après correction :
No issues to display.

## 4. Vérification Spring Boot

L'application démarre correctement.

Résultat :

Found 9 JPA repository interfaces.

Connexion MySQL réussie.

## 5. Conclusion

La couche Repository du projet a été créée
avec Spring Data JPA.

Les neuf interfaces utilisent JpaRepository.

L'analyse SonarQube a permis de corriger
les problèmes de nommage des constantes Java.