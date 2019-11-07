package ch.heig.amt.project.one.business.DAO;

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

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class TestUsersManager {
    @EJB
    UsersManagerLocal usersManagerLocal;

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateAUser() throws DuplicateKeyException, SQLException {
        boolean created = usersManagerLocal.create("Obyka" + System.currentTimeMillis(), "password");

        assertTrue(created);
    }
}
