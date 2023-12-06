package com.example.aoc;


import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Day06_1 {
    /*
    s = v * t
    s = v * (t - v)
    s = t * v - v^2
    0 = v^2 - v * t + s
    v1 = [t - (t^2 - 4 * 1 * s)^(0.5)] / [2]
    v2 = [t + (t^2 - 4 * 1 * s)^(0.5)] / [2]
    ----------------
    race1
    v1 = [7 - (7^2 - 4 * 1 * 9)^(0.5)] / [2] ~ 1.70
    v2 = [7 + (7^2 - 4 * 1 * 9)^(0.5)] / [2] ~ 5.30
    result = floor(v2) - ceil(v1) + 1 = 5 - 2 + 1 = 4
    ----------------
    race2
    v1 = [15 - (15^2 - 4 * 1 * 40)^(0.5)] / [2] ~ 3.47
    v2 = [15 + (15^2 - 4 * 1 * 40)^(0.5)] / [2] ~ 11.53
    result = floor(v2) - ceil(v1) + 1 = 11 - 4 + 1 = 8
    ----------------
    race3
    v1 = [30 - (30^2 - 4 * 1 * 200)^(0.5)] / [2] ~ 10.00
    v2 = [30 + (30^2 - 4 * 1 * 200)^(0.5)] / [2] ~ 20.00
    result = floor(v2) - ceil(v1) + 1 = 11 - 4 + 1 = 8
     */
    private static final String testText = """
Time:      7  15   30
Distance:  9  40  200
                """;
    private static int result = 1;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day06.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<Integer> times = Arrays.stream(reader.readLine().replace("Time:", "").split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
            List<Integer> distances = Arrays.stream(reader.readLine().replace("Distance:", "").split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
            System.out.println("times:"+times);
            System.out.println("distances:"+distances);
            for (int i = 0; i < times.size(); ++i) {
                int t = times.get(i);
                int s = distances.get(i);
                int delta = t * t - 4 * s;
                double v1 = (t - Math.pow(delta, 0.5)) / 2 + 0.001;
                double v2 = (t + Math.pow(delta, 0.5)) / 2 - 0.001;

                v1 = Math.max(0.0, v1);
                v2 = Math.min(t, v2);

                double betterResultsCount = Math.floor(v2) - Math.ceil(v1) + 1;
                System.out.println(betterResultsCount);

                result *= betterResultsCount;
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}