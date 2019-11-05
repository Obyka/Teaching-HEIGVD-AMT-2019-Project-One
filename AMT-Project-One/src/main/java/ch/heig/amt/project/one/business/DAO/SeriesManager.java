package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.Serie;
import ch.heig.amt.project.one.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SeriesManager implements SeriesManagerLocal {
    @Resource(lookup = "jdbc/amtDatasource")
    private DataSource dataSource;

    @Override
    public boolean create(Serie serie) {
        boolean created = false;
        try {
            Connection connection = dataSource.getConnection();
            String querySql = "INSERT INTO Serie(Title, Producer, Synopsis, Genre, AgeRestriction, OwnerID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setString(1, serie.getTitle());
            preparedStatement.setString(2, serie.getProducer());
            preparedStatement.setString(3, serie.getSynopsis());
            preparedStatement.setString(4, serie.getGenre());
            preparedStatement.setInt(5, serie.getAgeRestriction());
            preparedStatement.setLong(6, serie.getOwner());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                created = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return created;
    }

    @Override
    public List<Serie> findAll(User user, int index, int offset) {
        List<Serie> series = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Serie WHERE OwnerID = ? LIMIT ?, ?");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("ID");
                long ownerID = rs.getLong("OwnerID");
                String title = rs.getString("Title");
                String producer = rs.getString("Producer");
                String synopsis = rs.getString("Synopsis");
                String genre = rs.getString("Genre");
                int ageRestriction = rs.getInt("AgeRestriction");
                Serie serie = Serie.builder().genre(genre).title(title).producer(producer).synopsis(synopsis).ageRestriction(ageRestriction).build();
                serie.setId(id);
                serie.setOwner(ownerID);
                series.add(serie);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return series;
    }

    @Override
    public Serie findById(User user, long id) {
        Serie serie = Serie.builder().build();
        serie.setId(-1);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Serie WHERE ID = ? AND OwnerID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                long idSerie = rs.getLong("ID");
                long ownerID = rs.getLong("OwnerID");
                String title = rs.getString("Title");
                String producer = rs.getString("Producer");
                String synopsis = rs.getString("Synopsis");
                String genre = rs.getString("Genre");
                int ageRestriction = rs.getInt("AgeRestriction");
                serie = Serie.builder().genre(genre).title(title).producer(producer).synopsis(synopsis).ageRestriction(ageRestriction).build();
                serie.setId(idSerie);
                serie.setOwner(ownerID);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return serie;
    }

    @Override
    public boolean update(Serie serie) {
        boolean modified = false;
        try {
            Connection connection = dataSource.getConnection();
            String querySql = "UPDATE Serie SET Title = ?, Producer = ?, Synopsis = ?, Genre = ?, AgeRestriction = ? WHERE ID = ? AND OwnerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setString(1, serie.getTitle());
            preparedStatement.setString(2, serie.getProducer());
            preparedStatement.setString(3, serie.getSynopsis());
            preparedStatement.setString(4, serie.getGenre());
            preparedStatement.setInt(5, serie.getAgeRestriction());
            preparedStatement.setLong(6, serie.getId());
            preparedStatement.setLong(7, serie.getOwner());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                modified = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
        }

        return modified;
    }

    @Override
    public boolean delete(User user, long id) {
        boolean deleted = false;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Serie WHERE ID = ? AND OwnerID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user.getId());
            int row = preparedStatement.executeUpdate();
            if(row == 1) {
                deleted = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return deleted;
    }

    public int count(User user) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM Serie WHERE OwnerID = ?");
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
