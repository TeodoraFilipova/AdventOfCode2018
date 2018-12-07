package com.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DayFour {

    private List<Record> sortRecordsList(List<String> stringRecords) {
        List<Record> recordList = new ArrayList<>();

        for (String recordString : stringRecords) {
            String dateTimeString = recordString.substring(1, 17);
            String eventString = recordString.substring(19);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

            Record currentRecord = new Record(dateTime, eventString);
            recordList.add(currentRecord);
        }
        Collections.sort(recordList);
        return recordList;
    }

    private Map<DateGuardCombo, List<Boolean>> getGuardsSleepingHabits(List<Record> recordList) {
        Map<DateGuardCombo, List<Boolean>> sleepingHabitsMap = new HashMap<>();
        DateGuardCombo dateGuardCombo = null;
        List<Integer> asleepAwakeMinutes = new ArrayList<>();

        int currentRecordIndex = 0;

        while (currentRecordIndex < recordList.size()) {
            do {
                Record record = recordList.get(currentRecordIndex);
                if (record.getEvent().charAt(0) == 'G') {
                    if (currentRecordIndex != 0) {
                        asleepAwakeMinutes.add(60);
                        for (int i = 1; i < asleepAwakeMinutes.size(); i++) {
                            for (int j = 0; j < asleepAwakeMinutes.get(i) - asleepAwakeMinutes.get(i - 1); j++) {
                                if (i % 2 == 0) {
                                    sleepingHabitsMap.get(dateGuardCombo).add(true);
                                } else {
                                    sleepingHabitsMap.get(dateGuardCombo).add(false);
                                }
                            }
                        }
                    }

                    int guardId = Integer.parseInt(record.getEvent().substring(record.getEvent().indexOf('#') + 1, record.getEvent().indexOf('b') - 1));
                    dateGuardCombo = new DateGuardCombo(record.getDateTime().toLocalDate(), guardId);
                    asleepAwakeMinutes = new ArrayList<>();
                    asleepAwakeMinutes.add(0);

                    sleepingHabitsMap.put(dateGuardCombo, new ArrayList<>());
                } else {
                    asleepAwakeMinutes.add(record.getDateTime().getMinute());
                }

                currentRecordIndex++;

                if (currentRecordIndex == recordList.size()) {
                    asleepAwakeMinutes.add(60);
                    for (int i = 1; i < asleepAwakeMinutes.size(); i++) {
                        for (int j = 0; j < asleepAwakeMinutes.get(i) - asleepAwakeMinutes.get(i - 1); j++) {
                            if (i % 2 == 0) {
                                sleepingHabitsMap.get(dateGuardCombo).add(true);
                            } else {
                                sleepingHabitsMap.get(dateGuardCombo).add(false);
                            }
                        }
                    }
                    break;
                }

            } while (recordList.get(currentRecordIndex).getEvent().charAt(0) != 'G');
        }
        return sleepingHabitsMap;
    }

    private int getGuardMostAsleep(Map<DateGuardCombo, List<Boolean>> sleepingMap) {
//        List<Long> minutesAsleep = new ArrayList<>();
//        List<Integer> guardIds = new ArrayList<>();

        Map<Integer, Integer> maxMinutesAsleepPerGuard = new HashMap<>();
//        long maxMinutesAsleep = 0;
//        int sleepyGuardId = 0;

        Set<DateGuardCombo> keySet = sleepingMap.keySet();
        for (DateGuardCombo k : keySet) {
            int minutesAsleepCount = (int) sleepingMap.get(k).stream().filter(value -> value).count();

            if (!maxMinutesAsleepPerGuard.containsKey(k.getGuardId())) {
                maxMinutesAsleepPerGuard.put(k.getGuardId(), 0);
            }
            maxMinutesAsleepPerGuard.put(k.getGuardId(), maxMinutesAsleepPerGuard.get(k.getGuardId()) + minutesAsleepCount);

//            if (minutesAsleepCount > maxMinutesAsleep) {
//                maxMinutesAsleep = minutesAsleepCount;
//                sleepyGuardId = k.getGuardId();
//            }
        }
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : maxMinutesAsleepPerGuard.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();

//        sleepingMap.forEach((k, v) -> {
//            long minutesAsleepCount = sleepingMap.get(k).stream().filter(value -> value).count();
//            minutesAsleep.add(minutesAsleepCount);
//            guardIds.add(k.getGuardId());
//        });

//        int maxIndex = minutesAsleep.indexOf(Collections.max(minutesAsleep));
//        return guardIds.get(maxIndex);
    }

    private int getMinuteMostAsleep(Map<DateGuardCombo, List<Boolean>> sleepingMap, int sleepyGuardId) {
        List<List<Boolean>> minutesAsleep = new ArrayList<>();
        Map<Integer, Integer> timesAsleepPerMinute = new HashMap<>();

        Set<DateGuardCombo> keySet = sleepingMap.keySet();
        for (DateGuardCombo k : keySet) {
            if (k.getGuardId() == sleepyGuardId) {
                minutesAsleep.add(sleepingMap.get(k));
            }
        }

//        sleepingMap.forEach((k, v) -> {
//            if (k.getGuardId() == sleepyGuardId) {
//                minutesAsleep.add(v);
//            }
//        });

        for (int i = 0; i < minutesAsleep.size(); i++) {
            for (int j = 0; j < 60; j++) {
                if (minutesAsleep.get(i).get(j)) {
                    if (!timesAsleepPerMinute.containsKey(j)) {
                        timesAsleepPerMinute.put(j, 0);
                    }
                    int newCount = timesAsleepPerMinute.get(j) + 1;
                    timesAsleepPerMinute.put(j, newCount);
                }
            }
        }

        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : timesAsleepPerMinute.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    public int getGuardIdMultipliedByMostSleptMinute(List<String> stringRecords) {
        List<Record> records = sortRecordsList(stringRecords);
        Map<DateGuardCombo, List<Boolean>> guardsSleepingMap = getGuardsSleepingHabits(records);
        int sleepyGuardId = getGuardMostAsleep(guardsSleepingMap);
        int sleepyMinute = getMinuteMostAsleep(guardsSleepingMap, sleepyGuardId);
        return sleepyGuardId*sleepyMinute;
    }

    private List<Integer> getSleepiestMinuteAndGuard(Map<DateGuardCombo, List<Boolean>> sleepingMap) {
        Map<Integer, List<Integer>> guardTimesAsleepPerMinute = new HashMap<>();

        for (DateGuardCombo k : sleepingMap.keySet()) {
            int guardId = k.getGuardId();
            if (!guardTimesAsleepPerMinute.containsKey(guardId)) {
                guardTimesAsleepPerMinute.put(guardId, new ArrayList<>());
                for (int i = 0; i < 60; i++) {
                    guardTimesAsleepPerMinute.get(guardId).add(0);
                }
            }
            for (int i = 0; i < 60; i++) {
                if (sleepingMap.get(k).get(i)) {
                    guardTimesAsleepPerMinute.get(guardId).set(i, guardTimesAsleepPerMinute.get(guardId).get(i) + 1);
                }
            }
        }
        int highestSleepCount = 0;
        int sleepiestMinute = -1;
        int sleepiestGuard = -1;

        for (Map.Entry<Integer, List<Integer>> guardMinuteSleepsEntry : guardTimesAsleepPerMinute.entrySet()) {
            for (int i = 0; i < 60; i++) {
                if (guardMinuteSleepsEntry.getValue().get(i) > highestSleepCount) {
                    highestSleepCount = guardMinuteSleepsEntry.getValue().get(i);
                    sleepiestMinute = i;
                    sleepiestGuard = guardMinuteSleepsEntry.getKey();
                }
            }
        }
        List<Integer> guardMinute = new ArrayList<>();
        guardMinute.add(sleepiestGuard);
        guardMinute.add(sleepiestMinute);
        return guardMinute;
    }

    public int getGuardIdMultipliedByMostSleptMinuteStrategyTwo(List<String> stringRecords) {
        List<Record> records = sortRecordsList(stringRecords);
        Map<DateGuardCombo, List<Boolean>> guardsSleepingMap = getGuardsSleepingHabits(records);
        List<Integer> guardMinute = getSleepiestMinuteAndGuard(guardsSleepingMap);
        return guardMinute.get(0) * guardMinute.get(1);
    }

    class Record implements Comparable<Record> {
        private LocalDateTime dateTime;
        private String event;

        Record(LocalDateTime dateTime, String event) {
            this.dateTime = dateTime;
            this.event = event;
        }

        LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        @Override
        public int compareTo(Record o) {
            return getDateTime().compareTo(o.getDateTime());
        }
    }

    class DateGuardCombo implements Comparable<DateGuardCombo> {
        private LocalDate date;
        private int guardId;

        DateGuardCombo(LocalDate date, int guardId) {
            this.date = date;
            this.guardId = guardId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        int getGuardId() {
            return guardId;
        }

        public void setGuardId(int guardId) {
            this.guardId = guardId;
        }

        @Override
        public int compareTo(DateGuardCombo o) {
            return Integer.compare(getGuardId(), o.getGuardId());
        }
    }
}
