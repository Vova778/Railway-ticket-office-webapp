package ModelTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

public class ScheduleTest {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleTest.class);

    @BeforeAll
    static void logBefore () {
        LOGGER.info("Starting Schedule tests");
    }

   /* @Test
    void createStation(){
        Schedule schedule = new Schedule();
        List<Schedule> stationList = new ArrayList<>();
        stationList.add(new Schedule());
        assertDoesNotThrow( () ->  schedule.setId(100));
        assertDoesNotThrow( () ->  schedule.setName("Тест Станція"));
        assertDoesNotThrow(() ->schedule.setConnectingStations(stationList));
    }

    @Test
    void createBadStation(){
        Station station = new Station();
        List<Station> stationList = null;
        assertThrows(IllegalArgumentException.class, () ->  station.setId(-1));

        assertThrows(IllegalArgumentException.class,
                () ->  station.setName(null));

        assertThrows(IllegalArgumentException.class,
                () ->  station.setName("Т"));

        assertThrows(IllegalArgumentException.class,
                () ->station.setConnectingStations(stationList));
    }*/

}
