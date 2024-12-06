package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day06_2 {

    private static final String DAY_NUMBER = "06";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    private static int startX = -1;
    private static int startY = -1;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            setStart(input);
            result = countObstructionPositions(input);

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setStart(List<String> map) {
        for (int r = 0; r < map.size(); r++) {
            for (int c = 0; c < map.get(r).length(); c++) {
                if (map.get(r).charAt(c) == '^') {
                    startX = r;
                    startY = c;
                    return;
                }
            }
        }
    }

    private static long countObstructionPositions(List<String> map) {
        int rows = map.size();
        int cols = map.get(0).length();
        long obstructionCount = 0;

        // Try placing an obstruction at every empty position
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map.get(i).charAt(j) == '.') {
                    if (createsLoop(map, i, j)) {
                        obstructionCount++;
                    }
                }
            }
        }

        return obstructionCount;
    }

    private static boolean createsLoop(List<String> map, int obstructionX, int obstructionY) {
        // Directions: {dx, dy} == {dRow, dCol} for up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        // Create a modified map with the obstruction
        char[][] modifiedMap = new char[map.size()][];
        for (int i = 0; i < map.size(); i++) {
            modifiedMap[i] = map.get(i).toCharArray();
        }
        modifiedMap[obstructionX][obstructionY] = '#';

        Set<String> visitedStates = new HashSet<>();
        int x = startX, y = startY, direction = 0;
        visitedStates.add(x + "," + y + "," + direction);

        while (true) {
            int nextX = x + directions[direction][0];
            int nextY = y + directions[direction][1];

            // Check if next position is within bounds
            if (nextX < 0 || nextX >= modifiedMap.length || nextY < 0 || nextY >= modifiedMap[0].length) {
                return false; // Guard leaves the map
            }

            // Check if next position is an obstacle
            char nextCell = modifiedMap[nextX][nextY];
            if (nextCell == '#') {
                // Turn right (90 degrees clockwise)
                direction = (direction + 1) % 4;
            } else {
                // Move forward
                x = nextX;
                y = nextY;
            }

            String state = x + "," + y + "," + direction;

            // Check for a loop
            if (visitedStates.contains(state)) {
                return true; // Loop detected
            }

            visitedStates.add(state);
        }
    }
}
