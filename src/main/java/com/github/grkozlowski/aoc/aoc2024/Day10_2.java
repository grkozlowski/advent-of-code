package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day10_2 {

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

            result = calculateTrailheadRatings(map);

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateTrailheadRatings(long[][] map) {
        long totalRating = 0;
        int rows = map.length;
        int cols = map[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col] == 0) { // Found a trailhead
                    totalRating += calculateRatingForTrailhead(map, row, col);
                }
            }
        }
        return totalRating;
    }

    private static long calculateRatingForTrailhead(long[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;

        // Cache to store ratings for each position
        long[][] ratingsCache = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ratingsCache[i][j] = -1; // -1 means uncalculated
            }
        }

        return calculateDistinctTrails(map, startRow, startCol, ratingsCache);
    }

    private static long calculateDistinctTrails(long[][] map, int row, int col, long[][] ratingsCache) {
        if (ratingsCache[row][col] != -1) {
            return ratingsCache[row][col]; // Already visited - use cache
        }

        long trailsCount = 0;
        if (map[row][col] == 9) {
            trailsCount = 1;
        } else {
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if (isValidStep(map, row, col, newRow, newCol)) {
                    trailsCount += calculateDistinctTrails(map, newRow, newCol, ratingsCache);
                }
            }
        }

        ratingsCache[row][col] = trailsCount;
        return trailsCount;
    }

    private static boolean isValidStep(long[][] map, int currentRow, int currentCol, int newRow, int newCol) {
        return newRow >= 0 && newRow < map.length &&
                newCol >= 0 && newCol < map[0].length &&
                map[newRow][newCol] == map[currentRow][currentCol] + 1;
    }
}
