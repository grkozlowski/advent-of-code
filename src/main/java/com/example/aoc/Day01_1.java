package com.example.aoc;


import java.io.BufferedReader;
import java.io.FileReader;

public class Day01_1 {


    public static void main(String[] args) {
        String testText = """
                    1abc2
                    pqr3stu8vwx
                    a1b2c3d4e5f
                    treb7uchet
                """;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day01.txt"));
            long result = 0;
//            for (String line : testText.split("\n")) {
            for (String line : reader.lines().toList()) {
                int first = -1;
                int last = -1;
                for (char c : line.toCharArray()) {
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