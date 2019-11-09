package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
import ch.heig.amt.project.one.model.Serie;
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
public class TestSeriesManager {
    @EJB
    SeriesManagerLocal seriesManagerLocal;

    @EJB
    UsersManagerLocal usersManagerLocal;

    static int SERIE_ID = 0;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateASerie() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Serie serie = Serie.builder().title("X-Files" + currentTime).producer("Fox").synopsis("Alien thing").ageRestriction(16).genre("Thriller").build();
        serie.setOwner(user.getId());
        serie.setId(++SERIE_ID);
        boolean serieCreated = seriesManagerLocal.create(serie);
        assertTrue(serieCreated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToFindASerieById() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Serie serie = Serie.builder().title("X-Files" + currentTime).producer("Fox").synopsis("Alien thing").ageRestriction(16).genre("Thriller").build();
        serie.setOwner(user.getId());
        serie.setId(++SERIE_ID);
        boolean serieCreated = seriesManagerLocal.create(serie);
        assertTrue(serieCreated);

        Serie serieLoaded = seriesManagerLocal.findById(user, SERIE_ID);
        assertNotSame(serie, serieLoaded);
        assertEquals(serie, serieLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateASerie() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Serie serie = Serie.builder().title("X-Files" + currentTime).producer("Fox").synopsis("Alien thing").ageRestriction(16).genre("Thriller").build();
        serie.setOwner(user.getId());
        serie.setId(++SERIE_ID);
        boolean serieCreated = seriesManagerLocal.create(serie);
        assertTrue(serieCreated);

        Serie serieModified = serie;
        serieModified.setTitle("X-files: Le retour");

        boolean serieUpdated = seriesManagerLocal.update(serieModified);
        assertTrue(serieUpdated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteASerie() throws DuplicateKeyException, SQLException{
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Serie serie = Serie.builder().title("X-Files" + currentTime).producer("Fox").synopsis("Alien thing").ageRestriction(16).genre("Thriller").build();
        serie.setOwner(user.getId());
        serie.setId(++SERIE_ID);
        boolean serieCreated = seriesManagerLocal.create(serie);
        assertTrue(serieCreated);

        boolean serieDeleted = seriesManagerLocal.delete(user, SERIE_ID);
        assertTrue(serieDeleted);
    }
}
