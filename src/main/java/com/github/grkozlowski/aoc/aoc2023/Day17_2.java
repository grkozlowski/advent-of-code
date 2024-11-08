package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day17_2 {

    private static final String DAY_NUMBER = "17";
    private static final String testText = """
2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533
                """;
    private static long result = 0L;
    private static int[][] map;
    private static final Set<String> visitedSet = new HashSet<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();
            map = new int[input.size()][input.get(0).length()];
            for (int row = 0; row < input.size(); ++row) {
                String inputRow = input.get(row);
                for (int col = 0; col < inputRow.length(); ++col) {
                    map[row][col] = Integer.parseInt(inputRow.substring(col, col + 1));
                }
            }

            System.out.println(Arrays.deepToString(map));
            
            Queue<Position> pq = new PriorityQueue<>(Comparator.comparingInt(Position::getHeatLoss));
            Position currentPosition = new Position(0, 0, 0, -1, -1, 0, null);
            pq.add(currentPosition);
            while (!pq.isEmpty()) {
                currentPosition = pq.poll();
                if (currentPosition.wasAlreadyVisited()) {
                    continue;
                }

                if (currentPosition.isFinalPosition()) {
                    break;
                }
                currentPosition.markAsVisited();
                List<Position> newPositions = currentPosition.getNextValidPositions();
                pq.addAll(newPositions);
            }

            result = currentPosition.getHeatLoss();
            currentPosition.printPath();
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Position {
        private final int row;
        private final int col;

        private final int heatLoss;
        private final int forbiddenRow;
        private final int forbiddenCol;
        private final int forwardCount;
        private final Position previousPosition;
        private static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public Position(int row, int col, int heatLoss, int forbiddenRow, int forbiddenCol, int forwardCount, Position previousPosition) {
            this.row = row;
            this.col = col;
            this.heatLoss = heatLoss;
            this.forbiddenRow = forbiddenRow;
            this.forbiddenCol = forbiddenCol;
            this.forwardCount = forwardCount;
            this.previousPosition = previousPosition;
        }

        private String getKey() {
            return row + "|" + col + "|" + forbiddenRow + "|" + forbiddenCol + "|" + forwardCount;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    ", heatLoss=" + heatLoss +
                    ", forbiddenRow=" + forbiddenRow +
                    ", forbiddenCol=" + forbiddenCol +
                    ", forwardCount=" + forwardCount +
                    '}';
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getHeatLoss() {
            return heatLoss;
        }

        public int getForbiddenRow() {
            return forbiddenRow;
        }

        public int getForbiddenCol() {
            return forbiddenCol;
        }

        public int getForwardCount() {
            return forwardCount;
        }

        public Position getPreviousPosition() {
            return previousPosition;
        }

        public boolean isFinalPosition() {
            return this.getRow() == map.length - 1 &&
                    this.getCol() == map[0].length - 1;
        }

        public void markAsVisited() {
            visitedSet.add(getKey());
        }

        public boolean wasAlreadyVisited() {
            return visitedSet.contains(getKey());
        }

        public List<Position> getNextValidPositions() {
            List<Position> result = new ArrayList<>();

            for (int[] dir : dirs) {
                int diffRow = dir[0];
                int diffCol = dir[1];
                int newRow = this.getRow() + diffRow;
                int newCol = this.getCol() + diffCol;

                if (newRow == this.getForbiddenRow() && newCol == this.getForbiddenCol()) {
                    continue;
                }

                boolean isForwardMove = (newRow - this.getRow() == this.getRow() - this.getForbiddenRow()) &&
                        (newCol - this.getCol() == this.getCol() - this.getForbiddenCol());
                if (!isForwardMove && this.getForwardCount() < 4 && this.getRow() != 0 && this.getCol() != 0) {
                    continue;
                }

                int newForwardCount = isForwardMove ? this.getForwardCount() + 1 : 4;
                newRow = isForwardMove ? newRow : this.getRow() + 4 * diffRow;
                newCol = isForwardMove ? newCol : this.getCol() + 4 * diffCol;

                if (hasValidPosition(newRow, newCol, newForwardCount)) {
                    int newHeatLoss = this.getHeatLoss() + map[newRow][newCol];
                    if (!isForwardMove) {
                        newHeatLoss += map[this.getRow() + 1 * diffRow][this.getCol() + 1 * diffCol];
                        newHeatLoss += map[this.getRow() + 2 * diffRow][this.getCol() + 2 * diffCol];
                        newHeatLoss += map[this.getRow() + 3 * diffRow][this.getCol() + 3 * diffCol];
                    }
                    Position newPosition = new Position(newRow, newCol, newHeatLoss, newRow - diffRow, newCol - diffCol, newForwardCount, this);
                    result.add(newPosition);
                }
            }


            return result;
        }

        public void printPath() {
            System.out.println("=== printing path start ===");
            Position currentPosition = this;
            while (currentPosition != null) {
                System.out.println(currentPosition);
                currentPosition = currentPosition.getPreviousPosition();
            }

            System.out.println("=== printing path end ===");
        }

        private boolean hasValidPosition(int newRow, int newCol, int newForwardCount) {
            return newRow >= 0 &&
                    newCol >= 0 &&
                    newRow < map.length &&
                    newCol < map[0].length &&
                    newForwardCount <= 10;
        }

    }
}