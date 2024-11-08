package com.github.grkozlowski.aoc.aoc2023;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day24_1 {

    private static final String DAY_NUMBER = "24";
    private static final String testText = """
19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3
                """;
    private static long result = 0L;
    private static final long minBound = 200000000000000L;
    private static final long maxBound = 400000000000000L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            List<Hailstone> hailstones = new ArrayList<>();

            for (String s : input) {
                Long[] line = Arrays.stream(s
                                .replace(" @", ",")
                                .replace(" ", "")
                                .split(","))
                        .map(Long::parseLong)
                        .toArray(Long[]::new);

                hailstones.add(new Hailstone(
                        line[0],
                        line[1],
                        line[2],
                        line[3],
                        line[4],
                        line[5]
                ));
            }

            System.out.println("hailstones:"+hailstones);

            for (int i = 0; i < hailstones.size(); ++i) {
                for (int j = 0; j < i; ++j) {
                    Hailstone h1 = hailstones.get(i);
                    Hailstone h2 = hailstones.get(j);
                    double a1 = h1.getA();
                    double b1 = h1.getB();
                    double c1 = h1.getC();
                    double a2 = h2.getA();
                    double b2 = h2.getB();
                    double c2 = h2.getC();

                    if (a1 * b2 == b1 * a2) { // parallel lines
                        continue;
                    }

                    double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
                    double y = (c2 * a1 - c1 * a2) / (a1 * b2 - a2 * b1);

                    if (x <= maxBound &&
                            minBound <= x &&
                            y <= maxBound &&
                            minBound <= y) {
                        // for both points, intersection point is in the future
                        if ((x - h1.x) * h1.vX >= 0 && (y - h1.y) * h1.vY >= 0 &&
                                (x - h2.x) * h2.vX >= 0 && (y - h2.y) * h2.vY >= 0) {
                            result++;
                        }
                    }
                }
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private record Hailstone(long x, long y, long z, long vX, long vY, long vZ) {
        public double getA() {
            return vY;
        }

        public double getB() {
            return -vX;
        }

        public double getC() {
            return vY * x - vX * y;
        }
    }
}