package com.github.grkozlowski.aoc.aoc2022;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static com.github.grkozlowski.aoc.aoc2022.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day01_2 {

    private static final String DAY_NUMBER = "01";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            Queue<Long> pq = new PriorityQueue<>(Long::compareTo);
            long subSum = 0L;
            for (String line : input) {
                if (line.isEmpty()) {
                    pq.add(subSum);
                    subSum = 0L;
                    if (pq.size() == 4) {
                        pq.poll();
                    }
                    continue;
                }
                subSum += Long.parseLong(line);
            }
            while (!pq.isEmpty()) {
                result += pq.poll();
            }
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}