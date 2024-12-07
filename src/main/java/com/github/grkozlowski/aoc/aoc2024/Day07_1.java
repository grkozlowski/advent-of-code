package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day07_1 {

    private static final String DAY_NUMBER = "07";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            result = calculateCalibrationResult(input);

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateCalibrationResult(List<String> lines) {
        long totalCalibration = 0;

        for (String line : lines) {
            String[] parts = line.split(":");
            long testValue = Long.parseLong(parts[0].trim());
            String[] numbers = parts[1].trim().split("\\s+");
            List<Long> values = new ArrayList<>();

            for (String num : numbers) {
                values.add(Long.parseLong(num));
            }

            if (canProduceTestValue(testValue, values, 0, values.get(0))) {
                totalCalibration += testValue;
            }
        }

        return totalCalibration;
    }

    private static boolean canProduceTestValue(long target, List<Long> numbers, int index, long currentResult) {
        // Base case: if we've used all the numbers
        if (index == numbers.size() - 1) {
            return currentResult == target;
        }

        // Recursive case: try both operators (+ and *)
        long nextNumber = numbers.get(index + 1);
        return canProduceTestValue(target, numbers, index + 1, currentResult + nextNumber) ||
                canProduceTestValue(target, numbers, index + 1, currentResult * nextNumber);
    }
}
