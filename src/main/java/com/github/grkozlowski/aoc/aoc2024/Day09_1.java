package com.github.grkozlowski.aoc.aoc2024;

import com.github.grkozlowski.aoc.utils.InputType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.grkozlowski.aoc.aoc2024.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day09_1 {

    private static final String DAY_NUMBER = "09";
    private static final InputType INPUT_TYPE = InputType.LARGE;
    private static long result = 0L;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + INPUT_TYPE.subPath() + "day" + DAY_NUMBER + ".txt"));
            List<String> input = reader.lines().toList();

            result = calculateChecksum(input.get(0));

            System.out.println("result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateChecksum(String diskMap) {
        // Parse the dense disk map into a linear representation of file blocks
        List<Long> blocks = parseDiskMap(diskMap);

        // Compact the disk by moving file blocks to fill gaps
        compactDisk(blocks);

        // Compute the checksum of the final state
        return computeChecksum(blocks);
    }

    private static List<Long> parseDiskMap(String diskMap) {
        int fileId = 0;

        List<Long> list = new ArrayList<>();

        for (int i = 0; i < diskMap.length(); i++) {
            int length = diskMap.charAt(i) - '0'; // Convert char to int
            if (i % 2 == 0) { // Even indices are file lengths
                for (int h = 0; h < length; ++h) {
                    list.add((long) fileId);
                }
                fileId++;
            } else { // Odd indices are free space lengths
                for (int h = 0; h < length; ++h) {
                    list.add((long) -1);
                }
            }
        }
        return list;
    }

    private static void compactDisk(List<Long> blocks) {
        int writePointer = 0; // Tracks the position of the next free space

        for (int readPointer = blocks.size() - 1; readPointer >= 0; readPointer--) {
            while (writePointer < blocks.size() && blocks.get(writePointer) != -1L) writePointer++;

            if (blocks.get(readPointer) != -1) {
                // Move file block to the leftmost free space
                if (writePointer < readPointer) {
                    blocks.set(writePointer, blocks.get(readPointer));
                    blocks.set(readPointer, -1L); // Mark the old position as free space
                } else {
                    break;
                }
            }
        }
    }

    private static long computeChecksum(List<Long> blocks) {
        long checksum = 0;

        System.out.println(blocks);

        for (int position = 0; position < blocks.size(); position++) {
            if (blocks.get(position) >= 0) {
                long fileId = blocks.get(position); // Convert block character to file ID
                checksum += (long) position * fileId;
            }
        }

        return checksum;
    }
}
