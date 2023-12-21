package com.example.aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day21_2 {
    private static long result = 0L;
    private static final int[][] dirs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    private static final int steps = 26501365;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day21.txt"));
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

            int size = map.length;
            assert map.length == map[0].length;
            assert startRow == startCol;
            assert startRow == size / 2;
            assert steps % size == size / 2;

            System.out.println("steps / size:" + steps / size);

            long gridWidth = steps / size - 1;
            long oddGridsCount = (long) Math.pow(gridWidth / 2 * 2 + 1.0, 2);
            long evenGridsCount = (long) Math.pow((gridWidth + 1) / 2 * 2, 2);

            System.out.println("oddGridsCount:" + oddGridsCount);
            System.out.println("evenGridsCount:" + evenGridsCount);

            long oddPoints = search(startRow, startCol, size * 2 + 1, map);
            long evenPoints = search(startRow, startCol, size * 2, map);

            System.out.println("oddPoints:" + oddPoints);
            System.out.println("evenPoints:" + evenPoints);

            long cornerTop = search(size - 1, startCol, size - 1, map);
            long cornerRight = search(startRow, 0, size - 1, map);
            long cornerLeft = search(0, startCol, size - 1, map);
            long cornerBottom = search(startRow, size - 1, size - 1, map);

            long smallTopRight = search(size - 1, 0, size / 2 - 1, map);
            long smallTopLeft = search(size - 1, size - 1, size / 2 - 1, map);
            long smallBottomRight = search(0, 0, size / 2 - 1, map);
            long smallBottomLeft = search(0, size - 1, size / 2 - 1, map);

            long bigTopRight = search(size - 1, 0, (3 * size) / 2 - 1, map);
            long bigTopLeft = search(size - 1, size - 1, (3 * size) / 2 - 1, map);
            long bigBottomRight = search(0, 0, (3 * size) / 2 - 1, map);
            long bigBottomLeft = search(0, size - 1, (3 * size) / 2 - 1, map);

            result += oddGridsCount * oddPoints +
                    evenGridsCount * evenPoints +
                    cornerTop + cornerRight + cornerLeft + cornerBottom +
                    (gridWidth + 1) * (smallTopRight + smallTopLeft + smallBottomRight + smallBottomLeft) +
                    gridWidth * (bigTopRight + bigTopLeft + bigBottomRight + bigBottomLeft);

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