package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ViewersManagerLocal {
    /**
     * Insère un spectateur dans la base de donnée
     * @param viewer Spectateur à insérer
     * @return Insertion réussie ?
     */
    public boolean create(Viewer viewer);

    /**
     * Permet de récupérer une liste de spectateurs de taille offset apartenant à un utilisateur, depuis la valeur index
     * @param user Utilisateur à qui les spectateurs appartiennent
     * @param index Index de base de la première série
     * @param offset Quantité de spectateurs maximum à retourner
     * @return Liste de spectateurs récupérés depuis la base de donnée
     */
    public List<Viewer> findAll(User user, int index, int offset);

    /**
     * Récupère un spectateur appartenant à un utilisateur d'après son ID
     * @param user Utilisateur à qui le spectateur appartient
     * @param id ID du spectateur à retourner
     * @return Objet spectateur récupéré depuis la base de donnée -> objet vide ID = -1 si aucune spectateur n'a été trouvé
     */
    public Viewer findById(User user, long id);

    /**
     * Met à jour un spectateur dans la base de donnée
     * @param viewer Spectateur mis à jour (id doit correspondre à l'ancien)
     * @return Mise à jour réussie ?
     */
    public boolean update(Viewer viewer);

    /**
     * Supprime un spectateur appartenant à un user avec un id donné
     * @param user utilisateur à qui appartient le spectateur
     * @param id id du spectateur
     * @return Suppression réussie ?
     */
    public boolean delete(User user, long id);

    /**
     * Retourne le nombre de spectateurs possédés par un utilisateur donné
     * @param user utilisateur dont on souhaite compter les spectateurs
     * @return nombre de spectateurs
     */
    public int count(User user);

    }
