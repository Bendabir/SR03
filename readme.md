# Projet de SR03

## Consignes 

Le but de ce projet est de faire une vente en ligne de jeux vidéos.

Votre projet devra au moins suivre les éléments suivant :
* La liste des jeux devront être stockées dans une BDD.
* Chaque jeux aura comme informations (titre, type de console de jeux , tarif)
* Mise en place de webservices REST qui retourneront les résultats issue de la BDD.
* Les clients web et mobile devront intéragir avec vos webservices.
* Les clients devront être protégés via une session de connexion.
* Prévoir un système de panier pour la partie vente.
* L'utilisation de frameworks javascript est interdit, sauf si vous développez le votre.

## Structure de la BDD

On implémente le modèle relationnel suivant :

![UML de la BDD](UML.png)

On se propose d'ajouter un clef artificielle `ID` pour les jeux afin de simplifier leur référencement. 

## API

Le modèle REST est utilisé. On définit les chemins ci-dessous. On utilisera le format JSON comme format de données dans les retours de l'API.

#### Utilisateurs

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/users` | Retourne tous les utilisateurs. |
| `POST /api/users` | Ajoute un utilisateur. |
| `GET /api/users/:user` | Retourne un utilisateur spécifique. |
| `PUT /api/users/:user` | Modifie un utilisateur. |
| `DELETE /api/users/:user` | Supprime un utilisateur. |

#### Jeux

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/games` | Retourne tous les jeux. |
| `POST /api/games` | Ajoute un jeu. |
| `GET /api/games/:game` | Retourne un jeu spécifique. |
| `PUT /api/games/:game` | Modifie un jeu. |
| `DELETE /api/games/:game` | Supprime un jeu. |

#### Paniers

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/users/:user/cart` | Retourne le panier d'un utilisateur. |
| `POST /api/users/:user/cart` | Ajoute un item dans le panier d'un utilisateur. |
| `PUT /api/users/:user/cart` | Modifie un item dans le panier d'un utilisateur. |
| `DELETE /api/users/:user/cart` | Supprime un item dans le panier d'un utilisateur. |

## Protections

Les méthodes `POST`, `PUT` et `DELETE` de l'API sont protégées par une session de connexion. La méthode `GET` peut l'être également. 

L'API peut éventuellement implémenter une sécurisation via token (tel que JWT).

Les mots de passes sont tous hashés en base de données tel que `hashed_password = hash(username + password)`. Ainsi, deux utilisateurs peuvent avoir le même mot de passe sans avoir un hash identique. La fonction de hash reste à définir.  