package com.github.grkozlowski.aoc.aoc2023;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day05_2 {

    private static final String DAY_NUMBER = "05";
    private static final String testText = """
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
 
                """;
    private static long result = Long.MAX_VALUE;
    private static List<List<Long[]>> maps;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<Long> subValues = Arrays.stream(reader.readLine().replace("seeds: ", "").split(" ")).map(Long::parseLong).toList();
            reader.readLine(); // skip empty line
            maps = new ArrayList<>();
            readMappingLevel(reader);
            readMappingLevel(reader);
            readMappingLevel(reader);
            readMappingLevel(reader);
            readMappingLevel(reader);
            readMappingLevel(reader);
            readMappingLevel(reader);
            Long counter = 0L;
            for (int i = 1; i < subValues.size(); ++i) {
                if (i % 2 == 0) continue;
                Long start = subValues.get(i - 1);
                Long end = subValues.get(i - 1) + subValues.get(i);
                for (Long v = start; v < end; ++v) {
                    if (counter++ % 10_000_000 == 0) System.out.println(counter);
                    Long value = mapValues(v, maps.get(0));
                    value = mapValues(value, maps.get(1));
                    value = mapValues(value, maps.get(2));
                    value = mapValues(value, maps.get(3));
                    value = mapValues(value, maps.get(4));
                    value = mapValues(value, maps.get(5));
                    value = mapValues(value, maps.get(6));
                    result = result < value ? result : value;
                }
            }

            System.out.println("=== results ===");
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void readMappingLevel(BufferedReader reader) {
        try {
            System.out.println(reader.readLine()); // skip header
            List<Long[]> map = readMap(reader);
            map.sort(Comparator.comparing(v -> v[0]));
            maps.add(map);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Long mapValues(Long value, List<Long[]> map) {
        int mapperIndex = findMapper(value, map);
        return mapValue(value, map, mapperIndex);
    }

    private static Long mapValue(Long value, List<Long[]> map, int mapperIndex) {
        if (mapperIndex < 0 || mapperIndex >= map.size() || value < map.get(mapperIndex)[0] || value >= map.get(mapperIndex)[1]) {
            return value;
        } else {
            return map.get(mapperIndex)[2] + (value - map.get(mapperIndex)[0]);
        }
    }

    private static int findMapper(Long value, List<Long[]> map) {
        for (int i = 0; i < map.size(); ++i) {
            Long[] mapper = map.get(i);
            if (value >= mapper[0] && value < mapper[1]) return i;
        }
        return -1;
    }

    private static List<Long[]> readMap(BufferedReader reader) {
        List<Long[]> result = new ArrayList<>();
        try {
            String line = reader.readLine();
            while (!line.isBlank()) {
                result.add(Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new));
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = result.stream().map(entry -> new Long[]{entry[1], entry[1] + entry[2], entry[0]}).collect(Collectors.toList()); // [fromMinInclusive, fromMaxExclusive, toMinInclusive]
        return result;
    }

}