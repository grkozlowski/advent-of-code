package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day02_2 {

    private static final String DAY_NUMBER = "02";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();
            for (String line : input) {
                List<Long> values = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();

                if (isSafeWithDampener(values)) {
                    result++;
                }
            }

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isSafeWithDampener(List<Long> report) {
        if (report.size() < 2) return false;

        // If the report is already safe, return true
        if (isSafeReport(report)) {
            return true;
        }

        // Try removing each level and check if the report becomes safe
        for (int i = 0; i < report.size(); i++) {
            List<Long> modifiedReport = new ArrayList<>(report);
            modifiedReport.remove(i);
            if (isSafeReport(modifiedReport)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSafeReport(List<Long> report) {
        if (report.size() < 2) return false;

        boolean isIncreasing = report.get(1) > report.get(0);
        boolean isConsistent = true;

        for (int i = 1; i < report.size(); i++) {
            long diff = report.get(i) - report.get(i - 1);

            if (isIncreasing) {
                if (diff < 1 || diff > 3) {
                    isConsistent = false;
                    break;
                }
            } else {
                if (diff > -1 || diff < -3) {
                    isConsistent = false;
                    break;
                }
            }

            if ((isIncreasing && diff < 0) || (!isIncreasing && diff > 0)) {
                isConsistent = false;
                break;
            }
        }

        return isConsistent;
    }
}
