package com.github.grkozlowski.aoc.aoc2022;

import com.github.grkozlowski.aoc.utils.InputType;
import lombok.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import static com.github.grkozlowski.aoc.aoc2022.YearConstantValues.INPUT_FOLDER_PREFIX;
import static java.util.Map.entry;

public class Day02_1 {

    private static final String DAY_NUMBER = "02";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static final Map<Character, MoveResultor> mr = Map.ofEntries(
            entry('X', new MoveResultor(1L, Map.ofEntries(
                    entry('A', 3L),
                    entry('B', 0L),
                    entry('C', 6L)
            ))),
            entry('Y', new MoveResultor(2L, Map.ofEntries(
                    entry('A', 6L),
                    entry('B', 3L),
                    entry('C', 0L)
            ))),
            entry('Z', new MoveResultor(3L, Map.ofEntries(
                    entry('A', 0L),
                    entry('B', 6L),
                    entry('C', 3L)
            )))
    );
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            long subSum = 0L;
            for (String line : input) {
                Character myMove = line.charAt(2);
                Character opponentMove = line.charAt(0);

                subSum += mr.get(myMove).getMoveResult();
                subSum += mr.get(myMove).getOpponentMoveResult().get(opponentMove);
                result += subSum;
                System.out.println("subSum:" + subSum);
                subSum = 0L;
            }
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value
    private static class MoveResultor {
        long moveResult;
        Map<Character, Long> opponentMoveResult;
    }
}