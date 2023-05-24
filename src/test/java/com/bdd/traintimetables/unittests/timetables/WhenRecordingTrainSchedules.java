package com.bdd.traintimetables.unittests.timetables;

import com.bdd.traintimetables.InMemoryTimeTable;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


@DisplayName("When scheduling train services")
public class WhenRecordingTrainSchedules {

    // Given
    InMemoryTimeTable timeTable = new InMemoryTimeTable();

    @Test
    @DisplayName("We can schedule a trip with a single scheduled time")
    public void tripWithOneScheduleService() {
        // When
        timeTable.scheduleService("T1", LocalTimes.at("09:15"),
                "Hornsby", "Central");

        Assertions.assertThat(timeTable.getDepartures("T1", "Hornsby")).hasSize(1);
    }
}
