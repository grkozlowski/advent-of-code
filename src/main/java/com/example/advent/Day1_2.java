package com.example.advent;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Day1_2 {

    public static void main(String[] args) {
        String testText = """
                    two1nine
                    eightwothree
                    abcone2threexyz
                    xtwone3four
                    4nineeightseven2
                    zoneight234
                    7pqrstsixteen
                """;

        Map<String, Character> numbers = new HashMap<>();
        numbers.put("one",'1');
        numbers.put("two",'2');
        numbers.put("three",'3');
        numbers.put("four",'4');
        numbers.put("five",'5');
        numbers.put("six",'6');
        numbers.put("seven",'7');
        numbers.put("eight",'8');
        numbers.put("nine",'9');

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day1.txt"));
            long result = 0;
//            for (String line : testText.split("\n")) {
            for (String line : reader.lines().toList()) {
                line = line.trim();
                System.out.println(line);
                int first = -1;
                int last = -1;
                for (int i = 0; i < line.length(); ++i) {
                    char c = line.charAt(i);
                    if (!Character.isDigit(c)) {
                        for (Map.Entry<String, Character> entry : numbers.entrySet()) {
                            if (line.startsWith(entry.getKey(), i)) {
                                c = entry.getValue();
                                break;
                            }
                        }
                    }
                    if (Character.isDigit(c)) {
                        if (first == -1) {
                            first = Integer.parseInt(String.valueOf(c));
                        }
                        last = Integer.parseInt(String.valueOf(c));
                    }
                }
                first *= 10;
                result += first + last;
//                System.out.println(first + last);
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}