package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Day 1
//        List<Integer> frequencyChanges = new ArrayList<>();
//
//        while (true) {
//            String input = in.nextLine();
//            if (input.equals("end")) {
//                break;
//            }
//            frequencyChanges.add(Integer.valueOf(input));
//        }
//
//        DayOne dayOne = new DayOne();
//        System.out.println(dayOne.findFinalFrequency(frequencyChanges));
//        System.out.println(dayOne.findFirstFrequencyReachedTwice(frequencyChanges));

        // Day 2
//        List<String> ids = new ArrayList<>();
//
//        while (true) {
//            String input = in.nextLine();
//            if (input.contains(" ")) {
//                break;
//            }
//            ids.add(input);
//        }
//
//        DayTwo dayTwo = new DayTwo();
//        System.out.println(dayTwo.findChecksum(ids));
//        System.out.println(dayTwo.findCommonSequenceInBoxIds(ids));

        // Day 3
//        List<String> claims = new ArrayList<>();
//
//        while (true) {
//            String input =  in.nextLine();
//            if (input.length() == 1) {
//                break;
//            }
//            claims.add(input);
//        }
//
//        DayThree dayThree = new DayThree();
//        System.out.println(dayThree.getAreaOfOverlappingFabric(claims));
//        System.out.println(dayThree.getIdOfOnlyNonOverlappingClaim(claims));

        // Day 4
//        List<String> recordStrings = new ArrayList<>();
//
//        while (true) {
//            String input =  in.nextLine();
//            if (input.length() == 1) {
//                break;
//            }
//            recordStrings.add(input);
//        }
//
//        DayFour dayFour = new DayFour();
//        System.out.println(dayFour.getGuardIdMultipliedByMostSleptMinute(recordStrings));
//        System.out.println(dayFour.getGuardIdMultipliedByMostSleptMinuteStrategyTwo(recordStrings));

        // Day 5
        String input = in.nextLine();
        DayFive dayFive = new DayFive();
        System.out.println(dayFive.getFinalReactedPolymer(input).length());
        System.out.println(dayFive.getShortestFinalPolymer(input));
    }
}
