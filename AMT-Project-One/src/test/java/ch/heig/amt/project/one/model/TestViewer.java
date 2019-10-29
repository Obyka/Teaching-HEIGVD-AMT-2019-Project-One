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

    @Test
    void itShouldBePossibleToCreateAViewer() {
        Viewer viewer = Viewer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .genre(genre)
                .birthDate(birthdate)
                .build();
        viewer.setId(id);
        viewer.setOwner(idOwner);

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
    void itShouldBePossibleToSetAViewer() {
        Viewer viewer = Viewer.builder().build();

        viewer.setId(id);
        viewer.setOwner(idOwner);
        viewer.setFirstname(firstname);
        viewer.setLastname(lastname);
        viewer.setUsername(username);
        viewer.setGenre(genre);
        viewer.setBirthDate(birthdate);

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
    void itShouldBePossibleToHaveTwoViewersEqual() {
        Viewer viewer = Viewer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .genre(genre)
                .birthDate(birthdate)
                .build();
        viewer.setId(id);
        viewer.setOwner(idOwner);

        Viewer viewer2 = Viewer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .genre(genre)
                .birthDate(birthdate)
                .build();
        viewer2.setId(id);
        viewer2.setOwner(idOwner);

        assertEquals(true, viewer.equals(viewer2));
    }
}
