package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day11_1 {

    private static final String DAY_NUMBER = "11";
    private static final String testText = """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
                """;
    private static long result = 0L;
    private static Integer[][] map;
    private static List<Integer[]> galaxies;
    private static final Integer expansion = 2;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            map = new Integer[input.size()][input.get(0).length()];
            galaxies = new ArrayList<>();

            for (int r = 0; r < input.size(); ++r) {
                String line = input.get(r);
                for (int c = 0; c < line.length(); ++c) {
                    char ch = line.charAt(c);
                    if (ch == '#') {
                        galaxies.add(new Integer[]{r, c});
                    }
                    map[r][c] = ch == '#' ? 0 : 1;
                }
            }
            System.out.println(Arrays.deepToString(map));
            for (Integer[] g : galaxies) System.out.println(Arrays.deepToString(g));

            for (int r = 0; r < map.length; ++r) {
                int sum = 0;
                for (int c = 0; c < map[0].length; ++c) {
                    sum += map[r][c];
                }
                if (sum == map[0].length) {
                    for (int c = 0; c < map[0].length; ++c) {
                        map[r][c] = expansion;
                    }
                }
            }

            for (int c = 0; c < map[0].length; ++c) {
                int sum = 0;
                for (int r = 0; r < map.length; ++r) {
                    sum += map[r][c].equals(expansion) ? 1 : map[r][c];
                }
                if (sum == map.length) {
                    for (int r = 0; r < map.length; ++r) {
                        map[r][c] = expansion;
                    }
                }
            }
            System.out.println(Arrays.deepToString(map));

            for (int g1 = 0; g1 < galaxies.size(); ++g1) {
                for (int g2 = 0; g2 < g1; ++g2) {
                    Integer[] g1Coords = galaxies.get(g1);
                    Integer[] g2Coords = galaxies.get(g2);
                    int diffR = g2Coords[0] - g1Coords[0];
                    int diffC = g2Coords[1] - g1Coords[1];
                    long sum = 0L;

                    int startR = diffR > 0 ? g1Coords[0] + 1 : g2Coords[0] + 1;
                    int endR = diffR > 0 ? g2Coords[0] : g1Coords[0];
                    for (int r = startR; r <= endR; ++r) {
                        sum += map[r][g1Coords[1]] == 0 ? 1 : map[r][g1Coords[1]];
                    }

                    int startC = diffC > 0 ? g1Coords[1] + 1 : g2Coords[1] + 1;
                    int endC = diffC > 0 ? g2Coords[1] : g1Coords[1];
                    for (int c = startC; c <= endC; ++c) {
                        sum += map[g1Coords[0]][c] == 0 ? 1 : map[g1Coords[0]][c];
                    }

                    result += sum;
                }
            }


            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}