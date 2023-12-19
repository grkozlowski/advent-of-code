package com.example.aoc;

import java.io.*;
import java.util.*;

public class Day19_2 {

    private static final String testText = """
px{a<2006:qkq,m>2090:A,rfg}
pv{a>1716:R,A}
lnx{m>1548:A,A}
rfg{s<537:gd,x>2440:R,A}
qs{s>3448:A,lnx}
qkq{x<1416:A,crn}
crn{x>2662:A,R}
in{s<1351:px,qqz}
qqz{s>2770:qs,m<1801:hdj,R}
gd{a>3333:R,R}
hdj{m>838:A,pv}

{x=787,m=2655,a=1222,s=2876}
{x=1679,m=44,a=2067,s=496}
{x=2036,m=264,a=79,s=2244}
{x=2461,m=1339,a=466,s=291}
{x=2127,m=1623,a=2188,s=1013}
                """;
    private static long result = 0L;

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("input/day19.txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            Map<String, String[]> rules = new HashMap<>();
            for (String line : input) {
                if (line.isBlank()) {
                    break;
                }
                int rulesDefStart = line.indexOf('{');
                String ruleName = line.substring(0, rulesDefStart);
                String[] ruleDefinition = line.substring(rulesDefStart + 1, line.length() - 1).split(",");
                rules.put(ruleName, ruleDefinition);
            }

            rules.put("A", new String[]{"A"});
            rules.put("R", new String[]{"R"});

            Queue<Part> parts = new ArrayDeque<>();
            parts.add(new Part(1, 4000, 1, 4000, 1, 4000, 1, 4000, "in"));

            while (!parts.isEmpty()) {
                int size = parts.size();
                for (int i = 0; i < size; ++i) {
                    Part p = parts.poll();
                    String[] ruleDefinition = rules.get(p.getRule());
                    for (String rule : ruleDefinition) {
                        if (rule.equals("A")) {
                            result += p.getTraitsSum();
                            break;
                        }
                        if (rule.equals("R")) {
                            break;
                        }
                        if (!rule.contains(":")) {
                            parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), rule));
                            break;
                        }

                        String[] checkResult = rule.split(":");
                        int bound = Integer.parseInt(checkResult[0].substring(2));
                        char check = checkResult[0].charAt(1);
                        char checkedParam = checkResult[0].charAt(0);
                        String newRule = checkResult[1];

                        if (checkedParam == 'x') {
                            if (check == '>') {
                                parts.add(new Part(bound + 1, p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), newRule));
                                p = new Part(p.getxMin(), bound, p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), p.getRule());
                            }
                            if (check == '<') {
                                parts.add(new Part(p.getxMin(), bound - 1, p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), newRule));
                                p = new Part(bound, p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), p.getRule());
                            }
                        }
                        if (checkedParam == 'm') {
                            if (check == '>') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), bound + 1, p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), newRule));
                                p = new Part(p.getxMin(), p.getxMax(), p.getmMin(), bound, p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), p.getRule());
                            }
                            if (check == '<') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), bound - 1, p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), newRule));
                                p = new Part(p.getxMin(), p.getxMax(), bound, p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), p.getsMax(), p.getRule());
                            }
                        }
                        if (checkedParam == 'a') {
                            if (check == '>') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), bound + 1, p.getaMax(), p.getsMin(), p.getsMax(), newRule));
                                p = new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), bound, p.getsMin(), p.getsMax(), p.getRule());
                            }
                            if (check == '<') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(),bound - 1, p.getsMin(), p.getsMax(), newRule));
                                p = new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), bound, p.getaMax(), p.getsMin(), p.getsMax(), p.getRule());
                            }
                        }
                        if (checkedParam == 's') {
                            if (check == '>') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), bound + 1, p.getsMax(), newRule));
                                p = new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), bound, p.getRule());
                            }
                            if (check == '<') {
                                parts.add(new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), p.getsMin(), bound - 1, newRule));
                                p = new Part(p.getxMin(), p.getxMax(), p.getmMin(), p.getmMax(), p.getaMin(), p.getaMax(), bound, p.getsMax(), p.getRule());
                            }
                        }
                    }
                }
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Part {
        private final int xMin;
        private final int xMax;
        private final int mMin;
        private final int mMax;
        private final int aMin;
        private final int aMax;
        private final int sMin;
        private final int sMax;
        private final String rule;

        public Part(int xMin, int xMax, int mMin, int mMax, int aMin, int aMax, int sMin, int sMax, String rule) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.mMin = mMin;
            this.mMax = mMax;
            this.aMin = aMin;
            this.aMax = aMax;
            this.sMin = sMin;
            this.sMax = sMax;
            this.rule = rule;
        }

        public int getxMin() {
            return xMin;
        }

        public int getxMax() {
            return xMax;
        }

        public int getmMin() {
            return mMin;
        }

        public int getmMax() {
            return mMax;
        }

        public int getaMin() {
            return aMin;
        }

        public int getaMax() {
            return aMax;
        }

        public int getsMin() {
            return sMin;
        }

        public int getsMax() {
            return sMax;
        }

        public String getRule() {
            return rule;
        }

        public long getTraitsSum() {
            return (long) (xMax - xMin + 1) * (mMax - mMin + 1) * (aMax - aMin + 1) * (sMax - sMin + 1);
        }
    }
}