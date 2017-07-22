K-BA
===

* **URL**

  51.255.196.182

* **Content-Type**

  application/json

-----

## AUTH

### SUBSCRIBE

Incription d'un nouveai compte utilisateur sur notre plateforme

* **PATH**

  /auth/subscribe

* **METHOD**

  `POST`

* **BODY**

| FIELD | TYPE |
|---|---|
| firstname | String |
| lastname | String |
| email | String |
| password | String |
| pseudo | String |
| birthdate | String |
| address | String |
| zipcode | String |
| city | String |

  *Example*:

```json
{
  "firstname": "Frédéric",
  "lastname": "SANANES",
  "email": "test@test.com",
  "password": "12345678",
  "pseudo": "esgi",
  "birthdate": "01/01/1970",
  "address": "242 rue du faubourg saint Antoine",
  "zipcode": "75012",
  "city": "PARIS"
}
```

* **SUCCESS**

__Code__: **200** Ok

__Body__:

| FIELD | TYPE |
|---|---|
| uid | String |
| email | String |
| lastname | String |
| firstname | String |

*Example*:

```json
{
  "uid": "0000-0000-0000-0000",
  "email": "test@test.com",
  "lastname": "SANANES",
  "firstname": "Frédéric"
}
```

* **ERRORS**

__Code__: **401** Unauthorized


### LOGIN

Récupération d'un jeton de d'authentification pour accéder à l'ensemble de l'API

* **PATH**

  /auth/login

* **METHOD**

  `POST`

* **BODY**

| FIELD | TYPE |
|---|---|
| email | String |
| password | String |

  *Example*:

```json
{
  "email": "test@test.com",
  "password": "12345678"
}
```

* **SUCCESS**

__Code__: **200** Ok

__Headers__:

`Token`: Le jeton de connexion à l'API

__Body__:

| FIELD | TYPE |
|---|---|
| uid | String |
| email | String |
| lastname | String |
| firstname | String |

*Example*:

```json
{
  "uid": "0000-0000-0000-0000",
  "email": "test@test.com",
  "lastname": "SANANES",
  "firstname": "Frédéric"
}
```

* **ERRORS**

__Code__: **401** Unauthorized

----

## GROUP

### CREATE

Création d'un groupe pour l'utilisateur connecté

* **PATH**

  /group

* **METHOD**

  `POST`

* **HEADERS**

  `Authorization: Bearer ${Token}`

* **BODY**

| FIELD | TYPE |
|---|---|
| name | String |

  *Example*:

```json
{
  "name": "MyGroupName",
}
```

* **SUCCESS**

__Code__: **200** Ok

__Body__:

| FIELD | TYPE |
|---|---|
| uid | String |
| owner | User |
| name | String |

*Example*:

```json
{
  "uid": "0000-0000-0000-0000",
  "owner": {},
  "name": "MyGroupName",
}
```

* **ERRORS**

__Code__: **401** Unauthorized


### LIST

Récupération des groupes pour un utilisateur

* **PATH**

  /group

* **METHOD**

  `GET`

* **QUERY**

| FIELD | TYPE |
|---|---|
| user_uid | String |

  *Example*:

`/group?user_uid=0000-0000-0000-0000`

* **SUCCESS**

__Code__: **200** Ok


__Body__:

La résponse est sous forme de tableau

| FIELD | TYPE |
|---|---|
| uid | String |
| name | String |
| owner | User |
| members | [User] |
| lists | [List] |

*Example*:

```json
[
  {
    "uid": "0000-0000-0000-0000",
    "name": "MyGroupName",
    "owner": {},
    "members": [],
    "lists": []
  },
  {
    "uid": "0000-0000-0000-0001",
    "name": "MyOtherName",
    "owner": {},
    "members": [],
    "lists": []
  }
]
```

* **ERRORS**

__Code__: **401** Unauthorized

### ADD USER

Ajout d'un nouveau membre au groupe

* **PATH**

  /group/${group_uid}/user/${user_uid}

* **METHOD**

  `PUT`

* **PARAMS**

| FIELD | TYPE |
|---|---|
| group_uid | String |
| user_uid | String |

  *Example*:

`/group/0000-0000-0000-0000/user/0000-0000-0000-0002`

* **SUCCESS**

__Code__: **204** No content

* **ERRORS**

__Code__: **401** Unauthorized
