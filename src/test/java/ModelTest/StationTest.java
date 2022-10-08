package ModelTest;

import com.railway.ticket.office.webapp.model.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StationTest {
    private static final Logger LOGGER = LogManager.getLogger(StationTest.class);

    @BeforeAll
    static void logBefore () {
        LOGGER.info("Starting Station tests");
    }

    @Test
    void createStation(){
        Station station = new Station();
        List<Station> stationList = new ArrayList<>();
        stationList.add(new Station(101, "Тест Станція 2"));
        assertDoesNotThrow( () ->  station.setId(100));
        assertDoesNotThrow( () ->  station.setName("Тест Станція"));

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

    }


}
