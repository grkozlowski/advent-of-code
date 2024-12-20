package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day01_1 {

    private static final String DAY_NUMBER = "01";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();
            List<Long> left = new ArrayList<>();
            List<Long> right = new ArrayList<>();

            for (String line : input) {
                String[] values = line.split("   ");
                left.add(Long.parseLong(values[0]));
                right.add(Long.parseLong(values[1]));
            }
            left.sort(Long::compare);
            right.sort(Long::compare);

            for (int i = 0; i < left.size(); ++i) {
                result += Math.abs(left.get(i) - right.get(i));
            }

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}