# Atelier 4 — Inversion de contrôle, injection de dépendances et couche Service

## A. Comparaison des modes d'injection

### 1. Injection par constructeur

L'injection par constructeur consiste à fournir les dépendances
d'un objet lors de sa création.

Dans notre projet, nous utilisons Lombok avec @RequiredArgsConstructor
et des attributs final.

Exemple :

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
}

Avantages :
- Les dépendances sont obligatoires dès la création de l'objet.
- Les attributs peuvent être déclarés final.
- Le code est plus facile à tester.
- Les dépendances sont clairement identifiables.

### 2. Injection par attribut (Field Injection)

L'injection par attribut consiste à utiliser @Autowired
directement sur un attribut.

Exemple :

@Service
public class ContratServiceImpl implements IContratService {

    @Autowired
    private IContratRepository contratRepository;
}

Inconvénients :
- Les dépendances sont moins visibles.
- Les tests unitaires sont plus difficiles.
- Les attributs ne peuvent pas être final.
- L'objet peut être instancié sans que sa dépendance soit initialisée.

### 3. Résultat de l'analyse SonarQube

Lors de l'utilisation de l'injection par attribut,
SonarQube for IDE a signalé deux anomalies :

- java:S6813 : Remove this field injection and use constructor injection instead.
- java:S1128 : Remove this unused import 'lombok.RequiredArgsConstructor'.

Nous avons ensuite restauré l'injection par constructeur
avec @RequiredArgsConstructor et private final.

### 4. Conclusion

Nous privilégions l'injection par constructeur,
car elle améliore la lisibilité, la testabilité
et la maintenance du code.

C'est pourquoi tous les services du projet utilisent
@Service, @RequiredArgsConstructor et un Repository final.
## B. Tableau récapitulatif des services

Dans cet atelier, nous avons créé neuf interfaces de services
et neuf classes d'implémentation.

Chaque service utilise l'injection par constructeur grâce
à @RequiredArgsConstructor et un Repository déclaré final.

| Entité | Interface Service | Implémentation | Méthodes | Règle métier |
|---|---|---|---|---|
| Contrat | IContratService | ContratServiceImpl | CRUD complet | Refuser un montant total négatif |
| Agence | IAgenceService | AgenceServiceImpl | CRUD complet | Refuser un nom vide |
| Employe | IEmployeService | EmployeServiceImpl | CRUD complet | Refuser un nom vide |
| Vehicule | IVehiculeService | VehiculeServiceImpl | CRUD complet | Refuser un tarif journalier négatif |
| Equipement | IEquipementService | EquipementServiceImpl | CRUD complet | Refuser un libellé vide |
| Client | IClientService | ClientServiceImpl | CRUD complet | Refuser un nom ou un e-mail vide |
| Reservation | IReservationService | ReservationServiceImpl | CRUD complet | Refuser une date de fin antérieure à la date de début |
| Paiement | IPaiementService | PaiementServiceImpl | Lecture seule | Autoriser uniquement findById et findAll |
| Maintenance | IMaintenanceService | MaintenanceServiceImpl | CRUD complet | Refuser une date de fin antérieure à la date de début |

### Méthodes CRUD utilisées

Pour les huit services CRUD complets, nous avons implémenté :

- create() : créer une nouvelle entité.
- findById() : rechercher une entité par son identifiant.
- findAll() : récupérer toutes les entités.
- update() : modifier une entité existante.
- deleteById() : supprimer une entité.

Le service Paiement constitue une exception :
il contient uniquement findById() et findAll().

### Gestion des exceptions

Nous utilisons deux types d'exceptions :

- ResourceNotFoundException : lorsqu'une entité demandée
  n'existe pas dans la base de données.
- IllegalArgumentException : lorsqu'une règle métier
  n'est pas respectée.

### Adaptations aux entités du projet

Certaines règles métier ont été adaptées aux attributs
réellement présents dans notre modèle :

- Vehicule : l'entité ne possède pas de kilométrage.
  Nous vérifions donc le tarif journalier.
- Equipement : l'entité ne possède pas de quantité.
  Nous vérifions uniquement le libellé.
