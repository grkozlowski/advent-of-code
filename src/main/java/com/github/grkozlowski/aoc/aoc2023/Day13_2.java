package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day13_2 {

    private static final String DAY_NUMBER = "13";
    private static final String testText = """
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            List<String> currentPattern = new ArrayList<>();
            for (int lineIndex = 0; lineIndex < input.size(); ++lineIndex) {
                String line = input.get(lineIndex);
                if (!line.isBlank()) {
                    currentPattern.add(line);
                    if (lineIndex != input.size() - 1) {
                        continue;
                    }
                }
                char[][] pattern = new char[currentPattern.size()][currentPattern.get(0).length()];
                for (int i = 0; i < currentPattern.size(); ++i) {
                    pattern[i] = currentPattern.get(i).toCharArray();
                }

                int horizontal = -1;
                for (int i = 0; i < pattern.length - 1; ++i) {
                    if (isHorizontalMirror(pattern, i)) {
                        horizontal = i;
                        break;
                    }
                }

                System.out.println(Arrays.deepToString(pattern));
                pattern = transposeMatrix(pattern);
                System.out.println(Arrays.deepToString(pattern));

                int vertical = -1;
                for (int i = 0; i < pattern.length - 1; ++i) {
                    if (isHorizontalMirror(pattern, i)) {
                        vertical = i;
                        break;
                    }
                }

                int subResult = vertical + 1 + (horizontal + 1) * 100;
                System.out.println("horizontal:"+horizontal+"|vertical:"+vertical+"|subResult:"+subResult);
                assert vertical == -1 || horizontal == -1;
                result += subResult;
                currentPattern = new ArrayList<>();
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isHorizontalMirror(char[][] pattern, int index) {
        boolean imperfectionFound = false;
        for (int col = 0; col < pattern[0].length; ++col) {
            for (int row = 0; row < index + 1; ++row) {
                int oppositeRow = index * 2 - row + 1;
                if (oppositeRow < pattern.length) {
                    if (pattern[row][col] != pattern[oppositeRow][col]) {
                        if (!imperfectionFound) {
                            imperfectionFound = true;
                        } else {
                            return false;
                        }
                    }
                }

            }
        }

        return imperfectionFound;
    }

    public static char[][] transposeMatrix(char [][] m){
        char [][] result = new char[m[0].length][m.length];
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                result[col][row] = m[row][col];
            }
        }

        return result;
    }

}