package com.bdd.traintimetables;

import java.time.LocalTime;
import java.util.List;

public interface CanScheduleServices {

    void scheduleService(String line,
                         List<LocalTime> departingAt,
                         String departure,
                         String destination);

}
