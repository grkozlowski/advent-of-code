package com.example.aoc;


import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Day06_2 {
    /*
    s = v * t
    s = v * (t - v)
    s = t * v - v^2
    0 = v^2 - v * t + s
    v1 = [t - (t^2 - 4 * 1 * s)^(0.5)] / [2]
    v2 = [t + (t^2 - 4 * 1 * s)^(0.5)] / [2]
    ----------------
    race1
    v1 = [71530 - (71530^2 - 4 * 1 * 940200)^(0.5)] / [2] ~ 13.14
    v2 = [71530 + (71530^2 - 4 * 1 * 940200)^(0.5)] / [2] ~ 71516.85
    result = floor(v2) - ceil(v1) + 1 = 71516 - 14 + 1 = 71503
    ----------------
    race2
    v1 = [55826490 - (55826490^2 - 4 * 1 * 246144110121111)^(0.5)] / [2] ~ 4826340,995
    v2 = [55826490 + (55826490^2 - 4 * 1 * 246144110121111)^(0.5)] / [2] ~ 51000149,005
    result = floor(v2) - ceil(v1) + 1 = 51000149 - 4826341 + 1 = 46173809

    984576440484444
    3116596985720100
    2132020545235656
    46173808,01
     */
    private static final String testText = """
Time:      7 15 30
Distance:  9 40 200
                """;
    private static int result = 1;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day06.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<Long> times = Arrays.stream(reader.readLine().replace("Time:", "").replace(" ", "").split(" ")).filter(s -> !s.isBlank()).map(Long::parseLong).toList();
            List<Long> distances = Arrays.stream(reader.readLine().replace("Distance:", "").replace(" ", "").split(" ")).filter(s -> !s.isBlank()).map(Long::parseLong).toList();
            System.out.println("times:"+times);
            System.out.println("distances:"+distances);
            for (int i = 0; i < times.size(); ++i) {
                long t = times.get(i);
                long s = distances.get(i);
                long delta = t * t - 4 * s;
                double v1 = (t - Math.pow(delta, 0.5)) / 2 + 0.00001;
                double v2 = (t + Math.pow(delta, 0.5)) / 2 - 0.00001;

                v1 = Math.max(0.0, v1);
                v2 = Math.min(t, v2);

                System.out.println("delta:"+delta);
                System.out.println("v1:"+v1);
                System.out.println("v2:"+v2);

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