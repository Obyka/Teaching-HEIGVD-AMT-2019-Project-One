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

    Serie HelperCreateSerie() {
        Serie serie = Serie.builder()
                .title(title)
                .genre(genre)
                .producer(producer)
                .ageRestriction(ageRestriction)
                .synopsis(synopsis)
                .build();
        serie.setId(id);
        serie.setOwner(idOwner);

        return serie;
    }

    void HelperAssertSerie(Serie serie) {
        assertNotNull(serie);
        assertEquals(id, serie.getId());
        assertEquals(idOwner, serie.getOwner());
        assertEquals(title, serie.getTitle());
        assertEquals(genre, serie.getGenre());
        assertEquals(producer, serie.getProducer());
        assertEquals(ageRestriction, serie.getAgeRestriction());
        assertEquals(synopsis, serie.getSynopsis());
    }
    
    @Test
    void itShouldBePossibleToCreateASerie() {
        Serie got = HelperCreateSerie();
        HelperAssertSerie(got);
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

        HelperAssertSerie(got);
    }

    @Test
    void itShouldBePossibleToHaveTwoSeriesEqual() {
        Serie got = HelperCreateSerie();
        Serie got2 = HelperCreateSerie();

        assertEquals(true, got.equals(got2));
    }
}
