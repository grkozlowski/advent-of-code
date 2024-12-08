package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day08_2 {

    private static final String DAY_NUMBER = "08";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            result = calculateAntinodes(input);

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateAntinodes(List<String> map) {
        int rows = map.size();
        int cols = map.get(0).length();
        Set<String> antinodeLocations = new HashSet<>();

        // Find positions of all antennas
        for (char frequency = '0'; frequency <= 'z'; frequency++) {
            List<int[]> antennas = new ArrayList<>();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (map.get(r).charAt(c) == frequency) {
                        antennas.add(new int[]{r, c});
                    }
                }
            }

            // Calculate antinodes for the current frequency
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = 0; j < antennas.size(); j++) {
                    if (i == j) continue;

                    int[] a1 = antennas.get(i);
                    int[] a2 = antennas.get(j);

                    addAntinode(a1, a2, antinodeLocations, rows, cols);
                    addAntinode(a2, a1, antinodeLocations, rows, cols);
                }
            }
        }

        return antinodeLocations.size();
    }

    private static void addAntinode(int[] source, int[] target, Set<String> antinodeLocations, int rows, int cols) {
        for (int i = 1; i < 30; ++i) {
            int antinodeR = source[0] + i * (target[0] - source[0]);
            int antinodeC = source[1] + i * (target[1] - source[1]);

            if (antinodeR >= 0 && antinodeR < rows && antinodeC >= 0 && antinodeC < cols) {
                antinodeLocations.add(antinodeR + "," + antinodeC);
            }
        }
    }
}
