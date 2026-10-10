# Stratégies de chargement JPA – AutoLoc

## 1. Objectif

Dans le projet AutoLoc, les associations entre les entités
utilisent principalement FetchType.LAZY.

Cette stratégie permet de charger les entités associées
uniquement lorsqu'elles sont nécessaires.

Elle évite de charger inutilement un grand nombre de données
depuis la base de données.

## 2. Associations et stratégies de chargement

| Association | Type | Fetch |
|---|---|---|
| Agence - Vehicule | OneToMany / ManyToOne | LAZY |
| Agence - Employe | OneToMany / ManyToOne | LAZY |
| Vehicule - Equipement | ManyToMany | LAZY |
| Client - Reservation | OneToMany / ManyToOne | LAZY |
| Reservation - Vehicule | ManyToOne | LAZY |
| Reservation - Contrat | OneToOne | LAZY |
| Contrat - Paiement | OneToMany / ManyToOne | LAZY |
| Vehicule - Maintenance | OneToMany / ManyToOne | LAZY |

## 3. Stratégies de cascade

- Contrat - Paiement :
  CascadeType.ALL et orphanRemoval = true.

- Client - Reservation :
  CascadeType.PERSIST.

- Reservation - Contrat :
  CascadeType.ALL.

- Vehicule - Maintenance :
  CascadeType.PERSIST.

- Agence - Vehicule :
  Aucune cascade.

- Agence - Employe :
  Aucune cascade.

- Vehicule - Equipement :
  Aucune cascade.

## 4. Pourquoi utiliser LAZY ?

FetchType.LAZY permet de différer le chargement
des associations jusqu'au moment où elles sont utilisées.

Par exemple, lorsqu'on récupère une agence,
il n'est pas toujours nécessaire de récupérer immédiatement
tous ses véhicules et tous ses employés.

Cela permet de limiter les requêtes et les données chargées
inutilement.

Il faut toutefois accéder aux associations LAZY
dans un contexte de persistance adapté, par exemple
dans une méthode transactionnelle, pour éviter
LazyInitializationException.

## 5. Conclusion

Le projet AutoLoc utilise FetchType.LAZY pour optimiser
le chargement des relations entre les entités.

Les cascades sont définies uniquement sur les associations
qui nécessitent la propagation des opérations de persistance.