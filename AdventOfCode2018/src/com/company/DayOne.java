package com.company;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayOne {

    public int findFinalFrequency(List<Integer> frequencyChanges) {
        int frequency = 0;

        for (int i = 0; i < frequencyChanges.size(); i++) {
            frequency += frequencyChanges.get(i);
        }

        return frequency;
    }

    public int findFirstFrequencyReachedTwice(List<Integer> frequencyChanges) {
        int currentFrequency = 0;
        Set<Integer> foundFrequencies = new HashSet<>();
        foundFrequencies.add(currentFrequency);

        for (int i = 0; i < frequencyChanges.size(); i++) {
            currentFrequency += frequencyChanges.get(i);

            if (foundFrequencies.contains(currentFrequency)) {
                return currentFrequency;
            }

            foundFrequencies.add(currentFrequency);

            if (i == frequencyChanges.size() - 1) {
                i = -1;
            }
        }

        return currentFrequency;
    }
}
