package com.github.grkozlowski.aoc.aoc2023;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day20_1 {

    private static final String DAY_NUMBER = "20";
    private static final String testText = """
broadcaster -> a
%a -> inv, con
&inv -> b
%b -> con
&con -> output
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            Map<String, Module> modules = new HashMap<>();
            Set<String> conjunctionNames = new HashSet<>();

            for (String line : input) {
                String[] sourceDestination = line.split(" -> ");
                String[] destinations = sourceDestination[1].split(", ");
                if (sourceDestination[0].equals("broadcaster")) {
                    modules.put("broadcaster", new Module("broadcaster", 'b', destinations, null,null));
                    continue;
                }
                String sourceName = sourceDestination[0].substring(1);
                char sourceType = sourceDestination[0].charAt(0);
                modules.put(sourceName, new Module(sourceName, sourceType, destinations, sourceType == '%' ? false : null, new HashMap<>()));

                if (sourceType == '&') {
                    conjunctionNames.add(sourceName);
                }
            }

            for (Module m : modules.values()) {
                for (String destination : m.getDestinations()) {
                    if (conjunctionNames.contains(destination)) {
                        modules.get(destination).getConjunctionState().put(m.getName(), false);
                    }
                }
            }


            System.out.println(modules);

            Queue<Pulse> q = new ArrayDeque<>();
            q.add(new Pulse("button", "broadcaster", false));

            long lowCount = 0L;
            long highCount = 0L;

            for (int i = 0; i < 1000; ++i) {
                while (!q.isEmpty()) {
                    Pulse p = q.poll();
                    if (p.value) {
                        highCount++;
                    } else {
                        lowCount++;
                    }
                    if (!modules.containsKey(p.destination)) {
                        continue;
                    }

                    Module m = modules.get(p.destination);
                    List<Pulse> newPulses = m.processPulse(p);
                    q.addAll(newPulses);
                }
                q.add(new Pulse("button", "broadcaster", false));
            }

            result = lowCount * highCount;
            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    private static class Module {
        String name;
        char type;
        String[] destinations;
        Boolean flipFlopState;
        Map<String, Boolean> conjunctionState;

        public List<Pulse> processPulse(Pulse p) {
            List<Pulse> result = new ArrayList<>();
            if (type == 'b') {
                for (String destination : destinations) {
                    result.add(new Pulse(name, destination, false));
                }
            } else if (type == '%') {
                if (!p.value) {
                    flipFlopState = !flipFlopState;
                    for (String destination : destinations) {
                        result.add(new Pulse(name, destination, flipFlopState));
                    }
                }
            } else {
                conjunctionState.put(p.source, p.value);
                if (conjunctionState.values().stream().allMatch(Boolean.TRUE::equals)) {
                    for (String destination : destinations) {
                        result.add(new Pulse(name, destination, false));
                    }
                } else {
                    for (String destination : destinations) {
                        result.add(new Pulse(name, destination, true));
                    }
                }
            }

            return result;
        }
    }

    private record Pulse(String source, String destination, boolean value) {
    }

}