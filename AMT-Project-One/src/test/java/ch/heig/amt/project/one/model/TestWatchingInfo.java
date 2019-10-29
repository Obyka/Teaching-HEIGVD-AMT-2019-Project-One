package ch.heig.amt.project.one.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class TestWatchingInfo {
    private final long idOwner = 1525;
    private final Date beginningDate = new Date(1997, 10, 5);
    private final int timeSpent = 145;
    private final long idSerie = 14455;
    private final long idViewer = 47890;

    @Test
    void itShouldBePossibleToCreateAWatchingInfo() {
        WatchingInfo watchingInfo = WatchingInfo.builder()
                .timeSpent(timeSpent)
                .beginningDate(beginningDate)
                .idSerie(idSerie)
                .idViewer(idViewer)
                .build();
        watchingInfo.setOwner(idOwner);

        assertNotNull(watchingInfo);
        assertEquals(idOwner, watchingInfo.getOwner());
        assertEquals(beginningDate, watchingInfo.getBeginningDate());
        assertEquals(timeSpent, watchingInfo.getTimeSpent());
        assertEquals(idSerie, watchingInfo.getIdSerie());
        assertEquals(idViewer, watchingInfo.getIdViewer());
    }

    @Test
    void itShouldBePossibleToSetAWatchingInfo() {
        WatchingInfo watchingInfo = WatchingInfo.builder().build();

        watchingInfo.setOwner(idOwner);
        watchingInfo.setTimeSpent(timeSpent);
        watchingInfo.setBeginningDate(beginningDate);
        watchingInfo.setIdSerie(idSerie);
        watchingInfo.setIdViewer(idViewer);

        assertNotNull(watchingInfo);
        assertEquals(idOwner, watchingInfo.getOwner());
        assertEquals(beginningDate, watchingInfo.getBeginningDate());
        assertEquals(timeSpent, watchingInfo.getTimeSpent());
        assertEquals(idSerie, watchingInfo.getIdSerie());
        assertEquals(idViewer, watchingInfo.getIdViewer());
    }

    @Test
    void itShouldBePossibleToHaveTwoWatchingInfosEqual() {
        WatchingInfo watchingInfo = WatchingInfo.builder()
                .timeSpent(timeSpent)
                .beginningDate(beginningDate)
                .idSerie(idSerie)
                .idViewer(idViewer)
                .build();
        watchingInfo.setOwner(idOwner);

        WatchingInfo watchingInfo2 = WatchingInfo.builder()
                .timeSpent(timeSpent)
                .beginningDate(beginningDate)
                .idSerie(idSerie)
                .idViewer(idViewer)
                .build();
        watchingInfo2.setOwner(idOwner);

        assertEquals(true, watchingInfo.equals(watchingInfo2));
    }
}
