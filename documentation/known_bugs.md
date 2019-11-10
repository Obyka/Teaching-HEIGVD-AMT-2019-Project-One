# Bugs connus
- Avec certaines version de Firefox, il n'est pas possible d'obtenir de session sur l'url `localhost` et donc impossible de se connecter (implémentation du navigateur). Comme solution temporaire, soit changer de navigateur, soit ajouter un alias à localhost dans le fichier hosts (e.g AMT)

- Les tests Arquilian ne fonctionne qu'une seule fois (au déploiement) à cause du problème de conception mentionné ci-dessous. Pour les relancer, il faut redéployer le projets.

## Erreur de conception
Dans nos DAO, nos méthodes de création renvoyoient un boolean pour savoir si la création s'est déroulée correctement. Par exemple :
```java
public boolean create(Serie serie);
```
Or, nous avons remarqué lors de l'écriture des tests Arquilian que nous avions besoin de l'ID de l'entité créée à la place de ce boolean. Sans ça, nous n'avons aucun moyen efficace de connaître en tout temps le dernier ID généré par une table de la BDD, ce qui complique les tests.
