package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day14_1 {

    private static final String DAY_NUMBER = "14";
    private static final String testText = """
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            char[][] map = new char[input.size()][input.get(0).length()];
            for (int row = 0; row < input.size(); ++row) {
                for (int col = 0; col < input.get(0).length(); ++col) {
                    map[row][col] = input.get(row).charAt(col);
                }
            }

            System.out.println("=== input ===");
            System.out.println(Arrays.deepToString(map));

            rollStones(map);

            System.out.println("=== rolled stones ===");
            System.out.println(Arrays.deepToString(map));

            result = computeResult(map);

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long computeResult(char[][] map) {
        long result = 0L;
        int rowsCount = map.length;
        for (int col = 0; col < map[0].length; ++col) {
            for (int row = 0; row < rowsCount; ++row) {
                char c = map[row][col];
                if (c == 'O') {
                    result += rowsCount - row;
                }
            }
        }

        return result;
    }

    private static void rollStones(char[][] map) {
        for (int col = 0; col < map[0].length; ++col) {
            int freeIndex = 0;
            for (int row = 0; row < map.length; ++row) {
                char c = map[row][col];
                if (c == '#') {
                    freeIndex = row + 1;
                } else if (c == 'O') {
                    map[row][col] = '.';
                    map[freeIndex++][col] = 'O';
                }
            }
        }
    }

}