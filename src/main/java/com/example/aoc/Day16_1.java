package com.example.aoc;


import java.io.*;
import java.util.*;

public class Day16_1 {

    private static final String testText = """
.|...\\....
|.-.\\.....
.....|-...
........|.
..........
.........\\
..../.\\\\..
.-.-/..|..
.|....-|.\\
..//.|....
                """;
    private static long result = 0L;
    private static char[][] map;
    private static final Map<String, Set<String>> visitedMap = new HashMap<>();
    private static final Map<String, Set<String>> memoMap = new HashMap<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day16.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            map = new char[input.size()][input.get(0).length()];
            for (int row = 0; row < input.size(); ++row) {
                String inputRow = input.get(row);
                for (int col = 0; col < inputRow.length(); ++col) {
                    map[row][col] = inputRow.charAt(col);
                }
            }

            System.out.println(Arrays.deepToString(map));

            Queue<Position> queue = new ArrayDeque<>();
            queue.add(new Position(
                    0,
                    0,
                    1,
                    0));

            while (!queue.isEmpty()) {
                int queueSize = queue.size();
                for (int i = 0; i < queueSize; ++i) {
                    Position p = queue.poll();
                    System.out.println(p);
                    if (p.hasInvalidPosition() || p.wasAlreadyVisited()) {
                        continue;
                    }
                    p.markAsVisited();
                    char c = map[p.getRow()][p.getCol()];
                    queue.addAll(p.getNextPositions(c));
                }
            }

            for (Map.Entry<String, Set<String>> entry : visitedMap.entrySet()) {
                result = Math.max(result, entry.getValue().size());
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Position {
        private final int row;
        private final int col;
        private final int dirX;
        private final int dirY;
        private final String startKey;

        public Position(int row, int col, int dirX, int dirY) {
            this.row = row;
            this.col = col;
            this.dirX = dirX;
            this.dirY = dirY;
            this.startKey = row + "|" + col + "|" + dirX + "|" + dirY;
        }

        private Position(int row, int col, int dirX, int dirY, String startKey) {
            this.row = row;
            this.col = col;
            this.dirX = dirX;
            this.dirY = dirY;
            this.startKey = startKey;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getDirX() {
            return dirX;
        }

        public int getDirY() {
            return dirY;
        }

        private String getStartKey() {
            return startKey;
        }

        public void markAsVisited() {
            if (!visitedMap.containsKey(startKey)) {
                visitedMap.put(startKey, new HashSet<>());
            }

            visitedMap.get(startKey).add(this.getVisitedValue());

            if (!memoMap.containsKey(startKey)) {
                memoMap.put(startKey, new HashSet<>());
            }

            memoMap.get(startKey).add(this.getMemoValue());
        }

        public boolean wasAlreadyVisited() {
            return memoMap.getOrDefault(startKey, new HashSet<>()).contains(this.getMemoValue());
        }

        public List<Position> getNextPositions(char c) {
            List<Position> result = new ArrayList<>();
            if (c == '.') {
                result.add(new Position(
                        this.getRow() + this.getDirY(),
                        this.getCol() + this.getDirX(),
                        this.getDirX(),
                        this.getDirY(),
                        this.getStartKey()
                ));
            } else if (c == '|') {
                if (this.getDirX() != 0) {
                    result.add(new Position(
                            this.getRow() + 1,
                            this.getCol(),
                            0,
                            1,
                            this.getStartKey()
                    ));
                    result.add(new Position(
                            this.getRow() - 1,
                            this.getCol(),
                            0,
                            -1,
                            this.getStartKey()
                    ));
                } else {
                    result.add(new Position(
                            this.getRow() + this.getDirY(),
                            this.getCol() + this.getDirX(),
                            this.getDirX(),
                            this.getDirY(),
                            this.getStartKey()
                    ));
                }
            } else if (c == '-') {
                if (this.getDirY() != 0) {
                    result.add(new Position(
                            this.getRow(),
                            this.getCol() + 1,
                            1,
                            0,
                            this.getStartKey()
                    ));
                    result.add(new Position(
                            this.getRow(),
                            this.getCol() - 1,
                            -1,
                            0,
                            this.getStartKey()
                    ));
                } else {
                    result.add(new Position(
                            this.getRow() + this.getDirY(),
                            this.getCol() + this.getDirX(),
                            this.getDirX(),
                            this.getDirY(),
                            this.getStartKey()
                    ));
                }
            } else if (c == '\\') {
                result.add(new Position(
                        this.getRow() + this.getDirX(),
                        this.getCol() + this.getDirY(),
                        this.getDirY(),
                        this.getDirX(),
                        this.getStartKey()
                ));
            } else if (c == '/') {
                result.add(new Position(
                        this.getRow() - this.getDirX(),
                        this.getCol() - this.getDirY(),
                        -this.getDirY(),
                        -this.getDirX(),
                        this.getStartKey()
                ));
            }

            return result;
        }

        private String getVisitedValue() {
            return this.getRow() + "|" + this.getCol();
        }

        private String getMemoValue() {
            return this.getRow() + "|" + this.getCol() + "|" + this.getDirX() + "|" + this.getDirY();
        }

        public boolean hasInvalidPosition() {
            return this.getRow() < 0 ||
                    this.getCol() < 0 ||
                    this.getRow() >= map.length ||
                    this.getCol() >= map[0].length;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    ", dirX=" + dirX +
                    ", dirY=" + dirY +
                    ", startKey='" + startKey + '\'' +
                    '}';
        }
    }
}