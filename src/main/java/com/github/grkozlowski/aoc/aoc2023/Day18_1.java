package com.github.grkozlowski.aoc.aoc2023;


import java.awt.geom.GeneralPath;
import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day18_1 {

    private static final String DAY_NUMBER = "18";
    private static final String testText = """
R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)
                """;
    private static long result = 0L;
    private static final Map<String, Integer[]> dirMap = new HashMap<>(){{
        put("U", new Integer[]{-1, 0});
        put("D", new Integer[]{1, 0});
        put("L", new Integer[]{0, -1});
        put("R", new Integer[]{0, 1});
    }};
    private static final Set<String> pathPoints = new HashSet<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            Integer[] currentPoint = new Integer[]{0, 0};
            Integer[] minBoxPoint = new Integer[]{0, 0};
            Integer[] maxBoxPoint = new Integer[]{0, 0};
            GeneralPath path = new GeneralPath();
            path.moveTo(currentPoint[0], currentPoint[1]);
            pathPoints.add(currentPoint[0]+ "|" + currentPoint[1]);
            for (String line : input) {
                String[] directives = line.split(" ");
                Integer[] dir = dirMap.get(directives[0]);
                int multiplier = Integer.parseInt(directives[1]);

                for (int i = 0; i < multiplier; ++i) {
                    currentPoint[0] += dir[0];
                    currentPoint[1] += dir[1];
                    pathPoints.add(currentPoint[0]+ "|" + currentPoint[1]);
                }

                System.out.println("currentPoint:"+Arrays.deepToString(currentPoint));

                path.lineTo(currentPoint[0], currentPoint[1]);
                minBoxPoint[0] = Math.min(minBoxPoint[0], currentPoint[0]);
                minBoxPoint[1] = Math.min(minBoxPoint[1], currentPoint[1]);
                maxBoxPoint[0] = Math.max(maxBoxPoint[0], currentPoint[0]);
                maxBoxPoint[1] = Math.max(maxBoxPoint[1], currentPoint[1]);

                result += multiplier;
            }
            path.closePath();
            System.out.println("minBoxPoint:"+Arrays.deepToString(minBoxPoint));
            System.out.println("maxBoxPoint:"+Arrays.deepToString(maxBoxPoint));
            int pointsCount = 0;
            for (int row = minBoxPoint[0]; row <= maxBoxPoint[0]; ++row) {
                for (int col = minBoxPoint[1]; col <= maxBoxPoint[1]; ++col) {
                    if (path.contains(row, col) && !pathPoints.contains(row + "|" + col)) {
                        System.out.println(++pointsCount + ":" + row + "|" + col);
                        result++;
                    }
                }

            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}