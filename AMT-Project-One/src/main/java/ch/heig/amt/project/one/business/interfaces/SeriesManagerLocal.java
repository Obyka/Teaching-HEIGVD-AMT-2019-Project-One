package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SeriesManagerLocal {
    /**
     * Insère un objet Série et l'insère dans la base de donnée
     * @param serie Série à insérer
     * @return Insertion réussie ?
     */
    public boolean create(Serie serie);

    /**
     * Permet de récupérer une liste de série de taille offset apartenant à un utilisateur, depuis la valeur index
     * @param user Utilisateur à qui les séries appartiennent
     * @param index Index de base de la première série
     * @param offset Quantité de série maximum à retourner
     * @return Liste de séries récupérées depuis la base de donnée
     */
    public List<Serie> findAll(User user, int index, int offset);

    /**
     * Récupère une série appartenant à un utilisateur d'après son ID
     * @param user Utilisateur à qui la série appartient
     * @param id ID de la série à retourner
     * @return Objet série récupéré depuis la base de donnée -> objet vide ID = -1 si aucune série n'a été trouvée
     */
    public Serie findById(User user, long id);

    /**
     * Met à jour une série dans la base de donnée
     * @param serie Série mise à jour (id doit correspondre à l'ancien)
     * @return Mise à jour réussie ?
     */
    public boolean update(Serie serie);

    /**
     * Supprime une série appartenant à un user avec un id donné
     * @param user utilisateur à qui appartient la série
     * @param id id de la série
     * @return Suppression réussie ?
     */
    public boolean delete(User user, long id);

    /**
     * Retourne le nombre de série possédées par un utilisateur donné
     * @param user utilisateur dont on souhaite compter les séries
     * @return nombre de séries
     */
    public int count(User user);
}
