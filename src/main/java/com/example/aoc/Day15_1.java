package com.example.aoc;


import java.io.*;

public class Day15_1 {

    private static final String testText = """
rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day15.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            String input = reader.readLine();
            for (String step : input.split(",")) {
                int subValue = 0;
                for (char c : step.toCharArray()) {
                    subValue += c;
                    subValue *= 17;
                    subValue %= 256;
                }
                System.out.println("subResult["+step+"]:"+subValue);
                result += subValue;
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}