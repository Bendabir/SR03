# Projet de SR03

## TODO

* Check sur les stocks lors de l'ajout d'une commande
* Ajout de tooltip pour expliciter les genres ?
* Fonctionnalité de tri et de recherche
* Retravailler la connexion CAS (validateur de connexion uniquement et non interface ~ proxy)

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

### Server-side

* Paramétrage et utilisation d'un serveur d'application JAVA (WildFly 10.1).
* Mise en place d'une BDD.
* En JAVA mise en place de plusieurs webservices REST qui retourneront des résultats de la BDD.
* Mettre en pratique la sécurisation de cette partie.

### Client-side

* Développement d'un site web en JAVA ou PHP qui va intéragir avec vos webservices de la 1er partie.
* Développement d'une application mobile ( Android ou IOS ) qui va intéragir avec vos webservices de la 1er partie.
* Mettre en pratique la sécurisation de cette partie.

## Réalisation côté serveur

### Structure de la BDD

On implémente le modèle relationnel suivant :

![MCD de la BDD](MCD.png)

On se propose d'ajouter un clef artificielle `ID` pour les jeux afin de simplifier leur référencement. Les commandes (`orders`) sont identifiées par un ID unique également.

### API

Le modèle REST est utilisé. On définit les chemins ci-dessous. On utilisera le format JSON comme format de données dans les retours de l'API.

#### Utilisateurs

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/users` | Retourne tous les utilisateurs. |
| `POST /api/users` | Ajoute un utilisateur. |
| `GET /api/users/:user` | Retourne un utilisateur spécifique (ou un code 404 si l'utilisateur n'existe pas). |
| `PUT /api/users/:user` | Modifie un utilisateur. |
| `DELETE /api/users/:user` | Supprime un utilisateur. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

**Les méthodes `POST`, `PUT` et `DELETE` sont désactivées pour le moment.** Les utilisateurs sont créés automatiquement via le retour du CAS. Leur modification n'a pas vraiment de sens. On peut éventuellement implémenter la suppression d'un utilisateur (uniquement sur l'utilisateur connecté ou si l'on possède les droits administrateur).

##### `GET /api/users`

```json
[
  {
    "username": "briviere",
    "firstName": "Benjamin",
    "lastName": "Rivière",
    "status": "admin"
  },
  {
    "username": "colinajo",
    "firstName": "Jose Maria",
    "lastName": "Colina",
    "status": "user"
  }
]
```

##### `GET /api/users/:user`

```json
{
  "username": "briviere",
  "firstName": "Benjamin",
  "lastName": "Rivière",
  "status": "admin"
}
``` 

#### Jeux

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/games` | Retourne tous les jeux. |
| `POST /api/games` | Ajoute un jeu. |
| `GET /api/games/:game` | Retourne un jeu spécifique. |
| `PUT /api/games/:game` | Modifie un jeu. |
| `DELETE /api/games/:game` | Supprime un jeu. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

##### `GET /api/games`

```json
[
  {
    "id": 1,
    "title": "Rocket League",
    "console": "PS4",
    "price": 10,
    "releaseDate": "2015-07-07",
    "stock": 100,
    "genres": [
      "Action",
      "Sport"
    ]
  },
  {
    "id": 2,
    "title": "Middle-Earth: Shadow of Mordor",
    "console": "PC",
    "price": 4.61,
    "releaseDate": "2014-09-30",
    "stock": 5,
    "genres": [
      "Action",
      "Aventure"
    ]
  },
  {
    "id": 3,
    "title": "Middle-earth: Shadow of War",
    "console": "PC",
    "price": 60,
    "releaseDate": "2017-08-22",
    "stock": 1000,
    "genres": [
      "Action",
      "Aventure"
    ]
  },
  {
    "id": 4,
    "title": "Minecraft",
    "console": "PC",
    "price": 20,
    "releaseDate": "2010-01-01",
    "stock": 10000,
    "genres": [
      "Création",
      "Open world",
      "Sandbox"
    ]
  }
]
```

##### `POST /api/games`

Paramètres :

```json
{
  "title": "Le Solitaire",
  "console": "PC",
  "price": 0,
  "releaseDate": "2000-01-01",
  "stock": 10000,
  "genres": []
}
```

Retour :

```json
{
  "id": 6
}
```

##### `GET /api/games/:game`

Retour :

```json
{
  "id": 4,
  "title": "Minecraft",
  "console": "PC",
  "price": 20,
  "releaseDate": "2010-01-01",
  "stock": 10000,
  "genres": [
    "Création",
    "Open world",
    "Sandbox"
  ]
}    
```

