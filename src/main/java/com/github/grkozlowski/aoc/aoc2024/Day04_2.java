package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day04_2 {

    private static final String DAY_NUMBER = "04";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            result = countXMASOccurrences(input);

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long countXMASOccurrences(List<String> grid) {
        int rows = grid.size();
        int cols = grid.get(0).length();
        char[][] matrix = new char[rows][cols];

        // Convert the grid to a character matrix
        for (int i = 0; i < rows; i++) {
            matrix[i] = grid.get(i).toCharArray();
        }

        String[] targets = {"MAS", "SAM"};
        long count = 0L;

        // Traverse the grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for (String targetLeft: targets) {
                    for (String targetRight: targets) {
                        if (isMatch(matrix, row, col, 1, 1, targetLeft)) {
                            if (isMatch(matrix, row, col + 2, 1, -1, targetRight)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private static boolean isMatch(char[][] matrix, int startRow, int startCol, int rowOffset, int colOffset, String target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < target.length(); i++) {
            int newRow = startRow + i * rowOffset;
            int newCol = startCol + i * colOffset;

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || matrix[newRow][newCol] != target.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}