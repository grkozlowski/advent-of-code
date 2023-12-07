package com.example.aoc;


import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day07_2 {

    private static final String testText = """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
                """;
    private static long result = 0;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day07.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<Pair> input = reader
                    .lines()
                    .map(line -> line.split(" "))
                    .map(p -> new Pair(p[0], p[1]))
                    .sorted(Comparator.comparing(Pair::getMappedKey))
                    .collect(Collectors.toList());

            for (int i = 0; i < input.size(); ++i) {
                result += input.get(i).getValue() * (i + 1);
            }

            System.out.println(input);
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Pair {
        private final String key;
        private final Long value;

        public Pair(String k, String v) {
            key = k;
            value = Long.parseLong(v);
        }

        public String getKey() {
            return key;
        }

        public Long getValue() {
            return value;
        }

        public String getMappedKey() {
            return replaceSymbols(prefixType(key));
        }

        private String replaceSymbols(String key) {
            return key
                    .replace("A", "Z")
                    .replace("K", "P")
                    .replace("Q", "N")
                    .replace("J", "0")
                    .replace("T", "L");
        }

        private String prefixType(String key) {
            return getType(key).concat(key);
        }

        private String getType(String key) {
            Map<String, Integer> map = new HashMap<>();
            int jokersCount = 0;
            for (String c : key.split("")) {
                if (c.equals("J")) {
                    jokersCount++;
                } else {
                    map.put(c, map.getOrDefault(c, 0) + 1);
                }
            }

            int distinctKeysCount = map.keySet().size();

            if (distinctKeysCount <= 1) {
                return "7"; // Five of a kind
            } else if (distinctKeysCount == 2) {
                String firstKey = map.keySet().toArray(String[]::new)[0];
                Integer firstValue = map.get(firstKey);
                if (firstValue + jokersCount == 4 || firstValue == 1) {
                    return "6"; // Four of a kind
                } else {
                    return "5"; // Full house == 3 + 2
                }
            } else if (distinctKeysCount == 3) {
                // cases
                // 3 1 1 J0 ==> 3 of kind
                // 2 2 1 J0 ==> 2 pairs
                // 2 1 1 J1 ==> 3 of kind
                // 1 1 1 J2 ==> 3 of kind
                String[] keys = map.keySet().toArray(String[]::new);
                String firstKey = keys[0];
                String secondKey = keys[1];
                Integer firstValue = map.get(firstKey);
                Integer secondValue = map.get(secondKey);
                if (jokersCount == 0 && (firstValue == 2 || secondValue == 2)) {
                    return "3"; // Two pair
                } else {
                    return "4"; // Three of a kind
                }
            } else if (distinctKeysCount == 4) {
                return "2"; // One pair ==> if there is a joker, than it will create a pair, if not, we already have a pair
            } else {
                return "1"; // High card
            }
        }

        public String toString() {
            return "[" + getMappedKey() + ", " + getKey() + ", " + getValue() + "]";
        }
    }
}