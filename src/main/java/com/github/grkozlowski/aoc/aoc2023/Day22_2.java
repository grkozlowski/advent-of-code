package com.github.grkozlowski.aoc.aoc2023;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day22_2 {

    private static final String DAY_NUMBER = "22";
    private static final String testText = """
1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9
                """;
    private static long result = 0L;
    private static final List<Brick> bricks = new ArrayList<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            for (String s : input) {
                String[] line = s.replace("~", ",").split(",");
                bricks.add(new Brick(
                        Integer.parseInt(line[0]),
                        Integer.parseInt(line[1]),
                        Integer.parseInt(line[2]),
                        Integer.parseInt(line[3]),
                        Integer.parseInt(line[4]),
                        Integer.parseInt(line[5])
                ));
            }

            bricks.sort(Comparator.comparingInt(Brick::getZ1));
            System.out.println(bricks);

            for (int i = 0; i < bricks.size(); ++i) {
                Brick b1 = bricks.get(i);
                int maxZ = 1;
                for (int j = 0; j < i; ++j) {
                    Brick b2 = bricks.get(j);
                    if (overlap(b1, b2)) {
                        maxZ = Math.max(maxZ, b2.getZ2() + 1);
                    }
                }
                b1.setZ2(maxZ + b1.getZ2() - b1.getZ1());
                b1.setZ1(maxZ);
            }

            bricks.sort(Comparator.comparingInt(Brick::getZ1));
            System.out.println(bricks);

            Map<Integer, Set<Integer>> below = new HashMap<>();
            Map<Integer, Set<Integer>> above = new HashMap<>();
            for (int i = 0; i < bricks.size(); ++i) {
                below.put(i, new HashSet<>());
                above.put(i, new HashSet<>());
            }

            for (int up = 0; up < bricks.size(); ++up) {
                Brick bUp = bricks.get(up);
                for (int down = 0; down < up; ++down) {
                    Brick bDown = bricks.get(down);
                    if (overlap(bUp, bDown) && bUp.getZ1() == bDown.getZ2() + 1) {
                        below.get(up).add(down);
                        above.get(down).add(up);
                    }
                }
            }

            System.out.println(below);
            System.out.println(above);

            for (int brickIndex = 0; brickIndex < bricks.size(); ++brickIndex) {
                Queue<Integer> q = new ArrayDeque<>();
                Set<Integer> falling = new HashSet<>();
                for (Integer brickAboveIndex : above.get(brickIndex)) {
                    if (below.get(brickAboveIndex).size() == 1) {
                        q.add(brickAboveIndex);
                        falling.add(brickAboveIndex);
                    }
                }

                while (!q.isEmpty()) {
                    int fallingBrickIndex = q.poll();
                    for (Integer brickAboveIndex : above.get(fallingBrickIndex)) {
                        if(falling.containsAll(below.get(brickAboveIndex))) {
                            q.add(brickAboveIndex);
                            falling.add(brickAboveIndex);
                        }
                    }
                }

                result += falling.size();
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean overlap(Brick b1, Brick b2) {
        return Math.max(b1.getX1(), b2.getX1()) <= Math.min(b1.getX2(), b2.getX2()) && Math.max(b1.getY1(), b2.getY1()) <= Math.min(b1.getY2(), b2.getY2());
    }

    @Data
    @AllArgsConstructor
    private static class Brick {
        int x1;
        int y1;
        int z1;
        int x2;
        int y2;
        int z2;
    }

}