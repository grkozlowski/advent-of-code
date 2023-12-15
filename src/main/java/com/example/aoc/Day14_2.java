package com.example.aoc;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14_2 {

    private static final String testText = """
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
                """;
    private static long result = 0L;
    private static final Map<Integer, Integer> memo = new HashMap<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day14.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            char[][] map = new char[input.size()][input.get(0).length()];
            for (int row = 0; row < input.size(); ++row) {
                for (int col = 0; col < input.get(0).length(); ++col) {
                    map[row][col] = input.get(row).charAt(col);
                }
            }

            System.out.println("=== input ===");
            System.out.println(Arrays.deepToString(map));

            boolean check = true;
            for (int i = 0; i < 1_000_000_000; ++i) {
                rollStonesNorth(map);
                rollStonesWest(map);
                rollStonesSouth(map);
                rollStonesEast(map);

                if (check) {
                    int hash = Arrays.deepHashCode(map);
                    if (memo.containsKey(hash)) {
                        System.out.println("Found cycle at i:"+i);

                        int diff = i - memo.get(hash);
                        int times = (999_999_999 - i) / diff;
                        i = i + diff * times;
                        check = false;

                        System.out.println("diff:"+diff);
                        System.out.println("times:"+times);
                        System.out.println("jump:"+(diff * times));
                        System.out.println("New i:"+i);
                    } else {
                        memo.put(hash, i);
                    }
                }
            }

            System.out.println("=== rolled stones ===");
            System.out.println(Arrays.deepToString(map));

            result = computeResult(map);

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long computeResult(char[][] map) {
        long result = 0L;
        int rowsCount = map.length;
        for (int col = 0; col < map[0].length; ++col) {
            for (int row = 0; row < rowsCount; ++row) {
                char c = map[row][col];
                if (c == 'O') {
                    result += rowsCount - row;
                }
            }
        }

        return result;
    }

    private static void rollStonesNorth(char[][] map) {
        for (int col = 0; col < map[0].length; ++col) {
            int freeIndex = 0;
            for (int row = freeIndex; row < map.length; ++row) {
                char c = map[row][col];
                if (c == '#') {
                    freeIndex = row + 1;
                } else if (c == 'O') {
                    map[row][col] = '.';
                    map[freeIndex++][col] = 'O';
                }
            }
        }
    }

    private static void rollStonesWest(char[][] map) {
        for (int row = 0; row < map.length; ++row) {
            int freeIndex = 0;
            for (int col = freeIndex; col < map[0].length; ++col) {
                char c = map[row][col];
                if (c == '#') {
                    freeIndex = col + 1;
                } else if (c == 'O') {
                    map[row][col] = '.';
                    map[row][freeIndex++] = 'O';
                }
            }
        }
    }

    private static void rollStonesSouth(char[][] map) {
        for (int col = 0; col < map[0].length; ++col) {
            int freeIndex = map.length - 1;
            for (int row = freeIndex; row >= 0; --row) {
                char c = map[row][col];
                if (c == '#') {
                    freeIndex = row - 1;
                } else if (c == 'O') {
                    map[row][col] = '.';
                    map[freeIndex--][col] = 'O';
                }
            }
        }
    }

    private static void rollStonesEast(char[][] map) {
        for (int row = 0; row < map.length; ++row) {
            int freeIndex = map[0].length - 1;
            for (int col = freeIndex; col >= 0; --col) {
                char c = map[row][col];
                if (c == '#') {
                    freeIndex = col - 1;
                } else if (c == 'O') {
                    map[row][col] = '.';
                    map[row][freeIndex--] = 'O';
                }
            }
        }
    }

}