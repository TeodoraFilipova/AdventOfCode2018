package com.company;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class DayFive {
    private static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public String getFinalReactedPolymer(String input) {
        Queue<Character> polymerQueue = new LinkedList<>();
        Stack<Character> usedCharsStack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            polymerQueue.offer(input.charAt(i));
        }

        usedCharsStack.push(polymerQueue.poll());

        while (!polymerQueue.isEmpty()) {
            if (usedCharsStack.isEmpty()) {
                usedCharsStack.push(polymerQueue.poll());
            }
            if ((Character.isLowerCase(usedCharsStack.peek())
                    && Character.isUpperCase(polymerQueue.element())
                    && usedCharsStack.peek() == Character.toLowerCase(polymerQueue.element()))
                    || (Character.isUpperCase(usedCharsStack.peek())
                    && Character.isLowerCase(polymerQueue.element())
                    && usedCharsStack.peek() == Character.toUpperCase(polymerQueue.element()))) {
                usedCharsStack.pop();
                polymerQueue.poll();
            } else {
                usedCharsStack.push(polymerQueue.poll());
            }
        }

        StringBuilder stackString = new StringBuilder();
        while (!usedCharsStack.isEmpty()) {
            stackString.append(usedCharsStack.pop());
        }

        StringBuilder result = new StringBuilder();
        for (int i = stackString.toString().length() - 1; i >= 0; i--) {
            result.append(stackString.toString().charAt(i));
        }

        return result.toString();
    }

    private String removeAllUnitsOfType(String polymer, char unitType) {
        char unitTypeToRemove = Character.toLowerCase(unitType);
        StringBuilder newPolymer = new StringBuilder();
        for (int i = 0; i < polymer.length(); i++) {
            if (Character.toLowerCase(polymer.charAt(i)) != unitTypeToRemove) {
                newPolymer.append(polymer.charAt(i));
            }
        }
        return newPolymer.toString();
    }

    public int getShortestFinalPolymer(String input) {
        int shortestPolymer = Integer.MAX_VALUE;
        for (char LETTER : LETTERS) {
            String newPolymer = removeAllUnitsOfType(input, LETTER);
            int length = getFinalReactedPolymer(newPolymer).length();
            shortestPolymer = Math.min(shortestPolymer, length);
        }
        return shortestPolymer;
    }

}
