package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayTwo {

    public int findChecksum(List<String> ids) {
        Map<String, Integer> map = findCountWithExactlyTwoAndExactlyThreeOfLetter(ids);
        int countTwo = map.get("NumberOfIdsWithTwoLetters");
        int countThree = map.get("NumberOfIdsWithThreeLetters");
        return countThree * countTwo;
    }

    private Map<String, Integer> findCountWithExactlyTwoAndExactlyThreeOfLetter(List<String> ids) {
        int countTwo = 0;
        int countThree = 0;
        Map<String, Integer> twoAndThreeCountsMap = new HashMap<>();
        for (String id : ids) {
            Map<Character, Integer> letterCountMap = new HashMap<>();
            for (char letter : id.toCharArray()) {
                if (!letterCountMap.containsKey(letter)) {
                    letterCountMap.put(letter, 0);
                }
                letterCountMap.put(letter, letterCountMap.get(letter) + 1);
            }
            if (letterCountMap.containsValue(2)) {
                countTwo++;
            }
            if (letterCountMap.containsValue(3)) {
                countThree++;
            }
        }
        twoAndThreeCountsMap.put("NumberOfIdsWithTwoLetters", countTwo);
        twoAndThreeCountsMap.put("NumberOfIdsWithThreeLetters", countThree);
        return twoAndThreeCountsMap;
    }

    private List<String> findTwoBoxIds(List<String> ids) {
        List<String> boxIds = new ArrayList<>();
        for (int i = 0; i < ids.size() - 1; i++) {
            for (int j = i+1; j < ids.size(); j++) {
                int countWrong = 0;
                for (int k = 0; k < ids.get(i).length(); k++) {
                    if (ids.get(i).charAt(k) != ids.get(j).charAt(k)) {
                        countWrong++;
                    }
                    if (countWrong > 1) {
                        break;
                    }
                }
                if (countWrong == 1) {
                    String idOne = ids.get(i);
                    String idTwo = ids.get(j);
                    boxIds.add(idOne);
                    boxIds.add(idTwo);
                }
            }
        }
        return boxIds;
    }

    public String findCommonSequenceInBoxIds(List<String> ids) {
        StringBuilder commonSequence = new StringBuilder();
        List<String> twoMatchingBoxIds = findTwoBoxIds(ids);
        for (int i = 0; i < twoMatchingBoxIds.get(0).length(); i++) {
            if (twoMatchingBoxIds.get(0).charAt(i) == twoMatchingBoxIds.get(1).charAt(i)) {
                commonSequence.append(twoMatchingBoxIds.get(0).charAt(i));
            }
        }
        return commonSequence.toString();
    }
}
