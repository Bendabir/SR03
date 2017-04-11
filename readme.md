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

![MCD de la BDD](MCD.png)

On se propose d'ajouter un clef artificielle `ID` pour les jeux afin de simplifier leur référencement. Les commandes (`orders`) sont identifiées par un ID unique également.

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

#### Consoles

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/consoles` | Retourne toutes les consoles. |
| `POST /api/consoles` | Ajoute une console. |
| `GET /api/consoles/:console` | Retourne une console spécifique. |
| `PUT /api/consoles/:console` | Modifie une console. |
| `DELETE /api/consoles/:console` | Supprime une console. |

#### Types de jeu

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/gameGenres` | Retourne tous les types. |
| `POST /api/gameGenres` | Ajoute un type. |
| `PUT /api/gameGenres/:gameGenre` | Modifie un type. |
| `DELETE /api/gameGenres/:gameGenre` | Supprime une type. |

#### Commandes

| Chemin | Description |
|:------:|:-----------:|
| `POST /api/orders` | Ajoute une commande. |
| `GET /api/orders/:user` | Retourne toutes les commandes d'un utilisateur. |
| `GET /api/orders/:user/:num` | Retourne une commande d'un utilisateur. |

A voir le type de route : `/api/users/:user/orders` ou `/api/orders/:user` ou `/api/orders` qui retourne uniquement les commandes de l'utilisateur logué.

#### Paniers

On se propose d'intégrer une route dans l'API qui retourne le panier actuel de l'utilisateur connecté.

## Protections

Les méthodes `POST`, `PUT` et `DELETE` de l'API sont protégées par une session de connexion. La méthode `GET` peut l'être également. 

L'API peut éventuellement implémenter une sécurisation via token (tel que JWT).

Les mots de passes sont tous hashés en base de données tel que `hashed_password = hash(username + password)`. Ainsi, deux utilisateurs peuvent avoir le même mot de passe sans avoir un hash identique. La fonction de hash reste à définir. 

## Connexions

La méthode de connexion mise en place se fait à travers le CAS de l'UTC.  