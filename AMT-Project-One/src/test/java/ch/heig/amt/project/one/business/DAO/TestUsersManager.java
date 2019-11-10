/*package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
import ch.heig.amt.project.one.model.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;

import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class TestUsersManager {
    @EJB
    UsersManagerLocal usersManagerLocal;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAUser() throws DuplicateKeyException, SQLException {
        boolean created = usersManagerLocal.create("Obyka" + System.currentTimeMillis(), "password");

        assertTrue(created);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToHaveAValidConnection() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean created = usersManagerLocal.create("Obyka" + currentTime, "password");
        boolean connected = usersManagerLocal.validConnection("Obyka" + currentTime, "password");

        assertTrue(connected);
        assertTrue(created);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShoulBePossibleToFindAUserByUsername() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean created = usersManagerLocal.create("Obyka" + currentTime, "password");
        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);

        assertTrue(created);
        assertEquals("Obyka" + currentTime, user.getUsername());
    }
}
*/