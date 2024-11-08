package com.github.grkozlowski.aoc.aoc2023;

import java.io.*;
import java.util.List;
import java.util.Set;

import com.github.grkozlowski.aoc.utils.InputType;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day25 {

    private static final String DAY_NUMBER = "25";
    private static final InputType inputType = InputType.FILE;
    private static final String testText = """
jqt: rhn xhk nvd
rsh: frs pzl lsr
xhk: hfx
cmg: qnr nvd lhk bvb
rhn: xhk bvb hfx
bvb: xhk hfx
pzl: lsr hfx nvd
qnr: nvd
ntq: jqt hfx bvb xhk
nvd: lhk
lsr: lhk
rzs: qnr cmg lsr rsh
frs: qnr lhk lsr
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader;
            if (InputType.FILE.equals(inputType)) {
                reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
            } else {
                reader = new BufferedReader(new StringReader(testText));
            }
            List<String> input = reader.lines().toList();

            DefaultUndirectedGraph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

            for (String line : input) {
                String[] nodes = line.replace(":", "").split(" ");
                graph.addVertex(nodes[0]);
                for (int i = 1; i < nodes.length; ++i) {
                    graph.addVertex(nodes[i]);
                    graph.addEdge(nodes[0], nodes[i]);
                }
            }

            Set<String> minCut = new StoerWagnerMinimumCut<>(graph).minCut();
            graph.removeAllVertices(minCut);

            result = (long) graph.vertexSet().size() * minCut.size();
            System.out.println("result:" + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}