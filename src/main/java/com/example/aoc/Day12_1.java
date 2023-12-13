package com.example.aoc;


import java.io.*;
import java.util.*;

public class Day12_1 {

    private static final String testText = """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day12.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            int checked = 0;
            for (String line : input) {
                String[] patternGroups = line.split(" ");
                long subResult = computeArrangements(
                        patternGroups[0],
                        Arrays.stream(patternGroups[1].split(",")).map(Integer::parseInt).toList());
                System.out.println("subResult["+(++checked)+"]:"+subResult);
                result += subResult;
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long computeArrangements(String pattern, List<Integer> expectedGroups) {
        int unknowns = 0;
        for (char ch : pattern.toCharArray()) {
            if (ch == '?') {
                unknowns++;
            }
        }

        long patternResult = 0L;
        for (int i = 0; i < Math.pow(2, unknowns); ++i) {
            String currentPattern = pattern;
            for (int j = 0; j < unknowns; ++j) {
                String rep = (i >> j) % 2 == 0 ? "." : "#";
                currentPattern = currentPattern.replaceFirst("\\?", rep);
            }
            if (isPatternOk(currentPattern, expectedGroups)) {
                patternResult++;
            }
        }

        return patternResult;
    }

    private static boolean isPatternOk(String pattern, List<Integer> expectedGroups) {
//        System.out.println("pattern:"+pattern);
        int currentGroupSize = 0;
        List<Integer> patternGroups = new ArrayList<>();
        for (char ch : pattern.toCharArray()) {
            if (ch == '.') { // working spring
                if (currentGroupSize > 0) {
                    patternGroups.add(currentGroupSize);
                }
                currentGroupSize = 0;
            }
            if (ch == '#') { // damaged spring
                currentGroupSize++;
            }
        }
        if (currentGroupSize > 0) {
            patternGroups.add(currentGroupSize);
        }

        return patternGroups.equals(expectedGroups);
    }
}