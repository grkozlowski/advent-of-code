package com.example.aoc;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day05_1 {


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
    private static List<Long> values;
    private static List<Long[]> map;
    private static List<List<Integer>> debug;
    private static List<List<Long[]>> debugMaps;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day05.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            values = Arrays.stream(reader.readLine().replace("seeds: ", "").split(" ")).map(Long::parseLong).collect(Collectors.toList());
            System.out.println(values);
            reader.readLine(); // skip empty line

            debug = new ArrayList<>();
            for (Long value : values) {
                debug.add(new ArrayList<>());
            }
            debugMaps = new ArrayList<>();

            processMappingLevel(reader);
            processMappingLevel(reader);
            processMappingLevel(reader);
            processMappingLevel(reader);
            processMappingLevel(reader);
            processMappingLevel(reader);
            processMappingLevel(reader);

            int resultIndex = -1;
            for (int i = 0; i < values.size(); ++i) {
                Long value = values.get(i);
                result = result < value ? result : value;
                if (result == value) {
                    resultIndex = i;
                }
            }
            System.out.println("=== results ===");
            System.out.println("result:"+result);
            System.out.println("resultIndex:"+resultIndex);
            System.out.println("debug:"+debug);
            int mapIndex = 0;
            for (int i : debug.get(resultIndex)) {

                if (i >= 0) System.out.println(Arrays.deepToString(debugMaps.get(mapIndex).get(i)));
                else System.out.println("sameValue");
                mapIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processMappingLevel(BufferedReader reader) {
        try {
            System.out.println(reader.readLine()); // skip header
            map = readMap(reader);
            map.sort(Comparator.comparing(v -> v[0]));
            for (Long[] entry : map) {
                System.out.println(Arrays.deepToString(entry));
            }
            mapValues(values, map);
            System.out.println(values);
        } catch(Exception e) {
            e.printStackTrace();
        }

        debugMaps.add(map);
    }

    private static void mapValues(List<Long> values, List<Long[]> map) {
        for (int i = 0; i < values.size(); ++i) {
            Long value = values.get(i);
            int mapperIndex = findMapper(value, map);
            Long newValue = mapValue(i, map, mapperIndex);
            values.set(i, newValue);
        }

    }
    // 76872543 --> too low

    private static Long mapValue(int valueIndex, List<Long[]> map, int mapperIndex) {
        if (mapperIndex < 0 || mapperIndex >= map.size() || values.get(valueIndex) < map.get(mapperIndex)[0] || values.get(valueIndex) >= map.get(mapperIndex)[1]) {
            debug.get(valueIndex).add(-1);
            return values.get(valueIndex);
        } else {
            debug.get(valueIndex).add(mapperIndex);
            return map.get(mapperIndex)[2] + (values.get(valueIndex) - map.get(mapperIndex)[0]);
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
        // [fromMinInclusive, fromMaxExclusive, toMinInclusive]
        result = result.stream().map(entry -> new Long[]{entry[1], entry[1] + entry[2], entry[0]}).collect(Collectors.toList());
        return result;
    }

}