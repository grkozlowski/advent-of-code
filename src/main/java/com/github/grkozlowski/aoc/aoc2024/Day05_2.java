package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day05_2 {

    private static final String DAY_NUMBER = "05";
    private static final InputType INPUT_TYPE = InputType.LARGE;

    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            int i = 0;

            // Ordering parsing
            List<String> orderingRules = new ArrayList<>();
            while (!input.get(i).isEmpty()) {
                orderingRules.add(input.get(i));
                i++;
            }
            // Updates parsing
            List<List<Long>> updates = new ArrayList<>();
            for (i = i + 1; i < input.size(); i++) {
                String[] pages = input.get(i).split(",");
                List<Long> update = new ArrayList<>();
                for (String page : pages) {
                    update.add(Long.parseLong(page));
                }
                updates.add(update);
            }

            result = calculateMiddlePageSum(orderingRules, updates);

            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long calculateMiddlePageSum(List<String> orderingRules, List<List<Long>> updates) {
        // Map of rules
        Map<Long, Set<Long>> precedenceMap = new HashMap<>();
        for (String rule : orderingRules) {
            String[] parts = rule.split("\\|");
            Long before = Long.parseLong(parts[0]);
            Long after = Long.parseLong(parts[1]);

            precedenceMap.putIfAbsent(before, new HashSet<>());
            precedenceMap.get(before).add(after);
        }

        long sum = 0L;

        for (List<Long> update : updates) {
            if (!isCorrectOrder(update, precedenceMap)) {
                // Correct the order of the update
                List<Long> correctedOrder = correctOrder(update, precedenceMap);
                // Middle element of the corrected update
                sum += correctedOrder.get(correctedOrder.size() / 2);
            }
        }

        return sum;
    }

    private static boolean isCorrectOrder(List<Long> update, Map<Long, Set<Long>> precedenceMap) {
        Map<Long, Integer> positionMap = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            positionMap.put(update.get(i), i);
        }

        for (Map.Entry<Long, Set<Long>> entry : precedenceMap.entrySet()) {
            Long before = entry.getKey();
            for (Long after : entry.getValue()) {
                // If both pages are in the update, check their order
                if (positionMap.containsKey(before) && positionMap.containsKey(after)) {
                    if (positionMap.get(before) > positionMap.get(after)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static List<Long> correctOrder(List<Long> update, Map<Long, Set<Long>> precedenceMap) {
        // Topological sort to arrange pages in the correct order
        Set<Long> inUpdate = new HashSet<>(update);
        Map<Long, Integer> inDegree = new HashMap<>();
        Map<Long, List<Long>> graph = new HashMap<>();

        // Graph and in-degree map for pages in the update
        for (Long page : inUpdate) {
            graph.putIfAbsent(page, new ArrayList<>());
            inDegree.putIfAbsent(page, 0);
        }

        for (Map.Entry<Long, Set<Long>> entry : precedenceMap.entrySet()) {
            Long before = entry.getKey();
            for (Long after : entry.getValue()) {
                if (inUpdate.contains(before) && inUpdate.contains(after)) {
                    graph.get(before).add(after);
                    inDegree.put(after, inDegree.get(after) + 1);
                }
            }
        }

        // Topological sort using Kahn's Algorithm
        Queue<Long> queue = new LinkedList<>();
        for (Long page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Long> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            Long current = queue.poll();
            sortedOrder.add(current);

            for (Long neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedOrder;
    }
}