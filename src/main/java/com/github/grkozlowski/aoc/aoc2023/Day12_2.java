package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day12_2 {

    private static final String DAY_NUMBER = "12";
    private static final String testText = """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
                """;
    private static long result = 0L;
    private static Map<String, Long> memo;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            int checked = 0;
            for (String line : input) {
                String[] patternGroups = line.split(" ");
                String pattern = patternGroups[0] + "?" + patternGroups[0] + "?" + patternGroups[0] + "?" + patternGroups[0] + "?" + patternGroups[0] + ".";
                List<Integer> groups = Arrays.stream(patternGroups[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
                int size = groups.size();
                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < size; ++j) {
                        groups.add(groups.get(j));
                    }
                }
                memo = new HashMap<>();
                long subResult = computeArrangements(0, 0, pattern, groups, 0);
                System.out.println("subResult["+(++checked)+"]:"+subResult);
                System.out.println("pattern["+(checked)+"]:"+pattern.substring(0, pattern.length() - 1));
                System.out.println("groups["+(checked)+"]:"+groups);
                System.out.println("---------------------");

                result += subResult;
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long computeArrangements(int indexP, int indexG, String pattern, List<Integer> expectedGroups, long total) {
        if (memo.containsKey(pattern + "|" + expectedGroups + "|" + indexP + "|" + indexG)) {
            return memo.get(pattern + "|" + expectedGroups + "|" + indexP + "|" + indexG);
        }
        if (indexP >= pattern.length()) {
            return indexG == expectedGroups.size() ? 1 : 0;
        }
        if (indexG == expectedGroups.size()) {
            return pattern.substring(indexP).contains("#") ? 0 : 1;
        }

        if (".?".contains(pattern.substring(indexP, indexP + 1))) {
            total += computeArrangements(indexP + 1, indexG, pattern, expectedGroups, 0L);
        }

        if ("#?".contains(pattern.substring(indexP, indexP + 1))) {
            int groupSize = expectedGroups.get(indexG);
            int endIndex = indexP + groupSize;
            if (endIndex < pattern.length()) {
                if (!pattern.substring(indexP, endIndex).contains(".")) {
                    if (pattern.charAt(endIndex) != '#') {
                        total += computeArrangements(endIndex + 1, indexG + 1, pattern, expectedGroups, 0L);
                    }
                }
            }
        }

        memo.put(pattern + "|" + expectedGroups + "|" + indexP + "|" + indexG, total);

        return total;
    }

}