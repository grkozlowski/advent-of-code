package com.example.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.*;

import static com.example.aoc.utils.Math.lcm;

public class Day20_2 {

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day20.txt"));
            List<String> input = reader.lines().toList();

            Map<String, Module> modules = new HashMap<>();
            Set<String> conjunctionNames = new HashSet<>();
            String resultGenerator = null;

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
                if (Arrays.asList(destinations).contains("rx")) {
                    resultGenerator = sourceName;
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
            System.out.println(modules.get(resultGenerator));

            Queue<Pulse> q = new ArrayDeque<>();
            q.add(new Pulse("button", "broadcaster", false));

            long turn = 0L;
            Map<String, Long> highTurn = new HashMap<>();

            while (true) {
                turn++;
                if (turn % 1_000_000 == 0) {
                    System.out.println("turn:"+turn);
                }
                while (!q.isEmpty()) {
                    Pulse p = q.poll();
                    if (!modules.containsKey(p.destination)) {
                        continue;
                    }

                    if (p.destination.equals(resultGenerator) && p.value) {
                        System.out.println("setting high for ["+p.source+"] at turn ["+turn+"]");
                        highTurn.put(p.source, turn);
                    }

                    Module m = modules.get(p.destination);
                    List<Pulse> newPulses = m.processPulse(p);
                    q.addAll(newPulses);
                }

                if (modules.get(resultGenerator).getConjunctionState().size() == highTurn.size()) {
                    break;
                }

                q.add(new Pulse("button", "broadcaster", false));
            }

            System.out.println(highTurn);
            result = 1L;
            for (Long value : highTurn.values()) {
                result = lcm(result, value);
            }

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