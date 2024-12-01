package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day01_2 {

    private static final String DAY_NUMBER = "01";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();
            List<Long> left = new ArrayList<>();
            Map<Long, Long> right = new HashMap<>();

            for (String line : input) {
                String[] values = line.split("   ");
                long vLeft = Long.parseLong(values[0]);
                long vRight = Long.parseLong(values[1]);
                left.add(vLeft);
                right.put(vRight, right.getOrDefault(vRight, 0L) + 1L);
            }
            for (long vLeft : left) {
                result += vLeft * right.getOrDefault(vLeft, 0L);
            }

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}