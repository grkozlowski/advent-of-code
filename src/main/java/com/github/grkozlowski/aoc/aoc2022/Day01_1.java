package com.github.grkozlowski.aoc.aoc2022;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2022.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day01_1 {

    private static final String DAY_NUMBER = "01";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            long subSum = 0L;
            for (String line : input) {
                if (line.equals("")) {
                    result = Math.max(result, subSum);
                    subSum = 0L;
                    continue;
                }
                subSum += Long.parseLong(line);
            }
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}