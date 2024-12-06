package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day06_1 {

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
            result = simulateGuardPath(input);

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

    private static long simulateGuardPath(List<String> map) {
        // Directions: {dx, dy} == {dRow, dCol} for up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        Set<String> visited = new HashSet<>();
        int x = startX, y = startY, direction = 0;
        visited.add(x + "," + y);

        // Simulate guard movement
        while (true) {
            int nextX = x + directions[direction][0];
            int nextY = y + directions[direction][1];

            // Check if next position is within bounds
            if (nextX < 0 || nextX >= map.size() || nextY < 0 || nextY >= map.get(0).length()) {
                break; // Guard has left the map
            }

            // Check if next position is an obstacle
            char nextCell = map.get(nextX).charAt(nextY);
            if (nextCell == '#') {
                // Turn right (90 degrees clockwise)
                direction = (direction + 1) % 4;
            } else {
                // Move forward
                x = nextX;
                y = nextY;
                visited.add(x + "," + y);
            }
        }

        return visited.size();
    }
}