- Maintenance : l'entité ne possède pas de coût.
  Nous vérifions donc uniquement les dates.

Ces adaptations permettent de respecter la structure
des entités existantes sans ajouter d'attributs inutiles.
## C. Diagnostic des erreurs Spring

### 1. NoSuchBeanDefinitionException

**Description :**

Cette exception apparaît lorsque Spring ne trouve pas
un Bean nécessaire à l'injection de dépendances.

**Causes possibles :**

- La classe Service ne possède pas l'annotation @Service.
- La classe se trouve en dehors des packages analysés par Spring.
- Aucun Bean correspondant au type demandé n'est déclaré.

**Solution :**

Ajouter l'annotation @Service à la classe d'implémentation
et vérifier que celle-ci se trouve dans un package analysé
par Spring Boot.

Exemple :

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
}

Dans notre projet, les services se trouvent dans le package
tn.esprit.fareskhouloud4cce11.service.

Ils sont donc détectés par Spring Boot lorsque l'application
principale se trouve dans le package parent.


### 2. NoUniqueBeanDefinitionException

**Description :**

Cette exception apparaît lorsque plusieurs Beans
correspondent au même type et que Spring ne sait pas
lequel injecter.

**Exemple :**

Deux classes différentes implémentent la même interface
IContratService.

Lorsqu'une autre classe demande un IContratService,
Spring trouve deux implémentations possibles.

**Solutions :**

- Utiliser @Primary pour désigner l'implémentation
  à privilégier.
- Utiliser @Qualifier pour sélectionner explicitement
  le Bean souhaité.

Exemple avec @Primary :

@Primary
@Service
public class ContratServiceImpl implements IContratService {
// ...
}

Exemple avec @Qualifier :

public MonControleur(
@Qualifier("contratServiceImpl") IContratService service) {
this.service = service;
}


### 3. Dépendances circulaires

**Description :**

Une dépendance circulaire apparaît lorsque deux Beans
dépendent directement ou indirectement l'un de l'autre.

**Exemple :**

- ServiceA dépend de ServiceB.
- ServiceB dépend de ServiceA.

Spring ne peut pas construire ces deux objets normalement
avec l'injection par constructeur.

**Conséquences :**

L'application peut échouer au démarrage à cause
d'un cycle de dépendances.

**Solutions :**

- Réorganiser les responsabilités des services.
- Supprimer les dépendances inutiles.
- Extraire la logique commune dans un troisième service.

**Conclusion :**

L'injection par constructeur facilite la détection
des dépendances circulaires.

Une bonne séparation des responsabilités permet
d'éviter ce problème.
## D. Analyse SonarQube

### 1. Objectif

L'objectif de cette analyse est de détecter les problèmes
de qualité du code dans les packages service et exception.

Nous avons utilisé le plugin SonarQube for IDE dans IntelliJ.

### 2. Première anomalie : java:S6813

**Message :**
Remove this field injection and use constructor injection instead.

**Cause :**
Nous avons temporairement remplacé l'injection par constructeur
par une injection directe sur l'attribut avec @Autowired.

**Conséquence :**
Cette pratique rend les dépendances moins explicites
et complique les tests unitaires.

**Correction :**
Nous avons supprimé l'injection par attribut et restauré
l'injection par constructeur avec @RequiredArgsConstructor
et un Repository déclaré final.

**Statut :** Anomalie détectée et corrigée.


### 3. Deuxième anomalie : java:S1128

**Message :**
Remove this unused import 'lombok.RequiredArgsConstructor'.

**Cause :**
Après avoir retiré l'annotation @RequiredArgsConstructor
pendant l'expérience, son import était devenu inutile.

**Correction :**
Nous avons restauré l'annotation @RequiredArgsConstructor
et conservé son import, désormais utilisé.

**Statut :** Anomalie détectée et corrigée.


### 4. Test de la règle java:S112

**Objectif :**
Vérifier si SonarQube signale l'utilisation
d'une exception trop générique.

**Modification temporaire :**
Dans ContratServiceImpl.java, nous avons remplacé
ResourceNotFoundException par RuntimeException.

Exemple :

