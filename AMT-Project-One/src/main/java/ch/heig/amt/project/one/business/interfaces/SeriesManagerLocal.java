package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SeriesManagerLocal {
    public boolean create(Serie serie);
    public List<Serie> findAll(User user, int index, int offset);
    public Serie findById(User user, long id);
    public boolean update(Serie serie);
    public boolean delete(User user, long id);
    public int count(User user);
}