##### `PUT /api/games/:game`

Paramètres :

```json
{
  "title": "Minecraft",
  "console": "PC",
  "price": 15,
  "releaseDate": "2000-01-01",
  "stock": 10000,
  "genres": ["OpenWorld"]
}
```

Retour :

```json
{
  "id": 4
}
```

##### `DELETE /api/games/:game`

Retour :

```json
true
```

#### Consoles

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/consoles` | Retourne toutes les consoles. |
| `POST /api/consoles` | Ajoute une console. |
| `GET /api/consoles/:console` | Retourne une console spécifique. |
| `PUT /api/consoles/:console` | Modifie une console. |
| `DELETE /api/consoles/:console` | Supprime une console. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

##### `GET /api/consoles`

Retour :

```json
[
  {
    "name": "GameBoy",
    "launchedDate": "2017-04-04"
  },
  {
    "name": "PC",
    "launchedDate": "2017-04-06"
  },
  {
    "name": "PS1",
    "launchedDate": "2017-04-06"
  },
  {
    "name": "PS2",
    "launchedDate": "2017-04-06"
  },
  {
    "name": "PS3",
    "launchedDate": "2017-04-06"
  },
  {
    "name": "PS4",
    "launchedDate": "2017-04-06"
  },
  {
    "name": "xBox",
    "launchedDate": "2001-01-01"
  },
  {
    "name": "xBox360",
    "launchedDate": "2006-05-01"
  }
]
```

##### `POST /api/consoles`

Paramètres :

```json
{
  "name": "Wii",
  "launchedDate": "2006-01-01"
}
```

Retour :

```json
{
  "name": "Wii"
}
```

##### `GET /api/consoles/:console`

Retour :

```json
{
  "name": "xBox360",
  "launchedDate": "2006-05-01"
}   
```

##### `PUT /api/consoles/:console`

Paramètres :

```json
{
  "name": "PC",
  "launchedDate": "1970-01-01"
}
```

Retour : 

```json
{
  "name": "PC"
}
```

##### `DELETE /api/consoles/:console`

Retour : 

```json
true
```

#### Types de jeu

| Chemin | Description |
|:------:|:-----------:|
| `GET /api/gameGenres` | Retourne tous les types. |
| `POST /api/gameGenres` | Ajoute un type. |
| `PUT /api/gameGenres/:gameGenre` | Modifie un type. |
| `DELETE /api/gameGenres/:gameGenre` | Supprime une type. |

Les méthodes `POST`, `PUT` et `DELETE` sont protégées : un utilisateur doit être connecté et il doit posséder les droits administrateurs.

##### `GET /api/gameGenres`

Retour : 

```json
[
  {
    "name": "Course"
  },
  {
    "name": "Création"
  },
  {
    "name": "Fiction interactive"
  },
  {
    "name": "FPS"
  },
  {
    "name": "Gestion"
  },
  {
    "name": "God Game"
  },
  {
    "name": "Grande stratégie"
  },
  {
    "name": "Hack'n'slash"
  },
  {
    "name": "Infiltration"
  },
]
```

##### `POST /api/gameGenres`

Paramètres : 

```json
{
  "name": "OpenWorld"
}
```

Retour :

```json
{
  "name": "OpenWorld"
}
```

##### `GET /api/gameGenres/:gameGenre`

Retour : 

```json
{
  "name": "Combat"
}    
```

##### `PUT /api/gameGenres/:gameGenre`

Paramètres :

```json
{
  "name": "Action"
}
```

Retour :

```json
{
  "name": "Action"
}
```

##### `DELETE /api/gameGenres/:gameGenre`

Retour :

```json
true
```

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

##### `GET /api/orders`

Retour : 

```json
[
  {
    "num": 1,
    "date": "2017-04-07",
    "lines": [
      {
        "game": {
          "title": "Minecraft",
          "console": "PC"
        },
        "unitPrice": 20,
        "quantity": 1
      }
    ]
  },
  {
    "num": 2,
    "date": "2017-04-07",
    "lines": [
      {
        "game": {
          "title": "Middle-Earth: Shadow of Mordor",
          "console": "PC"
        },
        "unitPrice": 45,
        "quantity": 1
      },
      {
        "game": {
          "title": "Middle-earth: Shadow of War",
          "console": "PC"
        },
        "unitPrice": 5.6,
        "quantity": 1
      }
    ]
  },
  {
    "num": 6,
    "date": "2017-04-17",
    "lines": [
      {
        "game": {
          "title": "Rocket League",
          "console": "PS4"
        },
        "unitPrice": 20,
        "quantity": 1
      }
    ]
  },
]
```

##### `GET /api/orders/all`

Retour : 

```json
[
  {
    "num": 1,
    "date": "2017-04-07",
    "user": "briviere",
    "lines": [
      {
        "game": {
          "title": "Minecraft",
          "console": "PC"
        },
        "unitPrice": 20,
        "quantity": 1
      }
    ]
  },
  {
    "num": 2,
    "date": "2017-04-07",
    "user": "briviere",
    "lines": [
      {
        "game": {
          "title": "Middle-Earth: Shadow of Mordor",
          "console": "PC"
        },
        "unitPrice": 45,
        "quantity": 1
      },
      {
        "game": {
          "title": "Middle-earth: Shadow of War",
          "console": "PC"
        },
        "unitPrice": 5.6,
        "quantity": 1
      }
    ]
  },
  {
    "num": 3,
    "date": "2017-04-16",
    "user": "colinajo",
    "lines": [
      {
        "game": {
          "title": "Middle-earth: Shadow of War",
          "console": "PC"
        },
        "unitPrice": 10,
        "quantity": 1
      }
    ]
  },
  {
    "num": 6,
    "date": "2017-04-17",
    "user": "briviere",
    "lines": [
      {
        "game": {
          "title": "Rocket League",
          "console": "PS4"
        },
        "unitPrice": 20,
        "quantity": 1
      }
    ]
  },
]
```

##### `GET /api/orders/all/:num`

Retour : 

```json
{
  "num": 3,
  "date": "2017-04-16",
  "user": "colinajo",
  "lines": [
    {
      "game": {
        "title": "Middle-earth: Shadow of War",
        "console": "PC"
      },
      "unitPrice": 10,
      "quantity": 1
    }
  ]
}
```

##### `GET /api/orders/:num`

Retour : 

```json
{
  "num": 2,
  "date": "2017-04-07",
  "lines": [
    {
      "game": {
        "title": "Middle-Earth: Shadow of Mordor",
        "console": "PC"
      },
      "unitPrice": 45,
      "quantity": 1
    },
    {
      "game": {
        "title": "Middle-earth: Shadow of War",
        "console": "PC"
      },
      "unitPrice": 5.6,
      "quantity": 1
    }
  ]
}
```

##### `GET /api/orders/:user`

Retour : 

```json
[
  {
    "num": 3,
    "date": "2017-04-16",
    "lines": [
      {
        "game": {
          "title": "Middle-earth: Shadow of War",
          "console": "PC"
        },
        "unitPrice": 10,
        "quantity": 1
      }
    ]
  }
]
```

##### `GET /api/orders/:user/:num`

Retour : 

```json
{
  "num": 6,
  "date": "2017-04-17",
  "lines": [
    {
      "game": {
        "title": "Rocket League",
        "console": "PS4"
      },
      "unitPrice": 20,
      "quantity": 1
    }
  ]
}
```

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

##### `GET /api/cart`

Retour :

```json
[
  {
    "game": {
      "id": 3,
      "title": "Middle-earth: Shadow of War",
      "console": "PC"
    },
    "quantity": 1
  }
]
```

##### `GET /api/cart/validate`

Retour : 

```json
{
  "num": 7
}
```

##### `POST /api/cart`

Paramètres :

```json
{
  "game": 3,
  "quantity": 1
}
```

Retour : 

```json
{
  "product": 0
}
```

##### `PUT /api/cart/:product`

Paramètres : 

```json
{
  "quantity": 5
}
```

Retour : 

```json
{
  "product": 2
}
```

##### `DELETE /api/cart/:product`

Retour : 

```json
true
```

##### `DELETE /api/cart`

Retour : 

```json
true
```


### Protections

Les méthodes `POST`, `PUT` et `DELETE` de l'API sont protégées par une session de connexion. La méthode `GET` peut l'être également. 

L'API peut éventuellement implémenter une sécurisation via token (tel que JWT). 

### Connexions

La méthode de connexion mise en place se fait à travers le CAS de l'UTC. Ainsi, aucun mot de passe n'est stocké en base. Toutes les données des utilisateurs sont issues du retour de la connexion CAS. Les utilisateurs ont différents statuts `user` (par défaut) ou `admin`.

## Réalisation côté client

### Version web

#### CSS

On utilise le framework Material Design Lite (MDL) de Google. Il ne fournit qu'un framework CSS simple et efficace (sans framework JavaScript).

Disponible [ici](https://getmdl.io).

#### JavaScript

Afin de simplifier nos requêtes et plus généralement notre code, nous avons développé notre propre framework JavaScript sur un concept similaire à celui d'Angular.

### Application Android