throw new RuntimeException(
"Contrat introuvable avec l'identifiant " + id
);

**Résultat :**
Après l'analyse, SonarQube for IDE affichait
"No issues to display".

La règle java:S112 n'a donc pas été signalée
lors de ce test.

**Correction :**
Nous avons restauré l'utilisation de
ResourceNotFoundException dans ContratServiceImpl.java.

**Statut :** Règle testée, anomalie non observée.


### 5. Bilan de l'analyse

| Règle SonarQube | Problème | Résultat |
|---|---|---|
| java:S6813 | Injection par attribut | Détectée et corrigée |
| java:S1128 | Import inutilisé | Détectée et corrigée |
| java:S112 | Exception trop générique | Testée, non signalée |

Deux anomalies ont été effectivement détectées
et corrigées.

Une troisième règle a été testée, mais elle n'a pas
été signalée par SonarQube for IDE.

### 6. Conclusion

L'analyse SonarQube nous a permis d'améliorer
la qualité du code et de confirmer l'intérêt
de l'injection par constructeur.

Nous avons également appris à distinguer
une anomalie réellement détectée d'une règle
testée sans signalement.
## E. Questions théoriques

### 1. Qu'est-ce que l'inversion de contrôle (IoC) ?

L'inversion de contrôle est un principe de conception
dans lequel la création et la gestion des objets sont
confiées à un conteneur externe.

Dans notre application, Spring Boot gère les objets
appelés Beans.

Par exemple, Spring crée automatiquement
ContratServiceImpl et lui fournit son Repository.

Le développeur n'a donc pas besoin d'instancier
manuellement le service avec new.

### 2. Qu'est-ce que l'injection de dépendances (DI) ?

L'injection de dépendances est un mécanisme qui permet
à un objet de recevoir les composants dont il a besoin,
au lieu de les créer lui-même.

Dans notre projet, les services reçoivent leurs
Repositories grâce à Spring.

Exemple :

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
}

L'annotation @RequiredArgsConstructor de Lombok
génère le constructeur nécessaire à l'injection.

### 3. Pourquoi privilégier l'injection par constructeur ?

L'injection par constructeur présente plusieurs avantages :

- Les dépendances sont obligatoires dès la création.
- Les attributs peuvent être déclarés final.
- Le code est plus facile à tester.
- Les dépendances sont clairement visibles.
- Les dépendances circulaires sont plus faciles à détecter.

C'est pourquoi nous avons utilisé l'injection
par constructeur dans les neuf services du projet.

### 4. Quel est le rôle de la couche Service ?

La couche Service contient la logique métier
de l'application.

Elle se situe entre les contrôleurs et les Repositories.

Elle permet notamment de :

- Vérifier les règles métier.
- Organiser les opérations CRUD.
- Gérer les erreurs métier.
- Séparer les responsabilités de l'application.

Exemple :

Dans VehiculeServiceImpl, nous refusons
un tarif journalier négatif avant d'enregistrer
le véhicule dans la base de données.

Le Repository, quant à lui, s'occupe
de l'accès aux données.

### 5. Pourquoi utiliser une interface pour chaque Service ?

Une interface définit les opérations disponibles
sans imposer leur implémentation.

Par exemple, IContratService déclare les méthodes :

- create()
- findById()
- findAll()
- update()
- deleteById()

La classe ContratServiceImpl fournit
leur implémentation.

Cette séparation permet :

- De réduire le couplage entre les composants.
- De faciliter les tests unitaires.
- De remplacer une implémentation sans modifier
  les classes qui utilisent l'interface.
- D'améliorer la maintenance du projet.

### Conclusion générale

Cet atelier nous a permis de mettre en pratique
l'inversion de contrôle et l'injection de dépendances
avec Spring Boot.

Nous avons créé neuf services métier en respectant
une architecture organisée en couches :

Controller -> Service -> Repository -> Base de données

Nous avons utilisé l'injection par constructeur,
implémenté les opérations CRUD, ajouté des validations
métier et géré les ressources inexistantes
avec ResourceNotFoundException.

Enfin, l'utilisation de SonarQube for IDE
nous a permis d'identifier et de corriger
des problèmes de qualité du code.