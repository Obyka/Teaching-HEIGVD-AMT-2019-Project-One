package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ViewersManager implements ViewersManagerLocal {
    @Resource(lookup = "jdbc/amtDatasource")
    private DataSource dataSource;

    @Override
    public boolean create(Viewer viewer) {
        boolean created = false;
        try {
            Connection connection = dataSource.getConnection();
            String querySql = "INSERT INTO Viewer(Firstname, Lastname, Username, Genre, Birthdate, OwnerID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setString(1, viewer.getFirstname());
            preparedStatement.setString(2, viewer.getLastname());
            preparedStatement.setString(3, viewer.getUsername());
            preparedStatement.setString(4, viewer.getGenre());
            java.util.Date dateViewer = viewer.getBirthDate();
            java.sql.Date dateViewerDB = new java.sql.Date(dateViewer.getTime());
            preparedStatement.setDate(5, dateViewerDB);
            preparedStatement.setLong(6, viewer.getOwner());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                created = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return created;
    }

    @Override
    public List<Viewer> findAll(User user, int index, int offset) {
        List<Viewer> viewers = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Viewer WHERE OwnerID = ? LIMIT ?, ?");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                long id = rs.getLong("ID");
                String firstname = rs.getString("Firstname");
                String lastname = rs.getString("Lastname");
                String username = rs.getString("Username");
                String genre = rs.getString("Genre");
                java.util.Date birthdate = rs.getDate("Birthdate");
                long ownerID = rs.getLong("OwnerID");

                Viewer viewer = Viewer.builder().firstname(firstname).lastname(lastname).username(username).genre(genre).birthDate(birthdate).build();
                viewer.setId(id);
                viewer.setOwner(ownerID);
                viewers.add(viewer);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return viewers;
    }

    @Override
    public Viewer findById(User user, long id) {
        Viewer viewer = Viewer.builder().build();
        viewer.setId(-1);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Viewer WHERE ID = ? AND OwnerID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                long idDb = rs.getLong("ID");
                String firstname = rs.getString("Firstname");
                String lastname = rs.getString("Lastname");
                String username = rs.getString("Username");
                String genre = rs.getString("Genre");
                java.util.Date birthdate = rs.getDate("Birthdate");
                long ownerID = rs.getLong("OwnerID");

                viewer = Viewer.builder().firstname(firstname).lastname(lastname).username(username).genre(genre).birthDate(birthdate).build();
                viewer.setId(idDb);
                viewer.setOwner(ownerID);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return viewer;
    }

    @Override
    public boolean update(Viewer viewer) {
        boolean updated = false;
        try {
            Connection connection = dataSource.getConnection();
            String querySql = "UPDATE Viewer SET Firstname = ?, Lastname = ?, Username = ?, Genre = ?, Birthdate = ? WHERE ID = ? AND OwnerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setString(1, viewer.getFirstname());
            preparedStatement.setString(2, viewer.getLastname());
            preparedStatement.setString(3, viewer.getUsername());
            preparedStatement.setString(4, viewer.getGenre());
            java.util.Date dateViewer = viewer.getBirthDate();
            java.sql.Date dateViewerDB = new java.sql.Date(dateViewer.getTime());
            preparedStatement.setDate(5, dateViewerDB);
            preparedStatement.setLong(6, viewer.getId());
            preparedStatement.setLong(7, viewer.getOwner());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                updated = true;
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return updated;
    }

    @Override
    public boolean delete(User user, long id) {
        boolean deleted = false;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Viewer WHERE ID = ? AND OwnerID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user.getId());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                deleted = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return deleted;
    }

    public int count(User user) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from Viewer where OwnerID = ?");
            preparedStatement.setLong(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int nbRecord = rs.getInt("count(*)");
            preparedStatement.close();
            connection.close();
            return nbRecord;
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.ViewersManager.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }
}
