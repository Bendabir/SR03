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

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

**Les méthodes `POST`, `PUT` et `DELETE` sont désactivées pour le moment.** Les utilisateurs sont créés automatiquement via le retour du CAS. Leur modification n'a pas vraiment de sens. On peut éventuellement implémenter la suppression d'un utilisateur (uniquement sur l'utilisateur connecté ou si l'on possède les droits administrateur).

#### Jeux

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/games` | Retourne tous les jeux. |
| `POST /api/games` | Ajoute un jeu. |
| `GET /api/games/:game` | Retourne un jeu spécifique. |
| `PUT /api/games/:game` | Modifie un jeu. |
| `DELETE /api/games/:game` | Supprime un jeu. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

#### Consoles

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/consoles` | Retourne toutes les consoles. |
| `POST /api/consoles` | Ajoute une console. |
| `GET /api/consoles/:console` | Retourne une console spécifique. |
| `PUT /api/consoles/:console` | Modifie une console. |
| `DELETE /api/consoles/:console` | Supprime une console. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

#### Types de jeu

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/gameGenres` | Retourne tous les types. |
| `POST /api/gameGenres` | Ajoute un type. |
| `PUT /api/gameGenres/:gameGenre` | Modifie un type. |
| `DELETE /api/gameGenres/:gameGenre` | Supprime une type. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

#### Commandes

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/orders` | Retourne toutes les commandes de l'utilisateur connecté. |
| `GET /api/orders/all` | Retourne toutes les commandes. **Nécessite les droits administrateurs.** |
| `GET /api/orders/all/:num` | Retourne une commande. **Nécessite les droits administrateurs.** |
| `GET /api/orders/:num` | Retourne une commande de l'utilisateur connecté. |
| `GET /api/orders/:user` | Retourne toutes les commandes d'un utilisateur. **Nécessite les droits administrateurs.** |
| `GET /api/orders/:user/:num` | Retourne une commande d'un utilisateur. **Nécessite les droits administrateurs.** |

Toutes les méthodes sont protégées par une connexion. Certaines méthodes nécessitent des droits supplémentaires. 

#### Paniers

On se propose d'intégrer une route dans l'API qui retourne le panier actuel de l'utilisateur connecté.

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/cart` | Retourne le panier de l'utilisateur connecté. |
| `GET /api/cart/validate` | Sauvegarde le panier courant de l'utilisateur connecté en base (achat). |
| `POST /api/cart` | Ajoute un produit au panier de l'utilisateur connecté. |
| `PUT /api/cart/:product` | Modifie un produit dans le panier de l'utilisateur connecté. |
| `DELETE /api/cart/:product` | Supprime un produit du panier de l'utilisateur connecté. |
| `DELETE /api/cart` | Supprime tous les produits du panier de l'utilisateur connecté. |

`:product` identifie l'index du produit dans le panier (le premier article a l'index 0, le deuxième 1, etc.).

## Protections

Les méthodes `POST`, `PUT` et `DELETE` de l'API sont protégées par une session de connexion. La méthode `GET` peut l'être également. 

L'API peut éventuellement implémenter une sécurisation via token (tel que JWT). 

## Connexions

La méthode de connexion mise en place se fait à travers le CAS de l'UTC. Ainsi, aucun mot de passe n'est stocké en base. Toutes les données des utilisateurs sont issues du retour de la connexion CAS. Les utilisateurs ont différents statuts `user` (par défaut) ou `admin`.