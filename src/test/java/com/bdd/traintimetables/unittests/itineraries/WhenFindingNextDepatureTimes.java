package com.bdd.traintimetables.unittests.itineraries;

import com.bdd.traintimetables.ItineraryService;
import com.bdd.traintimetables.TimeTable;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("When finding the next train departure times")
public class WhenFindingNextDepatureTimes {

    private TimeTable timeTable;
    private ItineraryService itineraryService;

    private LocalTime at(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("H:mm"));
    }

    private TimeTable departures(LocalTime... departures) {
        return new TimeTable() {
            @Override
            public List<String> findLinesThrough(String from, String to) {
                return List.of("T1");
            }

            @Override
            public List<LocalTime> getDepartures(String lineName, String from) {
                return List.of(departures);
            }
        };
    }

    @Test
    @DisplayName("should be the first train after the departure time")
    public void tripWithOneScheduledTime(){

        // Given
        timeTable = departures(at("8:10"), at("8:20"), at("8:30"));
        itineraryService = new ItineraryService(timeTable);

        // When
        List<LocalTime> proposedDepartures =
                itineraryService.findNextDepartures(at("8:25"), "Hornsby", "Central");

        // Then
        assertThat(proposedDepartures).containsExactly(at("8:30"));
    }

    @Test
    @DisplayName("should propose the next 2 trains")
    public void tripWithServeralScheduledTimes() {

        // Given
        timeTable = departures(at("8:10"), at("8:20"), at("8:30"), at("8:45"));
        itineraryService = new ItineraryService(timeTable);

        // When
        List<LocalTime> proposedDepartures =
                itineraryService.findNextDepartures(at("8:05"), "Hornsby", "Central");

        // Then
        assertThat(proposedDepartures).containsExactly(at("8:10"), at("8:20"));
    }

    @Test
    @DisplayName("No trains should be returned if none are available")
    public void anAfterHoursTrip() {

        // Given
        timeTable = departures(at("8:10"), at("8:20"), at("8:30"));
        itineraryService = new ItineraryService(timeTable);

        // When
        List<LocalTime> proposedDepartures =
                itineraryService.findNextDepartures(at("8:50"), "Hornsby", "Central");

        // Then
        assertThat(proposedDepartures).isEmpty();
    }

}
