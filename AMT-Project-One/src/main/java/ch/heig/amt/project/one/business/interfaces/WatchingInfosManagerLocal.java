package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
import ch.heig.amt.project.one.model.WatchingInfo;

import javax.ejb.Local;
import java.util.List;

@Local
public interface WatchingInfosManagerLocal {
    /**
     * Insère un profil de visionnage dans la base de donnée
     * @param watchingInfo profil de visionnage à insérer
     * @return Insertion réussie ?
     */
    public boolean create(WatchingInfo watchingInfo);

    /**
     * Trouve les profils de visionnage lié à un spectateur
     * @param user utilisateur qui possède l'entité
     * @param viewer spectateur dont on souhaite lister les profils de visionnage
     * @param index index Index de base du profil de visionnage
     * @param offset Quantité de profils de visionnage maximum à retourner
     * @return Liste des profils de visionnage
     */
    public List<WatchingInfo> findByViewer(User user, Viewer viewer, int index, int offset);

    /**
     * Trouve les profils de visionnage lié à une série
     * @param user utilisateur qui possède l'entité
     * @param serie série dont on souhaite lister les profils de visionnage
     * @param index index Index de base du profil de visionnage
     * @param offset Quantité de profils de visionnage maximum à retourner
     * @return Liste des profils de visionnage
     */
    public List<WatchingInfo> findBySerie(User user, Serie serie, int index, int offset);

    /**
     * Trouve un profil de visionnage selon la paire de clés unique idSérie idViewer
     * @param user utilisateur qui possède l'entité
     * @param idSerie id de la série liée au profil de visionnage
     * @param idViewer id du spectateur lié au profil de visionnage
     * @return Profil de visionnage
     */
    public WatchingInfo findOne(User user, long idSerie, long idViewer);

    /**
     * Met à jour un profil de visionnage dans la base de donnée
     * @param watchingInfo profil de visionnage mis à jour (id doit correspondre à l'ancien)
     * @return Mise à jour réussie ?
     */
    public boolean update(WatchingInfo watchingInfo);

    /**
     * Supprime un profil de visionnage  appartenant à un user avec un id donné
     * @param User utilisateur à qui appartient le n profil de visionnage
     * @param idSerie id de la série liée au profil de visionnage
     * @param idViewer id du spectateur lié au profil de visionnage
     * @return Suppression réussie ?
     */
    public boolean delete(User User, long idSerie, long idViewer);

    /**
     * Retourne le nombre de profils de visionnage possédés par un utilisateur donné
     * @param user utilisateur dont on souhaite compter les profils de visionnage
     * @return nombre de profils de visionnage
     */
    public int count(User user);

    /**
     * Retourne le nombre de profil de visionnage liés à une série donnée
     * @param user utilisateur à qui appartient les entités
     * @param IDSerie série dont on compte les profils de visionnage
     * @return nombre de profil de visionnage
     */
    public int countBySerie(User user, long IDSerie);

    /**
     * Retourne le nombre de profil de visionnage liés à un spectateur donné
     * @param user utilisateur à qui appartient les entités
     * @param IDViewer spectateur dont on compte les profils de visionnage
     * @return nombre de profil de visionnage
     */
    public int countByViewer(User user, long IDViewer);

    /**
     * retourne un nombre de profils de visionnages
     * @param limit
     * @param offset
     * @return les profils de visionnage
     */
    public List<WatchingInfo> findAllTest(int limit, int offset);
}
