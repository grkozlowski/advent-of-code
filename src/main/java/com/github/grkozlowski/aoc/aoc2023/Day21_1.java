package com.github.grkozlowski.aoc.aoc2023;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day21_1 {

    private static final String DAY_NUMBER = "21";
    private static final String testText = """
...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........
                """;
    private static long result = 0L;
    private static int[][] dirs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    private static final int steps = 64;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            char[][] map = new char[input.size()][input.get(0).length()];
            int startRow = -1, startCol = -1;

            for (int row = 0; row < input.size(); ++row) {
                String line = input.get(row);
                for (int col = 0; col < line.length(); ++col) {
                    char c = line.charAt(col);
                    if (c == 'S') {
                        startRow = row;
                        startCol = col;
                        c = '.';
                    }
                    map[row][col] = c;
                }
            }

            System.out.println(Arrays.deepToString(map));

            result = search(startRow, startCol, steps, map);

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long search(int startRow, int startCol, int startStepsLeft, char[][] map) {
        Set<String> result = new HashSet<>();

        boolean[][] seen = new boolean[map.length][map[0].length];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{startRow, startCol, startStepsLeft});

        while (!q.isEmpty()) {
            int[] position = q.poll();
            int row = position[0];
            int col = position[1];
            int stepsLeft = position[2];

            if (
                row < 0 ||
                col < 0 ||
                row >= map.length ||
                col >= map[0].length ||
                map[row][col] == '#' ||
                seen[row][col] ||
                stepsLeft < 0
            ) {
                continue;
            }

            seen[row][col] = true;
            if (stepsLeft % 2 == 0) { // because, for every grid we can come back to it by going forward and coming back, so we are sure that it is reachable at the end
                result.add(row + "|" + col);
            }

            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                q.add(new int[]{newRow, newCol, stepsLeft - 1});
            }
        }

        return result.size();
    }

}