package com.example.aoc;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day08_1 {

    private static final String testText = """
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
                """;
    private static int result = 0;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day08.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<Integer> directions = Arrays.stream(reader.readLine().replace("L", "0").replace("R", "1").split("")).map(Integer::parseInt).collect(Collectors.toList());

            String line = reader.readLine(); // skip empty line
            line = reader.readLine();
            Map<String, String[]> input = new HashMap<>();
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
            }

            String currentStep = "AAA";
            while (!currentStep.equals("ZZZ")) {
                int dir = directions.get((result++) % directions.size());
                currentStep = input.get(currentStep)[dir];
            }

            System.out.println(directions);
            for (Map.Entry<String, String[]> entry : input.entrySet()) {
                System.out.println(entry.getKey() + "=" + Arrays.deepToString(entry.getValue()));
            }
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}