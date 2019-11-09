/*package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.SeriesManagerLocal;
import ch.heig.amt.project.one.model.Serie;
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
public class TestSeriesManager {
    @EJB
    SeriesManagerLocal seriesManagerLocal;

    @Test
    @Transactional(TransactionMode.COMMIT)
    public void itShouldBePossibleToCreateASerie() throws DuplicateKeyException, SQLException {
        Serie serie = Serie.builder().title("X-Files" + System.currentTimeMillis()).producer("Fox").synopsis("Alien thing").ageRestriction(16).genre("Thriller").build();
        serie.setOwner(1);
        boolean created = seriesManagerLocal.create(serie);

        assertTrue(created);
    }
}
 */
