package ch.heig.amt.project.one.business.DAO;

import ch.heig.amt.project.one.business.interfaces.UsersManagerLocal;
import ch.heig.amt.project.one.business.interfaces.ViewersManagerLocal;
import ch.heig.amt.project.one.model.User;
import ch.heig.amt.project.one.model.Viewer;
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
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class TestViewersManager {
    @EJB
    ViewersManagerLocal viewersManagerLocal;

    @EJB
    UsersManagerLocal usersManagerLocal;

    static int VIEWER_ID = 0;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAViewer() throws DuplicateKeyException, SQLException {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Viewer viewer = Viewer.builder().firstname("Jordan").lastname("Mercier").genre("Homme").birthDate(new Date(1998, 3, 31)).username("jo.mercier").build();
        viewer.setOwner(user.getId());
        viewer.setId(++VIEWER_ID);
        boolean viewerCreated = viewersManagerLocal.create(viewer);
        assertTrue(viewerCreated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToFindAViewerById() {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Viewer viewer = Viewer.builder().firstname("Jordan").lastname("Mercier").genre("Homme").birthDate(new Date(1998, 3, 31)).username("jo.mercier").build();
        viewer.setOwner(user.getId());
        viewer.setId(++VIEWER_ID);
        boolean viewerCreated = viewersManagerLocal.create(viewer);
        assertTrue(viewerCreated);

        Viewer viewerLoaded = viewersManagerLocal.findById(user, VIEWER_ID);
        assertNotSame(viewer, viewerLoaded);
        assertEquals(viewer, viewerLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAViewer() {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Viewer viewer = Viewer.builder().firstname("Jordan").lastname("Mercier").genre("Homme").birthDate(new Date(1998, 3, 31)).username("jo.mercier").build();
        viewer.setOwner(user.getId());
        viewer.setId(++VIEWER_ID);
        boolean viewerCreated = viewersManagerLocal.create(viewer);
        assertTrue(viewerCreated);

        Viewer viewerModified = viewer;
        viewerModified.setLastname("Polier");
        viewerModified.setUsername("jo.polier");

        boolean viewerUpdated = viewersManagerLocal.update(viewerModified);
        assertTrue(viewerUpdated);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAViewer() {
        long currentTime = System.currentTimeMillis();
        boolean userCreated = usersManagerLocal.create("Obyka" + currentTime, "password");
        assertTrue(userCreated);

        User user = usersManagerLocal.findUserByUsername("Obyka" + currentTime);
        Viewer viewer = Viewer.builder().firstname("Jordan").lastname("Mercier").genre("Homme").birthDate(new Date(1998, 3, 31)).username("jo.mercier").build();
        viewer.setOwner(user.getId());
        viewer.setId(++VIEWER_ID);
        boolean viewerCreated = viewersManagerLocal.create(viewer);
        assertTrue(viewerCreated);

        boolean viewerDeleted = viewersManagerLocal.delete(user, VIEWER_ID);
        assertTrue(viewerDeleted);
    }
}
