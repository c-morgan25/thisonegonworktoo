package edu.unl.cse.soft160.loops;

// Name: GPT-5 Codex

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TemperatureAnalysis {
    /* TASK 1 */

    public static LocalDate getDateForLowestTemperature(List<Observation> observations) {
        if (observations == null || observations.isEmpty()) {
            return null;
        }
        LocalDate dateForLowest = null;
        Double lowestMeasurement = null;
        for (Observation observation : observations) {
            if (observation != null) {
                Double measurement = observation.getMeasurement();
                LocalDate date = observation.getDate();
                if (measurement != null && date != null) {
                    if (lowestMeasurement == null || measurement < lowestMeasurement) {
                        lowestMeasurement = measurement;
                        dateForLowest = date;
                    }
                }
            }
        }
        return dateForLowest;
    }

    /* TASK 2 */

    public static Double getLowestTemperatureBetweenTwoDates(List<Observation> observations,
                                                            LocalDate date1, LocalDate date2) {
        if (observations == null || observations.isEmpty()) {
            return null;
        }
        if (date1 == null || date2 == null) {
            return null;
        }
        LocalDate startDate = date1;
        LocalDate endDate = date2;
        if (date1.isAfter(date2)) {
            startDate = date2;
            endDate = date1;
        }
        Double lowestMeasurement = null;
        for (Observation observation : observations) {
            if (observation != null) {
                LocalDate date = observation.getDate();
                Double measurement = observation.getMeasurement();
                if (date != null && measurement != null) {
                    boolean notBeforeStart = !date.isBefore(startDate);
                    boolean notAfterEnd = !date.isAfter(endDate);
                    if (notBeforeStart && notAfterEnd) {
                        if (lowestMeasurement == null || measurement < lowestMeasurement) {
                            lowestMeasurement = measurement;
                        }
                    }
                }
            }
        }
        return lowestMeasurement;
    }

    /* TASK 3 */

    public static List<Double> getExtremeTemperatures(List<Observation> observations) {
        List<Double> extremeMeasurements = new ArrayList<Double>();
        if (observations == null || observations.isEmpty()) {
            return extremeMeasurements;
        }
        for (Observation candidate : observations) {
            if (isExtremeObservation(candidate, observations)) {
                extremeMeasurements.add(candidate.getMeasurement());
            }
        }
        return extremeMeasurements;
    }

    /* TASK 4 */

    public static Double getMostRecentExtremeTemperature(List<Observation> observations) {
        if (observations == null || observations.isEmpty()) {
            return null;
        }
        Double mostRecentExtreme = null;
        LocalDate dateForMostRecentExtreme = null;
        int indexForMostRecentExtreme = -1;
        int currentIndex = 0;
        for (Observation candidate : observations) {
            if (isExtremeObservation(candidate, observations)) {
                LocalDate candidateDate = candidate.getDate();
                Double candidateMeasurement = candidate.getMeasurement();
                boolean chooseCandidate = false;
                if (dateForMostRecentExtreme == null) {
                    chooseCandidate = true;
                } else if (candidateDate.isAfter(dateForMostRecentExtreme)) {
                    chooseCandidate = true;
                } else if (candidateDate.isEqual(dateForMostRecentExtreme) && currentIndex > indexForMostRecentExtreme) {
                    chooseCandidate = true;
                }
                if (chooseCandidate) {
                    mostRecentExtreme = candidateMeasurement;
                    dateForMostRecentExtreme = candidateDate;
                    indexForMostRecentExtreme = currentIndex;
                }
            }
            currentIndex = currentIndex + 1;
        }
        return mostRecentExtreme;
    }

    private static boolean isExtremeObservation(Observation candidate, List<Observation> observations) {
        if (candidate == null) {
            return false;
        }
        LocalDate candidateDate = candidate.getDate();
        Double candidateMeasurement = candidate.getMeasurement();
        if (candidateDate == null || candidateMeasurement == null) {
            return false;
        }
        boolean hasEarlierMeasurement = false;
        Double maximumEarlierMeasurement = null;
        for (Observation other : observations) {
            if (other != null) {
                LocalDate otherDate = other.getDate();
                Double otherMeasurement = other.getMeasurement();
                if (otherDate != null && otherMeasurement != null) {
                    if (otherDate.isBefore(candidateDate)) {
                        hasEarlierMeasurement = true;
                        if (maximumEarlierMeasurement == null || otherMeasurement > maximumEarlierMeasurement) {
                            maximumEarlierMeasurement = otherMeasurement;
                        }
                    }
                }
            }
        }
        if (!hasEarlierMeasurement) {
            return false;
        }
        if (maximumEarlierMeasurement == null) {
            return true;
        }
        return candidateMeasurement > maximumEarlierMeasurement;
    }
}