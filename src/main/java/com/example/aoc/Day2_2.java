package com.example.aoc;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;

public class Day2_2 {


    public static void main(String[] args) {
        String testText = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                """;

        try {
            long result = 0;
            BufferedReader reader = new BufferedReader(new FileReader("input/day2.txt"));
            for (String line : reader.lines().toList()) {
//            for (String line : testText.split("\n")) {

                Map<String, Integer> minValues = new java.util.HashMap<>(Map.of(
                        "red", 0,
                        "green", 0,
                        "blue", 0));

                Arrays.stream(line.substring(line.indexOf(':') + 1) // games as stream
                        .split(";")) // each game
                        .map(String::trim)
                        .flatMap(s -> Arrays.stream(s.split(","))) // each pair [value color]
                        .map(String::trim)
                        .map(s -> s.split(" ")) // tuple [value, color]
                        .forEach(valueColor -> minValues.put(valueColor[1], Math.max(minValues.get(valueColor[1]), Integer.parseInt(valueColor[0]))));

                result += minValues.values().stream()
                        .mapToLong(v -> v)
                        .reduce(
                                1,
                                (a, b) -> a * b);
            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}