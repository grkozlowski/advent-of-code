package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day03_1 {

    private static final String DAY_NUMBER = "03";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            Pattern mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
            StringBuilder memory = new StringBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();
            for (String line : input) {
                memory.append(line);
            }

            Matcher matcher = mulPattern.matcher(memory.toString());

            while (matcher.find()) {
                long x = Long.parseLong(matcher.group(1));
                long y = Long.parseLong(matcher.group(2));
                result += x * y;
            }

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}