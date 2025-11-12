package edu.unl.cse.soft160.loops;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/*
 * Category-Partition Documentation:
 * - length: null list, empty list, single element, multiple elements
 * - element order: chronological order, reverse order, unsorted order
 * - temperature trend: strictly increasing, mixed trend, no extremes
 * - data validity: null observation, null measurement, null date
 */
public class TemperatureAnalysisExtremeTemperaturesTest {

    @Test
    public void getExtremeTemperatures_nullList_returnsEmptyList() {
        assertEquals(Collections.emptyList(), TemperatureAnalysis.getExtremeTemperatures(null));
    }

    @Test
    public void getExtremeTemperatures_emptyList_returnsEmptyList() {
        assertEquals(Collections.emptyList(), TemperatureAnalysis.getExtremeTemperatures(Collections.emptyList()));
    }

    @Test
    public void getExtremeTemperatures_singleObservation_hasNoExtreme() {
        List<Observation> observations = Collections.singletonList(observation(5.0, LocalDate.of(2024, 1, 1)));
        assertEquals(Collections.emptyList(), TemperatureAnalysis.getExtremeTemperatures(observations));
    }

    @Test
    public void getExtremeTemperatures_increasingChronologicalMeasurements_returnsAllButEarliest() {
        List<Observation> observations = Arrays.asList(
                observation(10.0, LocalDate.of(2024, 1, 1)),
                observation(12.0, LocalDate.of(2024, 1, 2)),
                observation(15.0, LocalDate.of(2024, 1, 3))
        );
        assertEquals(Arrays.asList(12.0, 15.0), TemperatureAnalysis.getExtremeTemperatures(observations));
    }

    @Test
    public void getExtremeTemperatures_unsortedInput_usesChronologicalOrder() {
        List<Observation> observations = Arrays.asList(
                observation(15.0, LocalDate.of(2024, 1, 3)),
                observation(10.0, LocalDate.of(2024, 1, 1)),
                observation(12.0, LocalDate.of(2024, 1, 2))
        );
        assertEquals(Arrays.asList(15.0, 12.0), TemperatureAnalysis.getExtremeTemperatures(observations));
    }

    @Test
    public void getExtremeTemperatures_noMeasurementExceedsEarlier_returnsEmptyList() {
        List<Observation> observations = Arrays.asList(
                observation(10.0, LocalDate.of(2024, 2, 1)),
                observation(10.0, LocalDate.of(2024, 2, 2)),
                observation(9.0, LocalDate.of(2024, 2, 3))
        );
        assertEquals(Collections.emptyList(), TemperatureAnalysis.getExtremeTemperatures(observations));
    }

    @Test
    public void getExtremeTemperatures_ignoresNullData() {
        List<Observation> observations = Arrays.asList(
                null,
                observation(null, LocalDate.of(2024, 3, 1)),
                observation(8.0, null),
                observation(5.0, LocalDate.of(2024, 3, 1)),
                observation(9.0, LocalDate.of(2024, 3, 2))
        );
        assertEquals(Collections.singletonList(9.0), TemperatureAnalysis.getExtremeTemperatures(observations));
    }

    private Observation observation(Double measurement, LocalDate date) {
        return new Observation(measurement, date);
    }
}
