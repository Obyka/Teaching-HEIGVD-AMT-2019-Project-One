package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.utils.PasswordHash;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UsersManager implements UsersManagerLocal {
    @Resource(lookup = "jdbc/amtDatasource")
    private DataSource dataSource;

    @Override
    public boolean create(String username, String password) {
        boolean created = false;
        PasswordHash ph = new PasswordHash();
        String finalHashedPass = "";
        try {
            finalHashedPass = ph.createHash(password);
        } catch (Exception e){
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.UsersManager.class.getName()).log(Level.SEVERE, null, e);
            return created;
        }
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User(Username, Password) VALUES (?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, finalHashedPass);
            int row = preparedStatement.executeUpdate();

            if(row == 1) {
                created = true;
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.UsersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return created;
    }

    @Override
    public boolean validConnection(String username, String password) {
        boolean connectionValid = false;
        PasswordHash ph = new PasswordHash();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                String passwordDB = rs.getString("Password");
                try{
                    if (ph.validatePassword(password, passwordDB)) {
                        connectionValid = true;
                    }
                } catch(Exception e){
                    Logger.getLogger(ch.heig.amt.project.one.business.DAO.SeriesManager.class.getName()).log(Level.SEVERE, null, e);
                }

            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.UsersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return connectionValid;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = User.builder().build();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                String passwordDB = rs.getString("Password");
                String usernameDB = rs.getString("Username");
                long id = rs.getLong("ID");

                user.setUsername(usernameDB);
                user.setPassword(passwordDB);
                user.setId(id);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(ch.heig.amt.project.one.business.DAO.UsersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }
}
