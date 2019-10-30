package ch.heig.amt.project.one.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class TestViewer {
    private final String firstname = "John";
    private final String lastname = "Fitz";
    private final String username = "fitz_johnnn97";
    private final String genre = "Homme";
    private final Date birthdate = new Date(1997, 10, 5);
    private final long id = 154;
    private final long idOwner = 8560000;

    Viewer HelperCreateViewer() {
        Viewer viewer = Viewer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .genre(genre)
                .birthDate(birthdate)
                .build();
        viewer.setId(id);
        viewer.setOwner(idOwner);

        return viewer;
    }

    void HelperAssertViewer(Viewer viewer) {
        assertNotNull(viewer);
        assertEquals(id, viewer.getId());
        assertEquals(idOwner, viewer.getOwner());
        assertEquals(firstname, viewer.getFirstname());
        assertEquals(lastname, viewer.getLastname());
        assertEquals(username, viewer.getUsername());
        assertEquals(genre, viewer.getGenre());
        assertEquals(birthdate, viewer.getBirthDate());
    }

    @Test
    void itShouldBePossibleToCreateAViewer() {
        Viewer viewer = HelperCreateViewer();
        HelperAssertViewer(viewer);
    }

    @Test
    void itShouldBePossibleToSetAViewer() {
        Viewer viewer = Viewer.builder().build();

        viewer.setId(id);
        viewer.setOwner(idOwner);
        viewer.setFirstname(firstname);
        viewer.setLastname(lastname);
        viewer.setUsername(username);
        viewer.setGenre(genre);
        viewer.setBirthDate(birthdate);

        HelperAssertViewer(viewer);
    }

    @Test
    void itShouldBePossibleToHaveTwoViewersEqual() {
        Viewer viewer = HelperCreateViewer();
        Viewer viewer2 = HelperCreateViewer();

        assertEquals(true, viewer.equals(viewer2));
    }
}
