package com.example.aoc;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Day03_1 {

    private static final int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
    private static Character[][] engin = null;
    private static final String testText = """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
                """;

    public static void main(String[] args) {

        try {
            long result = 0;
            BufferedReader reader = new BufferedReader(new FileReader("input/day03.txt"));
            List<String> lines = reader.lines().toList();
//            List<String> lines = Arrays.stream(testText.split("\n")).toList();
            engin = new Character[lines.size() + 2][lines.get(0).length() + 2];
            // prepare engin
            for (Character[] row : engin) {
                Arrays.fill(row, '.');
            }
            // fill the engin with input
            for (int i = 0; i < lines.size(); ++i) {
                String line = lines.get(i);
                for (int j = 0; j < line.length(); ++j) {
                    engin[i + 1][j + 1] = line.charAt(j);
                }
            }
            System.out.println(Arrays.deepToString(engin));

            for (int r = 1; r < engin.length - 1; ++r) {
                int subValue = -1;
                boolean isEnginPart = false;
                for (int c = 1; c < engin[0].length - 1; ++c) {
                    Character currentChar = engin[r][c];
                    if (Character.isDigit(currentChar)) {
                        // increase subValue
                        if (subValue == -1) {
                            subValue = currentChar - '0';
                        } else {
                            subValue *= 10;
                            subValue += currentChar - '0';
                        }
                        // check if this is an engine part
                        if (!isEnginPart && checkIfPart(r, c)) {
                            isEnginPart = true;
                        }
                    } else {
                        // increase subValue if until now we have seen digit that matches the condition of being engine part
                        if (subValue != -1 && isEnginPart) {
                            result += subValue;
                        }
                        // reset temp values as we see some non-digit character
                        subValue = -1;
                        isEnginPart = false;
                    }
                }
                // check for elements that span untill the end of line
                if (subValue != -1 && isEnginPart) {
                    result += subValue;
                }
            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkIfPart(int r, int c) {
        for (int[] dir : dirs) {
            Character currentChar = engin[r + dir[0]][c + dir[1]];
            if (!Character.isDigit(currentChar) && !currentChar.equals('.')) {
                return true;
            }
        }

        return false;
    }
}