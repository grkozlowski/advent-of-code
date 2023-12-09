package com.example.aoc;


import java.io.*;
import java.util.Arrays;

public class Day09_1 {

    private static final String testText = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day09.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));

            String line = reader.readLine();
            while (line != null) {
                Integer[] values = Arrays.stream(line.split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
                result += calculateNextValue(values);
                line = reader.readLine();
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculateNextValue(Integer[] values) {
        if (allZeros(values)) {
            return 0;
        }

        Integer[] diffs = new Integer[values.length - 1];
        for (int i = 1; i < values.length; ++i) {
            diffs[i - 1] = values[i] - values[i - 1];
        }

        return values[values.length-1] + calculateNextValue(diffs);
    }

    private static boolean allZeros(Integer[] values) {
        return Arrays.stream(values).allMatch(v -> v == 0);
    }


}