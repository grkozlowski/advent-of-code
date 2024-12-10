package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day10_1 {

    private static final String DAY_NUMBER = "10";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();
            long[][] map = input
                    .stream()
                    .map(line -> line.chars().mapToLong(ch -> ch - '0').toArray())
                    .toArray(long[][]::new);

            result = calculateTrailheadScores(map);

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateTrailheadScores(long[][] map) {
        long totalScore = 0;
        int rows = map.length;
        int cols = map[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col] == 0) {
                    totalScore += calculateScoreForTrailhead(map, row, col);
                }
            }
        }
        return totalScore;
    }

    private static long calculateScoreForTrailhead(long[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        long score = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            if (map[row][col] == 9) {
                score++;
            }

            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (isValidStep(map, visited, row, col, newRow, newCol)) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return score;
    }

    private static boolean isValidStep(long[][] map, boolean[][] visited, int currentRow, int currentCol, int newRow, int newCol) {
        return newRow >= 0 && newRow < map.length &&
                newCol >= 0 && newCol < map[0].length &&
                !visited[newRow][newCol] &&
                map[newRow][newCol] == map[currentRow][currentCol] + 1;
    }
}
