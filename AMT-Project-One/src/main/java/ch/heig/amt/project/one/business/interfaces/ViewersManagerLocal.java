package ch.heig.amt.project.one.business.interfaces;

import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ViewersManagerLocal {
    public boolean create(Viewer viewer);
    public List<Viewer> findAll(User user, int index, int offset);
    public Viewer findById(User user, long id);
    public boolean update(Viewer viewer);
    public boolean delete(User user, long id);
    public int count(User user);

    }
