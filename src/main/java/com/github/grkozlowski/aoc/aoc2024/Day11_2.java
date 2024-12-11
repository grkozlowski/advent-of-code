package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day11_2 {

    private static final String DAY_NUMBER = "11";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));

            Map<Long, Long> stoneCounts = new HashMap<>();
            for (String line : reader.readLine().split(" ")) {
                long stone = Long.parseLong(line);
                stoneCounts.put(stone, stoneCounts.getOrDefault(stone, 0L) + 1);
            }

            int blinks = 75;
            stoneCounts = processBlinks(stoneCounts, blinks);
            result = stoneCounts.values().stream().mapToLong(Long::longValue).sum();

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Long, Long> processBlinks(Map<Long, Long> stoneCounts, int blinks) {
        for (int blink = 0; blink < blinks; blink++) {
            Map<Long, Long> nextStoneCounts = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stoneCounts.entrySet()) {
                long stone = entry.getKey();
                long count = entry.getValue();

                if (stone == 0) {
                    nextStoneCounts.put(1L, nextStoneCounts.getOrDefault(1L, 0L) + count);
                } else if (hasEvenDigits(stone)) {
                    splitStone(stone, count, nextStoneCounts);
                } else {
                    long newStone = stone * 2024;
                    nextStoneCounts.put(newStone, nextStoneCounts.getOrDefault(newStone, 0L) + count);
                }
            }
            stoneCounts = nextStoneCounts;
        }
        return stoneCounts;
    }

    // Calculate number of digits without converting to String
    private static boolean hasEvenDigits(long number) {
        int digits = (int) Math.log10(number) + 1;
        return digits % 2 == 0;
    }

    private static void splitStone(long stone, long count, Map<Long, Long> nextStoneCounts) {
        int digits = (int) Math.log10(stone) + 1;
        int mid = digits / 2;
        long divisor = (long) Math.pow(10, mid);
        long left = stone / divisor;
        long right = stone % divisor;
        nextStoneCounts.put(left, nextStoneCounts.getOrDefault(left, 0L) + count);
        nextStoneCounts.put(right, nextStoneCounts.getOrDefault(right, 0L) + count);
    }
}
