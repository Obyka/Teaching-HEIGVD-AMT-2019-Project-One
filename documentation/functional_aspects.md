# Aspects fonctionnels et implémentation
Notre application sert à gérer des séries, des spectateurs et des profils de visionnage (Un profil consite à lier un spectateur à une série et un temps de visionnage.)

Voici le buisness model de notre application:
![image](https://user-images.githubusercontent.com/28777250/68535312-b3598480-0340-11ea-862e-61a03e8e99af.png)

Chaque utilisateur peut créer ses entités et les gérer avec les méthodes CRUD (Create Read Update Delete)
Chaque entité possède son DAO ainsi que son interface de DAO (qui définit les opérations à implémenter)

Ainsi, voici nos routes et les opérations qu'elles permettent d'effectuer :

| Route      | Fonctionnalité(s) |
| :------------ | :------------- |
| /register     | Permet à un visiteur de s'inscrire |
| /login       | Permet à un utilisateur de se connecter |
| /logout       | Permet à un utilisateur de se déconnecter |
| /restreint/series| Permet à un utilisateur de visualiser toutes les séries |
| /restreint/viewers| Permet à un utilisateur de visualiser tous les spectateurs |
| /restreint/detailserie| Permet à un utilisateur de visualiser les détails d'une série (et ses profils de visionnage liés) |
| /restreint/detailviewer| Permet à un utilisateur de visualiser les détails d'un spectateur (et ses profils de visionnage liés)| 
| /restreint/series| Permet à un utilisateur de visualiser toutes les séries |
| /restreint/addserie| Permet à un utilisateur d'accéder au formulaire d'insertion des séries |
| /restreint/addviewer| Permet à un utilisateur d'accéder au formulaire d'insertion des spectateurs |
| /restreint/addwatchinginfo| Permet à un utilisateur d'accéder au formulaire d'insertion des profils de visionnage |
| /restreint/modifyserie| Permet à un utilisateur d'accéder au formulaire de modification d'une série |
| /restreint/modifyviewer| Permet à un utilisateur d'accéder au formulaire de modification d'un spectateur |
| /restreint/modifywatchinginfo| Permet à un utilisateur d'accéder au formulaire de modification d'une série |
| /restreint/deleteserie| Permet à un utilisateur de supprimer une série |
| /restreint/deleteviewer| Permet à un utilisateur de supprimer un spectateur |
| /restreint/deletewatchinginfo| Permet à un utilisateur de supprimer un profil de visionnage |

Afin de gérer la cohérence des routes 2 filtres nous permettent d'y gérer l'accès

| Filtre      | Fonctionnalité(s) | Route d'application |
| :---------- | :------------- | :------------- |
| loginFilter | Redirige vers la page de login si aucune connexion n'est établie | /restreint/* |
| LoggedFilter| Empêche les utilisateurs connectés de se re-connecter ou de créer un nouveau compte | /login |
| | | /register |

Pour gérer la connexion nous utilisons la méthode getSession().setAttribute de `HttpServletRequest request` afin de définir l'utilisateur connecté. Toujours par rapport à la connexion, nous utilisons une implémentation de l'algorithme `PBKDF2` avec un nombre d'itérations défini selon le standard du NIST.

Pour gérer la pagination, nous avons opté pour une solution personnelle :
Dans le DAO nous créons une méthode qui nous permet de récupérer un sous-ensemble de nos entités basé sur un index de départ et un offset gérés grâce à un paramètre GET. (example de requête SQL dans le DAO)
`"SELECT * FROM Serie WHERE OwnerID = ? LIMIT ?, ?"`

Dans le servlet, nous définissons un nombre d'entités souhaitées par page, nous calculons le nombre de page total (nbEntité / nbEntitéParPage) et nous passons les entités, et le nombre de page à la vue

Dans la vue, nous créons dynamiquement un tableau des entités reçues et les X boutons se réferont au X pages totale.
Ainsi, pour une requête renvoyant 100 entités, dans un servlet affichant 10 entités à la fois, nous aurions 10 pages.
Pour rendre la pagination plus agréable, nous regardons le nombre de page totale et la page courante, et générons trois "vues" de pagination différente

1) Nous sommes dans les premières pages

![image](https://user-images.githubusercontent.com/28777250/68535772-1e5a8980-0348-11ea-92d1-c7105fee2877.png)

2) Nous sommes dans les dernières pages

![image](https://user-images.githubusercontent.com/28777250/68535787-3e8a4880-0348-11ea-98ad-cce8a501d67b.png)

3) Nous sommes entre les deux

![image](https://user-images.githubusercontent.com/28777250/68535790-519d1880-0348-11ea-9fb7-d36e56276ecd.png)

Concernant les vues, nous avons utilisé un template `bootstrap` appelé `SB Admin 2` que nous avons customisé

Pour générer les données nous avons créé un script python qui utilise la librairie `Faker` pour avoir au moins une partie des entités qui sembleront réelles. Ce script renvoie simplement les requêtes `INSERT INTO` correspondantes. Comme nous avons trouvé cette méthode trop lente pour l'insertion, nous avons alors exporté en CSV la base nouvellement créée, puis utiliser cela dans notre script de création avec des requêtes `LOAD INFILE`
