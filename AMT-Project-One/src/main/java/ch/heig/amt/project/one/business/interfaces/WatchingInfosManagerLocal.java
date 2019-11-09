package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
import ch.heig.amt.project.one.model.WatchingInfo;

import javax.ejb.Local;
import java.util.List;

@Local
public interface WatchingInfosManagerLocal {
    public boolean create(WatchingInfo watchingInfo);
    public List<WatchingInfo> findByViewer(User user, Viewer viewer, int index, int offset);
    public List<WatchingInfo> findBySerie(User user, Serie serie, int index, int offset);
    public WatchingInfo findOne(User user, long idSerie, long idViewer);
    public boolean update(WatchingInfo watchingInfo);
    public boolean delete(User User, long idSerie, long idViewer);
    public int count(User user);
    public int countBySerie(User user, long IDSerie);
    public int countByViewer(User user, long IDViewer);
    public List<WatchingInfo> findAllTest(int limit, int offset);
}
