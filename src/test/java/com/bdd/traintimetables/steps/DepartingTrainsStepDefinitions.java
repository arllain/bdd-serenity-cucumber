package com.bdd.traintimetables.steps;

import com.bdd.traintimetables.InMemoryTimeTable;
import com.bdd.traintimetables.ItineraryService;
import com.bdd.traintimetables.unittests.timetables.LocalTimes;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class DepartingTrainsStepDefinitions {

    InMemoryTimeTable timeTable = new InMemoryTimeTable();
    ItineraryService itineraryService = new ItineraryService(timeTable);

    @Given("the {} train to {} leaves {} at {}")
    public void theTrainLeavesAt(String line, String to, String from,
                                 List<String> departureTimes) {
        Assertions.assertThat(departureTimes).isEmpty();
        //timeTable.scheduleService(line, departureTimes, from, to);
    }

    List<LocalTime> proposedDepartures;
    @When("Travis wants to travel from {} to Chatswood at {}")
    public void travisWantsToTravelFromHornsbyToChatswoodAt(String from, String to, LocalTime departureTime) {
        proposedDepartures = itineraryService.findNextDepartures(departureTime, from, to);
    }

    @Then("he should be told about the trains at: {}")
    public void heShouldBeToldAboutTheTrainsAt(List<LocalTime> expected) {
        Assertions.assertThat(proposedDepartures).isEqualTo(expected);
    }

    @ParameterType(".*")
    public LocalTime time(String timeValue) {
        return LocalTime.parse(timeValue, DateTimeFormatter.ofPattern("H:mm"));
    }

    @ParameterType(".*")
    public List<LocalTime> times(String timeValue) {
        return stream(timeValue.split(","))
                .map(String::trim)
                        .map(this::time)
                                .collect(Collectors.toList());
    }

}
