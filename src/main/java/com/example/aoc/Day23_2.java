package com.example.aoc;

import java.io.*;
import java.util.List;

public class Day23_2 {

    private static final String testText = """
#.#####################
#.......#########...###
#######.#########.#.###
###.....#.>.>.###.#.###
###v#####.#v#.###.#.###
###.>...#.#.#.....#...#
###v###.#.#.#########.#
###...#.#.#.......#...#
#####.#.#.#######.#.###
#.....#.#.#.......#...#
#.#####.#.#.#########v#
#.#...#...#...###...>.#
#.#.#v#######v###.###v#
#...#.>.#...>.>.#.###.#
#####v#.#.###v#.#.###.#
#.....#...#...#.#.#...#
#.#########.###.#.#.###
#...###...#...#...#.###
###.###.#.###v#####v###
#...#...#.#.>.>.#.>.###
#.###.###.#.###.#.#v###
#.....###...###...#...#
#####################.#
                """;
    private static long result = 0L;
    private static char[][] map;
    private static boolean[][] seen;
    private static final int[][] dirs = new int[][]{
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };
    private static int startRow;
    private static int startCol;
    private static int endRow;
    private static int endCol;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day23.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            map = new char[input.size()][input.get(0).length()];
            seen = new boolean[input.size()][input.get(0).length()];
            startRow = 0;
            startCol = 1;
            endRow = map.length - 1;
            endCol = map[0].length - 2;

            for (int row = 0; row < input.size(); ++row) {
                String line = input.get(row);
                for (int col = 0; col < line.length(); ++col) {
                    char c = line.charAt(col);
                    map[row][col] = c;
                    if (c == '#') {
                        seen[row][col] = true;
                    }
                }
            }

            dfs(startRow, startCol, -1L);

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dfs(int row, int col, long stepsSoFar) {
        if (row < 0 ||
        col < 0 ||
        row >= map.length ||
        col >= map[0].length ||
        seen[row][col]) {
            return;
        }
        seen[row][col] = true;

        stepsSoFar += 1;

        if (row == endRow &&
        col == endCol) {
            System.out.println("computingResult");
            result = Math.max(result, stepsSoFar);
        }

        for (int[] dir : dirs) {
            dfs(row + dir[0], col + dir[1], stepsSoFar);
        }

        seen[row][col] = false;
    }

}