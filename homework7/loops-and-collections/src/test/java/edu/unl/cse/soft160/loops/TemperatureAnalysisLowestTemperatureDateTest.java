package edu.unl.cse.soft160.loops;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/*
 * Category-Partition Documentation:
 * - length: null list, empty list, single element, multiple elements
 * - element order: lowest first, lowest middle, lowest last, duplicate lowest
 * - data validity: null observation, null measurement, null date, all valid data
 */
public class TemperatureAnalysisLowestTemperatureDateTest {

    @Test
    public void getDateForLowestTemperature_nullList_returnsNull() {
        assertNull(TemperatureAnalysis.getDateForLowestTemperature(null));
    }

    @Test
    public void getDateForLowestTemperature_emptyList_returnsNull() {
        assertNull(TemperatureAnalysis.getDateForLowestTemperature(Collections.emptyList()));
    }

    @Test
    public void getDateForLowestTemperature_singleObservation_returnsItsDate() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<Observation> observations = Collections.singletonList(observation(15.0, date));
        assertEquals(date, TemperatureAnalysis.getDateForLowestTemperature(observations));
    }

    @Test
    public void getDateForLowestTemperature_lowestInMiddle_returnsDate() {
        LocalDate day1 = LocalDate.of(2024, 1, 1);
        LocalDate day2 = LocalDate.of(2024, 1, 2);
        LocalDate day3 = LocalDate.of(2024, 1, 3);
        List<Observation> observations = Arrays.asList(
                observation(12.0, day1),
                observation(5.0, day2),
                observation(8.0, day3)
        );
        assertEquals(day2, TemperatureAnalysis.getDateForLowestTemperature(observations));
    }

    @Test
    public void getDateForLowestTemperature_tieKeepsFirstOccurrence() {
        LocalDate day1 = LocalDate.of(2024, 2, 1);
        LocalDate day2 = LocalDate.of(2024, 2, 2);
        List<Observation> observations = Arrays.asList(
                observation(-1.0, day1),
                observation(-1.0, day2),
                observation(3.0, LocalDate.of(2024, 2, 3))
        );
        assertEquals(day1, TemperatureAnalysis.getDateForLowestTemperature(observations));
    }

    @Test
    public void getDateForLowestTemperature_skipsNullMeasurementAndDate() {
        LocalDate day1 = LocalDate.of(2024, 3, 1);
        LocalDate day2 = LocalDate.of(2024, 3, 2);
        LocalDate day3 = LocalDate.of(2024, 3, 3);
        List<Observation> observations = Arrays.asList(
                observation(null, day1),
                observation(4.0, null),
                observation(-5.0, day2),
                observation(-3.0, day3)
        );
        assertEquals(day2, TemperatureAnalysis.getDateForLowestTemperature(observations));
    }

    @Test
    public void getDateForLowestTemperature_ignoresNullObservation() {
        LocalDate day1 = LocalDate.of(2024, 4, 1);
        LocalDate day2 = LocalDate.of(2024, 4, 2);
        List<Observation> observations = Arrays.asList(
                null,
                observation(7.0, day1),
                observation(2.0, day2)
        );
        assertEquals(day2, TemperatureAnalysis.getDateForLowestTemperature(observations));
    }

    private Observation observation(Double measurement, LocalDate date) {
        return new Observation(measurement, date);
    }
}
