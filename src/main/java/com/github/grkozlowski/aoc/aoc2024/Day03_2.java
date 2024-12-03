package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day03_2 {

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

            String memoryContent = memory.toString();
            Matcher mulMatcher = mulPattern.matcher(memoryContent);

            boolean isMulEnabled = true; // Initially, mul instructions are enabled

            for (int i = 0; i < memoryContent.length(); ) {
                if (memoryContent.startsWith("do()", i)) {
                    isMulEnabled = true;
                    i += 4; // Skip the length of "do()"
                } else if (memoryContent.startsWith("don't()", i)) {
                    isMulEnabled = false;
                    i += 7; // Skip the length of "don't()"
                } else {
                    if (mulMatcher.find(i) && mulMatcher.start() == i) {
                        if (isMulEnabled) {
                            long x = Long.parseLong(mulMatcher.group(1));
                            long y = Long.parseLong(mulMatcher.group(2));
                            result += x * y;
                        }
                        i = mulMatcher.end(); // Move past the current mul()
                    } else {
                        i++; // Skip invalid or unrelated characters
                    }
                }
            }

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}