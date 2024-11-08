package com.github.grkozlowski.aoc.aoc2023;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day18_2 {

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
    private static double result = 0L;
    private static final Map<String, Integer[]> dirMap = new HashMap<>(){{
        put("3", new Integer[]{-1, 0});
        put("1", new Integer[]{1, 0});
        put("2", new Integer[]{0, -1});
        put("0", new Integer[]{0, 1});
    }};

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            Long[] currentPoint = new Long[]{0L, 0L};

            List<Long[]> points = new ArrayList<>();
            points.add(new Long[]{currentPoint[0], currentPoint[1]});
            double boundLength = 0;

            for (String line : input) {
                String directives = line.split(" ")[2];
                String dirKey = directives.substring(directives.length() - 2, directives.length() - 1);
                Integer[] dir = dirMap.get(dirKey);
                Long multiplier = Long.parseLong(directives.substring(2, directives.length() - 2), 16);

                System.out.println("dir:"+Arrays.deepToString(dir));
                System.out.println("multiplier:"+multiplier);

                currentPoint[0] += dir[0] * multiplier;
                currentPoint[1] += dir[1] * multiplier;

                points.add(new Long[]{currentPoint[0], currentPoint[1]});

                boundLength += multiplier;
            }

            System.out.println("result:"+result);

            double area = 0;
            for (int i = 0; i < points.size(); ++i) {
                long x = points.get(i)[0];
                long y1 = points.get(i == points.size() - 1 ? 0 : i + 1)[1];
                long y2 = points.get(i == 0 ? points.size() - 1 : i - 1)[1];
                area += x * (y1 - y2) / 2.0; // Shoelace formula
            }
            area = Math.abs(area);

            double interiorPointsCount = area - boundLength / 2.0 + 1; // Pick's theory
            result = interiorPointsCount + boundLength;

            System.out.println("area:"+area);
            System.out.println("boundLength:"+boundLength);
            System.out.println("interiorPointsCount:"+interiorPointsCount);
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}