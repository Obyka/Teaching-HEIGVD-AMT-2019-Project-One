package ch.heig.amt.project.one.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSerie {
    private final String title = "Game of Thrones";
    private final String genre = "Drama";
    private final String producer = "HBO";
    private final int ageRestriction = 16;
    private final String synopsis = "Jon Snow is badass";
    private final long id = 25;
    private final long idOwner = 100;

    @Test
    void itShouldBePossibleToCreateASerie() {
        Serie got = Serie.builder()
                .title(title)
                .genre(genre)
                .producer(producer)
                .ageRestriction(ageRestriction)
                .synopsis(synopsis)
                .build();
        got.setId(id);
        got.setOwner(idOwner);

        assertNotNull(got);
        assertEquals(id, got.getId());
        assertEquals(idOwner, got.getOwner());
        assertEquals(title, got.getTitle());
        assertEquals(genre, got.getGenre());
        assertEquals(producer, got.getProducer());
        assertEquals(ageRestriction, got.getAgeRestriction());
        assertEquals(synopsis, got.getSynopsis());
    }

    @Test
    void itShouldBePossibleToSetASerie() {
        Serie got = Serie.builder().build();

        got.setId(id);
        got.setOwner(idOwner);
        got.setTitle(title);
        got.setGenre(genre);
        got.setProducer(producer);
        got.setAgeRestriction(ageRestriction);
        got.setSynopsis(synopsis);

        assertNotNull(got);
        assertEquals(id, got.getId());
        assertEquals(idOwner, got.getOwner());
        assertEquals(title, got.getTitle());
        assertEquals(genre, got.getGenre());
        assertEquals(producer, got.getProducer());
        assertEquals(ageRestriction, got.getAgeRestriction());
        assertEquals(synopsis, got.getSynopsis());
    }

    @Test
    void itShouldBePossibleToHaveTwoSeriesEqual() {
        Serie got = Serie.builder()
                .title(title)
                .genre(genre)
                .producer(producer)
                .ageRestriction(ageRestriction)
                .synopsis(synopsis)
                .build();
        got.setId(id);
        got.setOwner(idOwner);

        Serie got2 = Serie.builder()
                .title(title)
                .genre(genre)
                .producer(producer)
                .ageRestriction(ageRestriction)
                .synopsis(synopsis)
                .build();
        got2.setId(id);
        got2.setOwner(idOwner);

        assertEquals(true, got.equals(got2));
    }
}
