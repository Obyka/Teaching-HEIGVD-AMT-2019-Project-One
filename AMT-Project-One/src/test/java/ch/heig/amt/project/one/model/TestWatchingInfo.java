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

    WatchingInfo HelperCreateWatchingInfo() {
        WatchingInfo watchingInfo = WatchingInfo.builder()
                .timeSpent(timeSpent)
                .beginningDate(beginningDate)
                .idSerie(idSerie)
                .idViewer(idViewer)
                .build();
        watchingInfo.setOwner(idOwner);

        return watchingInfo;
    }

    void HelperAssertWatchingInfo(WatchingInfo watchingInfo) {
        assertNotNull(watchingInfo);
        assertEquals(idOwner, watchingInfo.getOwner());
        assertEquals(beginningDate, watchingInfo.getBeginningDate());
        assertEquals(timeSpent, watchingInfo.getTimeSpent());
        assertEquals(idSerie, watchingInfo.getIdSerie());
        assertEquals(idViewer, watchingInfo.getIdViewer());
    }

    @Test
    void itShouldBePossibleToCreateAWatchingInfo() {
        WatchingInfo watchingInfo = HelperCreateWatchingInfo();
        HelperAssertWatchingInfo(watchingInfo);
    }

    @Test
    void itShouldBePossibleToSetAWatchingInfo() {
        WatchingInfo watchingInfo = WatchingInfo.builder().build();

        watchingInfo.setOwner(idOwner);
        watchingInfo.setTimeSpent(timeSpent);
        watchingInfo.setBeginningDate(beginningDate);
        watchingInfo.setIdSerie(idSerie);
        watchingInfo.setIdViewer(idViewer);

        HelperAssertWatchingInfo(watchingInfo);
    }

    @Test
    void itShouldBePossibleToHaveTwoWatchingInfosEqual() {
        WatchingInfo watchingInfo = HelperCreateWatchingInfo();
        WatchingInfo watchingInfo2 = HelperCreateWatchingInfo();

        assertEquals(true, watchingInfo.equals(watchingInfo2));
    }
}
