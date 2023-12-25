package com.example.aoc;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day24_2 {

    private static final String testText = """
19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day24.txt"));
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

            Hailstone h1 = hailstones.get(0);
            Hailstone h2 = hailstones.get(1);

            int range = 500;
            for (int vX = -range; vX <= range; vX++) {
                for (int vY = -range; vY <= range; vY++) {
                    for (int vZ = -range; vZ <= range; vZ++) {

                        if (vX == 0 || vY == 0 || vZ == 0) {
                            continue;
                        }

                        // Find starting point for rock that will intercept first two hailstones (x,y) on this trajectory

                        // simultaneous linear equation (from part 1):
                        // H1:  x = A + a*t   y = B + b*t
                        // H2:  x = C + c*u   y = D + d*u
                        //
                        //  t = [ d ( C - A ) - c ( D - B ) ] / ( a * d - b * c )
                        //
                        // Solve for origin of rock intercepting both hailstones in x,y:
                        //     x = A + a*t - vX*t   y = B + b*t - vY*t
                        //     x = C + c*u - vX*u   y = D + d*u - vY*u

                        long A = h1.x, a = h1.vX - vX;
                        long B = h1.y, b = h1.vY - vY;
                        long C = h2.x, c = h2.vX - vX;
                        long D = h2.y, d = h2.vY - vY;

                        // skip if division by 0
                        if (c == 0 || (a * d) - (b * c) == 0) {
                            continue;
                        }

                        // Rock intercepts H1 at time t
                        long t = (d * (C - A) - c * (D - B)) / ((a * d) - (b * c));

                        // Calculate starting position of rock from intercept point
                        long x = h1.x + h1.vX * t - vX * t;
                        long y = h1.y + h1.vY * t - vY * t;
                        long z = h1.z + h1.vZ * t - vZ * t;

                        // check if this rock throw will hit all hailstones

                        boolean hitall = true;
                        for (Hailstone h : hailstones) {
                            long u;
                            if (h.vX != vX) {
                                u = (x - h.x) / (h.vX - vX);
                            } else if (h.vY != vY) {
                                u = (y - h.y) / (h.vY - vY);
                            } else if (h.vZ != vZ) {
                                u = (z - h.z) / (h.vZ - vZ);
                            } else {
                                throw new RuntimeException();
                            }

                            if ((x + u * vX != h.x + u * h.vX) || (y + u * vY != h.y + u * h.vY) || (z + u * vZ != h.z + u * h.vZ)) {
                                hitall = false;
                                break;
                            }
                        }

                        if (hitall) {
                            System.out.printf("%d %d %d   %d %d %d   %d %n", x, y, z, vX, vY, vZ, x + y + z); // 194723518367339 181910661443432 150675954587450   148 159 249   527310134398221
                            result = x + y + z;
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
    }
}