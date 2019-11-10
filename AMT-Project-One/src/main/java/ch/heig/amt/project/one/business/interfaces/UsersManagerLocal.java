package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.User;

import javax.ejb.Local;

@Local
public interface UsersManagerLocal {
    /**
     * Crée un nouvel utilisateur
     * @param username username du futur utilisateur
     * @param password mot-de-passe non-hashé de l'utilisateur
     * @return Création réussie ?
     */
    public boolean create(String username, String password);

    /**
     * Test la validité de connexion d'un utilisateur
     * @param username username de l'utilisateur souhaitant se connecter
     * @param password mot-de-passe rentré par l'utilisateur
     * @return Connexion réussie ?
     */
    public boolean validConnection(String username, String password);

    /**
     * Obtenir un utilisateur d'après son nom d'utilisateur
     * @param username nom d'utilisateur utilisé pour la recherche
     * @return Objet user retourné par la recherche -> ID = -1 si pas de résultat
     */
    public User findUserByUsername(String username);
}
