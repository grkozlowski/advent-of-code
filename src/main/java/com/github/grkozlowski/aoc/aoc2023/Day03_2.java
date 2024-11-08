package com.github.grkozlowski.aoc.aoc2023;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day03_2 {

    private static final String DAY_NUMBER = "03";
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
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
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

                for (int c = 1; c < engin[0].length - 1; ++c) {
                    Character currentChar = engin[r][c];
                    if (currentChar.equals('*')) {
                        result += getGearPower(r, c);
                    }
                }

            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getGearPower(int r, int c) {
        int[][] positionValue = new int[][]{
                {-1, -1, 0},
                {-1, -1, 0}
        };
        for (int[] dir : dirs) {
            int newR = r + dir[0];
            int newC = c + dir[1];
            Character currentChar = engin[newR][newC];
            if (Character.isDigit(currentChar)) {
                int[] digitData = getDigitData(newR, newC);
                if (positionValue[0][0] == digitData[0] && positionValue[0][1] == digitData[1] && positionValue[0][2] == digitData[2]) {
                    continue; // value already found
                }
                if (positionValue[1][0] == digitData[0] && positionValue[1][1] == digitData[1] && positionValue[1][2] == digitData[2]) {
                    continue; // value already found
                }
                if (positionValue[0][0] == -1) { // space not taken so far
                    positionValue[0] = digitData;
                } else if (positionValue[1][0] == -1) { // space not taken so far
                    positionValue[1] = digitData;
                } else {
                    return 0L; // 2 digits already found
                }
            }

        }

        System.out.println("Gear found at:["+ (r) + "|" + (c) + "]");
        System.out.println(Arrays.deepToString(positionValue));

        return (long) positionValue[0][2] * positionValue[1][2];
    }

    private static int[] getDigitData(int r, int c) {
        int[] result = new int[]{r, -1, -1};

        while(c - 1  >= 0 && Character.isDigit(engin[r][c - 1])) {
            --c;
        }
        result[1] = c;

        int value = engin[r][c] - '0';
        while (c + 1 < engin[0].length && Character.isDigit(engin[r][c + 1])) {
            ++c;
            value *= 10;
            value += engin[r][c] - '0';
        }
        result[2] = value;

        return result;
    }
}