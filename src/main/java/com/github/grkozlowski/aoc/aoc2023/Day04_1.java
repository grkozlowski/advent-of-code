package com.github.grkozlowski.aoc.aoc2023;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day04_1 {

    private static final String DAY_NUMBER = "04";
    private static final String testText = """
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                """;
    private static long result = 0;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
            for (String line : reader.lines().toList()) {
//            for (String line : testText.split("\n")) {
                int winnings = 0;
                Set<String> winningNumbers = getNumbersSet(
                        line.indexOf(":") + 1,
                        line.indexOf("|"),
                        line
                );
                Set<String> myNumbers = getNumbersSet(
                        line.indexOf("|") + 2,
                        line.length(),
                        line
                );
                for (String number : myNumbers) {
                    if (winningNumbers.contains(number)) {
                        winnings++;
                    }
                }
                if (winnings > 0) {
                    result += Math.pow(2, winnings - 1);
                }
            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getNumbersSet(int start, int end, String line) {
        return Arrays.stream(
                line
                        .substring(start, end)
                        .split(" "))
                .map(String::trim)
                .filter(s -> !s.equals(""))
                .collect(Collectors.toSet());
    }
}