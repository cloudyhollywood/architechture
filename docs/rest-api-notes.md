# Atelier 5 — Spring MVC et API REST

## 1. Objectif

L'objectif de cet atelier est d'exposer les fonctionnalités de
l'application de location de véhicules à travers des API REST
développées avec Spring Boot et Spring MVC.

## 2. Technologies utilisées

- Java 17
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- MySQL
- Postman
- Swagger / OpenAPI

## 3. Architecture

L'application utilise une architecture en couches :

Controller → Service → Repository → Base de données MySQL

Les contrôleurs REST se trouvent dans le package :

`tn.esprit.fareskhouloud4cce11.web.controller`

## 4. API REST — Agence

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | /agences | Récupérer toutes les agences |
| GET | /agences/{id} | Récupérer une agence par ID |
| POST | /agences | Ajouter une agence |
| PUT | /agences/{id} | Modifier une agence |
| DELETE | /agences/{id} | Supprimer une agence |

## 5. API REST — Véhicule

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | /vehicules | Récupérer tous les véhicules |
| GET | /vehicules/{id} | Récupérer un véhicule par ID |
| POST | /vehicules | Ajouter un véhicule |
| PUT | /vehicules/{id} | Modifier un véhicule |
| DELETE | /vehicules/{id} | Supprimer un véhicule |

## 6. API REST — Contrat

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | /contrats | Récupérer tous les contrats |
| GET | /contrats/{id} | Récupérer un contrat par ID |
| POST | /contrats | Ajouter un contrat |
| PUT | /contrats/{id} | Modifier un contrat |
| DELETE | /contrats/{id} | Supprimer un contrat |

## 7. Codes HTTP

Les API REST utilisent les codes HTTP suivants :

- 200 OK : lecture ou modification réussie.
- 201 Created : création réussie.
- 204 No Content : suppression réussie.
- 400 Bad Request : données invalides.
- 404 Not Found : ressource introuvable.

Les erreurs sont gérées par `GlobalExceptionHandler`.

## 8. Tests avec Postman

Les opérations CRUD ont été testées avec Postman.

Les scénarios vérifiés comprennent :

- Récupération des ressources avec GET.
- Création des ressources avec POST.
- Modification des ressources avec PUT.
- Suppression des ressources avec DELETE.
- Test de l'erreur 404 avec un identifiant inexistant.
- Test de l'erreur 400 avec un montant de contrat négatif.

## 9. Documentation Swagger

La documentation interactive des API est générée
automatiquement avec SpringDoc OpenAPI.

URL Swagger UI :

http://127.0.0.1:8082/api/swagger-ui/index.html

URL OpenAPI JSON :

http://127.0.0.1:8082/api/v3/api-docs

## 10. Conclusion

Cet atelier a permis de développer et tester des API REST
avec Spring Boot, Spring MVC et Postman.

Swagger UI permet de consulter la documentation des endpoints
et de tester les API depuis un navigateur.