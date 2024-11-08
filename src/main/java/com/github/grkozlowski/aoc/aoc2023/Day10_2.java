package com.github.grkozlowski.aoc.aoc2023;


import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day10_2 {

    private static final String DAY_NUMBER = "10";
    private static final String testText = """
FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L
                """;
    private static long steps = 0L;
    private static long result = 0L;
    private static Character[][] map;
    private static Integer[] start;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));

            List<String> lines = reader.lines().toList();
            map = new Character[lines.size()][lines.get(0).length()];
            for (int r = 0; r < lines.size(); ++r) {
                String line = lines.get(r);
                for (int c = 0; c < line.length(); ++c) {
                    char ch = line.charAt(c);
                    if (ch == 'S') {
                        start = new Integer[]{r, c};
                    }
                    map[r][c] = ch;
                }
            }
            System.out.println(Arrays.deepToString(map));

            Integer[] currentPosition = getConnectedCells(start)[0];
            Integer[] previousPosition = start;
            GeneralPath path = new GeneralPath();
            path.moveTo(start[0], start[1]);
            while (true) {
                steps++;
                path.lineTo(currentPosition[0], currentPosition[1]);
                System.out.println(steps + ":" + Arrays.deepToString(currentPosition));
                Integer[] newPosition = getNextPosition(currentPosition, previousPosition);
                if (!Arrays.equals(start, previousPosition)) {
                    map[previousPosition[0]][previousPosition[1]] = '*';
                }
                previousPosition = currentPosition;
                currentPosition = newPosition;
                if (map[currentPosition[0]][currentPosition[1]] == 'S') {
                    map[previousPosition[0]][previousPosition[1]] = '*';
                    break;
                }
            }

            for (int r = 0; r < map.length; ++r) {
                for (int c = 0; c < map[0].length; ++c) {
                    Character ch = map[r][c];
                    if (ch == 'S' || ch == '*') {
                        continue;
                    }
                    if (path.contains(new Point2D.Double(r, c))) {
                        map[r][c] = 'o';
                        result++;

                    } else {
                        map[r][c] = '.';
                    }
                }
            }

            System.out.println(Arrays.deepToString(map));

            System.out.println("steps:"+ steps);
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Integer[] getNextPosition(Integer[] currentPosition, Integer[] previousPosition) {
        Integer[] diff = new Integer[]{currentPosition[0] - previousPosition[0], currentPosition[1] - previousPosition[1]};
        Character currCh = map[currentPosition[0]][currentPosition[1]];
        if (diff[0] == 1) { // came from [up]
            if (currCh == '|') {
                return new Integer[]{currentPosition[0] + 1, currentPosition[1]};
            }
            if (currCh == 'L') {
                return new Integer[]{currentPosition[0], currentPosition[1] + 1};
            }
            if (currCh == 'J') {
                return new Integer[]{currentPosition[0], currentPosition[1] - 1};
            }
        }
        if (diff[0] == -1) { // came from [down]
            if (currCh == '|') {
                return new Integer[]{currentPosition[0] - 1, currentPosition[1]};
            }
            if (currCh == 'F') {
                return new Integer[]{currentPosition[0], currentPosition[1] + 1};
            }
            if (currCh == '7') {
                return new Integer[]{currentPosition[0], currentPosition[1] - 1};
            }
        }
        if (diff[1] == 1) { // came from [left]
            if (currCh == '-') {
                return new Integer[]{currentPosition[0], currentPosition[1] + 1};
            }
            if (currCh == 'J') {
                return new Integer[]{currentPosition[0] - 1, currentPosition[1]};
            }
            if (currCh == '7') {
                return new Integer[]{currentPosition[0] + 1, currentPosition[1]};
            }
        }
        if (diff[1] == -1) { // came from [right]
            if (currCh == '-') {
                return new Integer[]{currentPosition[0], currentPosition[1] - 1};
            }
            if (currCh == 'L') {
                return new Integer[]{currentPosition[0] - 1, currentPosition[1]};
            }
            if (currCh == 'F') {
                return new Integer[]{currentPosition[0] + 1, currentPosition[1]};
            }
        }

        return null;
    }

    private static Integer[][] getConnectedCells(Integer[] currentPosition) {
        int currRow = currentPosition[0];
        int currCol = currentPosition[1];
        Integer[] left = currCol == 0 ? null : new Integer[]{currRow, currCol - 1};
        Integer[] right = currCol == map[0].length - 1 ? null : new Integer[]{currRow, currCol + 1};
        Integer[] up = currRow == 0 ? null : new Integer[]{currRow - 1, currCol};
        Integer[] down = currRow == map.length - 1 ? null : new Integer[]{currRow + 1, currCol};

        List<Integer[]> result = new ArrayList<>();
        if (left != null) {
            Character ch = map[left[0]][left[1]];
            if (ch == '-' || ch == 'L' || ch == 'F' || ch == 'S') {
                result.add(left);
            }
        }
        if (right != null) {
            Character ch = map[right[0]][right[1]];
            if (ch == '-' || ch == 'J' || ch == '7' || ch == 'S') {
                result.add(right);
            }
        }
        if (up != null) {
            Character ch = map[up[0]][up[1]];
            if (ch == '|' || ch == '7' || ch == 'F' || ch == 'S') {
                result.add(up);
            }
        }
        if (down != null) {
            Character ch = map[down[0]][down[1]];
            if (ch == '|' || ch == 'L' || ch == 'J' || ch == 'S') {
                result.add(down);
            }
        }

        return result.toArray(Integer[][]::new);
    }
}