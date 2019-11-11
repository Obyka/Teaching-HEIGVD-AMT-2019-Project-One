# Projet 1 d'AMT
## Contexte
Pour le projet 1 du cours d'AMT, nous avons conceptualisé et développé un site web avec Java EE permettant de manipuler des entités (Séries, Spectateurs, Profils de visionnage) afin d'appliquer les techniques et pattern enseignés durant les cours.

## Déploiement de l'architecture
Voici les instructions pour déployer le projet à l'aide de `docker-compose
- Cloner le repo
- Lancer `start.sh` /!\ Stoppera l'infrastructure de test, si cette dernière est démarrée

Après un certain temps (autour d'une minute dû à la création d'un million d'entités dans la DB), le projet est prêt à être utiliser et trois containters ont été déployés, examinons-les ensemble.

### payara: serveur d'application
Nous déployons notre application (automatiquement) dans ce container. 
- L'application se trouve à l'adresse `localhost:8080/AMT-project-one`
- La console d'administration se trouve à l'adresse `localhost:4848` avec la paire de creds admin:admin

### db: serveur MySQL
Au démarrage, le script `create.sql` va être placé dans le répértoire `/docker-entrypoint-initdb.d` de l'image MySQL et va donc être lancé par le container. Ainsi, le schéma de notre database est créé, et les données insérées. 

Le sous-dossier `volumeSQL` est mappé au répértoire /docker-entrypoint-initdb.d
Les paramètres `--max-allowed-packet=50M --secure-file-priv=/docker-entrypoint-initdb.d` sont exécutées par le docker-compose.yml et servent respectivement à augmenter la taille maximum des fichiers lisibles par MySQL, et à exécuter la requête LOAD INFILE deouis le volume mappé.

Le mot-de-passe de l'utilisateur root est `amt_project_1_pwd`

### adminer: interface de gestion de la base de données
Au port `9090`, il est possible d'accéder à une instance d'adminer permettant de gérer la base de données sus-mentionnée. 

## Utilisation de notre site Web
Deux utilisateurs sont déjà créé au lancement du projet

| Nom d'utilisateur | Mot-de-passe |
|-------------------|--------------|
| Obyka             | password     |
| JoLaBanane98      | password     |

**Il est fortement conseillé de se connecter en tant qu'Obyka car il est propriétaire des entités pré-générees.**

## Description de l'implémentation et des aspects fonctionnels
Nous discutons de cela dans un fichier markdown à part pour plus de clarté.
Vous pouvez le retrouver [ici](documentation/functional_aspects.md)

## Tests

Pour lancer les tests, il faut :

- Lancer `test.sh` à la racine du projet /!\ Stoppera l'infrastructure de production, si cette dernière est démarrée
- Attendre que payara ait fini de se démarrer
- Se déplayer dans le répertoire `AMT-Project-One`
- Lancer mvn clean test

Il y aura sûrement votre certificat pour payara à importer (il a été discuté que cette manipulation sera faite de votre côté)

Nous avons documenté les tests dans un fichier markdown à part pour plus de clarté.
Vous pouvez le retrouver [ici](documentation/testing.md)

Un fichier séparé a également été créé pour les tests de charge, contenant une analyse de nos résultats.
Vous pouvez le retrouver [ici](documentation/load_testing.md)

## Liste des bugs connus
Nous avons listés les bugs connus dans un fichier markdown à part pour plus de clarté.
Vous pouvez le retrouver [ici](documentation/known_bugs.md)
