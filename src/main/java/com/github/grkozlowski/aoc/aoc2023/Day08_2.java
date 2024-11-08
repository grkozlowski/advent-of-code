package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;
import static com.github.grkozlowski.aoc.utils.Math.lcm;

public class Day08_2 {

    private static final String DAY_NUMBER = "08";
    private static final String testText = """
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)
                """;
    private static long result = 0;
    private static List<Integer> directions;
    private static Map<String, String[]> input;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            directions = Arrays.stream(reader.readLine().replace("L", "0").replace("R", "1").split("")).map(Integer::parseInt).collect(Collectors.toList());

            String line = reader.readLine(); // skip empty line
            line = reader.readLine();
            input = new HashMap<>();
            List<String> currentPlaces = new ArrayList<>();
            while (line != null) {
                String key = line.substring(0, 3);
                String leftValue = line.substring(7, 10);
                String rightValue = line.substring(12, 15);

                input.put(
                        key,
                        new String[]{
                                leftValue,
                                rightValue
                        }
                );
                line = reader.readLine();
                if (key.substring(2).equals("A")) {
                    currentPlaces.add(key);
                }
            }

            List<Long> stepsRequired = new ArrayList<>();
            for (String subPlace : currentPlaces) {
                stepsRequired.add(stepsForPlace(subPlace));
            }

            result = lcm(stepsRequired.toArray(Long[]::new));

            System.out.println(directions);
            for (Map.Entry<String, String[]> entry : input.entrySet()) {
                System.out.println(entry.getKey() + "=" + Arrays.deepToString(entry.getValue()));
            }
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Long stepsForPlace(String currentStep) {
        long subResult = 0;
        while (!currentStep.substring(2).equals("Z")) {
            int dir = directions.get((int) (subResult++) % directions.size());
            currentStep = input.get(currentStep)[dir];
        }

        return subResult;
    }
}