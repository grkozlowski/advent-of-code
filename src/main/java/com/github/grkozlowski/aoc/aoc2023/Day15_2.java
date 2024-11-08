package com.github.grkozlowski.aoc.aoc2023;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day15_2 {

    private static final String DAY_NUMBER = "15";
    private static final String testText = """
rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
                """;
    private static long result = 0L;
    private static final Map<Integer, List<Pair>> map = new HashMap<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            String input = reader.readLine();
            for (String step : input.split(",")) {
                if (step.charAt(step.length() - 1) == '-') {
                    String key = step.substring(0, step.length() - 1);
                    int hash = computeHash(key);
                    map.getOrDefault(hash, new ArrayList<>())
                            .removeIf(p -> p.getKey().equals(key));
                } else {
                    String[] keyValue = step.split("=");
                    int hash = computeHash(keyValue[0]);
                    if (!map.containsKey(hash)) {
                        map.put(hash, new ArrayList<>());
                    }
                    List<Pair> box = map.get(hash);
                    if (box.stream().anyMatch(p -> p.getKey().equals(keyValue[0]))) {
                        box.stream().filter(p -> p.getKey().equals(keyValue[0])).findFirst().get().setValue(Integer.parseInt(keyValue[1]));
                    } else {
                        box.add(new Pair(keyValue[0], Integer.parseInt(keyValue[1])));
                    }
                }
            }

            result = computeResult(map);

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int computeHash(String label) {
        int hash = 0;
        for (char c : label.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
//        System.out.println("hash["+label+"]:"+hash);

        return hash;
    }

    private static long computeResult(Map<Integer, List<Pair>> map) {
        long result = 0;
        for (Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
//            System.out.println(entry.getKey()+":"+entry.getValue());
            long boxNumber = entry.getKey();
            for (int i = 0; i < entry.getValue().size(); ++i) {
                Pair p = entry.getValue().get(i);
                long subResult = (boxNumber + 1) * p.getValue() * (i + 1);
//                System.out.println(p.getKey() + ":" + (boxNumber + 1) + "(box "+boxNumber+")" + " * " + (i + 1) + " (slot) * " + p.getValue() + " (focal length) = " + subResult);
                result += subResult;
            }
        }

        return result;
    }

    private static class Pair {
        private final String key;
        private int value;

        public Pair(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String toString() {
            return "[" + key + "|" + value + "]";
        }
    }
}