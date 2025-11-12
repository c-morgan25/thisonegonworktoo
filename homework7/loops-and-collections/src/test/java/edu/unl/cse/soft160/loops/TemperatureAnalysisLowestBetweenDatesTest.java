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
 * - element order: chronological order, reverse order, unsorted order
 * - list has an element on or before date: none, on boundary, strictly between boundaries
 * - date inputs: start before end, start after end, identical dates
 */
public class TemperatureAnalysisLowestBetweenDatesTest {

    @Test
    public void getLowestTemperatureBetweenTwoDates_nullList_returnsNull() {
        LocalDate day = LocalDate.of(2024, 5, 1);
        assertNull(TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(null, day, day));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_emptyList_returnsNull() {
        LocalDate day = LocalDate.of(2024, 5, 1);
        assertNull(TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(Collections.emptyList(), day, day));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_nullBoundaryDate_returnsNull() {
        List<Observation> observations = Collections.singletonList(observation(10.0, LocalDate.of(2024, 6, 1)));
        assertNull(TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, null, LocalDate.of(2024, 6, 2)));
        assertNull(TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, LocalDate.of(2024, 6, 2), null));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_singleObservationWithinRange_returnsMeasurement() {
        LocalDate date = LocalDate.of(2024, 7, 4);
        List<Observation> observations = Collections.singletonList(observation(3.5, date));
        assertEquals(Double.valueOf(3.5),
                TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, date, date));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_noObservationInRange_returnsNull() {
        LocalDate start = LocalDate.of(2024, 8, 1);
        LocalDate end = LocalDate.of(2024, 8, 10);
        List<Observation> observations = Arrays.asList(
                observation(1.0, LocalDate.of(2024, 7, 31)),
                observation(2.0, LocalDate.of(2024, 8, 11))
        );
        assertNull(TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, start, end));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_boundariesInclusiveAndOrderAgnostic() {
        LocalDate day1 = LocalDate.of(2024, 9, 1);
        LocalDate day2 = LocalDate.of(2024, 9, 5);
        LocalDate day3 = LocalDate.of(2024, 9, 10);
        List<Observation> observations = Arrays.asList(
                observation(7.0, day2),
                observation(4.0, day3),
                observation(6.0, day1)
        );
        assertEquals(Double.valueOf(4.0),
                TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, day3, day1));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_ignoresNullData() {
        LocalDate start = LocalDate.of(2024, 10, 1);
        LocalDate end = LocalDate.of(2024, 10, 3);
        List<Observation> observations = Arrays.asList(
                observation(null, LocalDate.of(2024, 10, 1)),
                observation(2.0, null),
                observation(1.5, LocalDate.of(2024, 10, 2)),
                observation(3.0, LocalDate.of(2024, 10, 3))
        );
        assertEquals(Double.valueOf(1.5),
                TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, start, end));
    }

    @Test
    public void getLowestTemperatureBetweenTwoDates_identicalBoundaries_usesSingleDay() {
        LocalDate targetDay = LocalDate.of(2024, 11, 15);
        List<Observation> observations = Arrays.asList(
                observation(9.0, targetDay),
                observation(5.0, LocalDate.of(2024, 11, 14)),
                observation(2.0, LocalDate.of(2024, 11, 16))
        );
        assertEquals(Double.valueOf(9.0),
                TemperatureAnalysis.getLowestTemperatureBetweenTwoDates(observations, targetDay, targetDay));
    }

    private Observation observation(Double measurement, LocalDate date) {
        return new Observation(measurement, date);
    }
}
