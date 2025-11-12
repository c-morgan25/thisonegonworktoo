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
 * - extreme availability: none, one extreme, multiple extremes
 * - tie handling: latest date tie broken by index, newest date strictly later
 */
public class TemperatureAnalysisMostRecentExtremeTemperatureTest {

    @Test
    public void getMostRecentExtremeTemperature_nullList_returnsNull() {
        assertNull(TemperatureAnalysis.getMostRecentExtremeTemperature(null));
    }

    @Test
    public void getMostRecentExtremeTemperature_emptyList_returnsNull() {
        assertNull(TemperatureAnalysis.getMostRecentExtremeTemperature(Collections.emptyList()));
    }

    @Test
    public void getMostRecentExtremeTemperature_noExtreme_returnsNull() {
        List<Observation> observations = Arrays.asList(
                observation(10.0, LocalDate.of(2024, 1, 1)),
                observation(9.0, LocalDate.of(2024, 1, 2)),
                observation(8.0, LocalDate.of(2024, 1, 3))
        );
        assertNull(TemperatureAnalysis.getMostRecentExtremeTemperature(observations));
    }

    @Test
    public void getMostRecentExtremeTemperature_multipleExtremes_returnsLatestDate() {
        List<Observation> observations = Arrays.asList(
                observation(6.0, LocalDate.of(2024, 2, 1)),
                observation(8.0, LocalDate.of(2024, 2, 2)),
                observation(9.0, LocalDate.of(2024, 2, 3)),
                observation(7.5, LocalDate.of(2024, 2, 2))
        );
        assertEquals(Double.valueOf(9.0), TemperatureAnalysis.getMostRecentExtremeTemperature(observations));
    }

    @Test
    public void getMostRecentExtremeTemperature_unsortedInput_selectsByDate() {
        List<Observation> observations = Arrays.asList(
                observation(15.0, LocalDate.of(2024, 3, 3)),
                observation(10.0, LocalDate.of(2024, 3, 1)),
                observation(12.0, LocalDate.of(2024, 3, 2))
        );
        assertEquals(Double.valueOf(15.0), TemperatureAnalysis.getMostRecentExtremeTemperature(observations));
    }

    @Test
    public void getMostRecentExtremeTemperature_tieOnDate_usesLargestIndex() {
        LocalDate sharedDate = LocalDate.of(2024, 4, 2);
        List<Observation> observations = Arrays.asList(
                observation(5.0, LocalDate.of(2024, 4, 1)),
                observation(7.0, sharedDate),
                observation(8.0, sharedDate)
        );
        assertEquals(Double.valueOf(8.0), TemperatureAnalysis.getMostRecentExtremeTemperature(observations));
    }

    @Test
    public void getMostRecentExtremeTemperature_ignoresNullData() {
        LocalDate day1 = LocalDate.of(2024, 5, 1);
        LocalDate day2 = LocalDate.of(2024, 5, 2);
        List<Observation> observations = Arrays.asList(
                null,
                observation(null, day1),
                observation(6.0, null),
                observation(4.0, day1),
                observation(7.0, day2)
        );
        assertEquals(Double.valueOf(7.0), TemperatureAnalysis.getMostRecentExtremeTemperature(observations));
    }

    private Observation observation(Double measurement, LocalDate date) {
        return new Observation(measurement, date);
    }
}
