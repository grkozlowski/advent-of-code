package com.github.grkozlowski.aoc.aoc2023;

import java.io.*;
import java.util.*;

import static com.github.grkozlowski.aoc.aoc2023.YearConstantValues.INPUT_FOLDER_PREFIX;

public class Day19_1 {

    private static final String DAY_NUMBER = "19";
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
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FOLDER_PREFIX + "day" + DAY_NUMBER + ".txt"));
//            BufferedReader reader = new BufferedReader(new StringReader(testText));
            List<String> input = reader.lines().toList();

            List<Part> parts = new ArrayList<>();
            Map<String, String[]> rules = new HashMap<>();

            boolean rulesReadingFinished = false;
            for (String line : input) {
                if (line.isBlank()) {
                    rulesReadingFinished = true;
                    continue;
                }
                if (!rulesReadingFinished) {
                    int rulesDefStart = line.indexOf('{');
                    String ruleName = line.substring(0, rulesDefStart);
                    String[] ruleDefinition = line.substring(rulesDefStart + 1, line.length() - 1).split(",");
                    rules.put(ruleName, ruleDefinition);
                }
                if (rulesReadingFinished) {
                    String[] traitValue = line.substring(1, line.length() - 1).split(",");
                    int xValue = Integer.parseInt(traitValue[0].split("=")[1]);
                    int mValue = Integer.parseInt(traitValue[1].split("=")[1]);
                    int aValue = Integer.parseInt(traitValue[2].split("=")[1]);
                    int sValue = Integer.parseInt(traitValue[3].split("=")[1]);
                    parts.add(new Part(xValue, mValue, aValue, sValue));
                }
            }

            for (Part p : parts) {
                String currentRule = "in";
                while (!currentRule.equals("A") && !currentRule.equals("R")) {
                    String[] ruleDefinition = rules.get(currentRule);
                    for (String rule : ruleDefinition) {
                        if (rule.equals("A") || rule.equals("R") || !rule.contains(":")) {
                            currentRule = rule;
                            break;
                        }

                        String[] checkResult = rule.split(":");
                        int bound = Integer.parseInt(checkResult[0].substring(2));
                        char check = checkResult[0].charAt(1);

                        int checkedValue = -1;
                        if (checkResult[0].charAt(0) == 'x') {
                            checkedValue = p.getX();
                        }
                        if (checkResult[0].charAt(0) == 'm') {
                            checkedValue = p.getM();
                        }
                        if (checkResult[0].charAt(0) == 'a') {
                            checkedValue = p.getA();
                        }
                        if (checkResult[0].charAt(0) == 's') {
                            checkedValue = p.getS();
                        }

                        if (check == '>' && checkedValue > bound) {
                            currentRule = checkResult[1];
                            break;
                        }
                        if (check == '<' && checkedValue < bound) {
                            currentRule = checkResult[1];
                            break;
                        }
                    }
                }
                p.setAccepted(currentRule.equals("A"));
                if (Boolean.TRUE.equals(p.getAccepted())) {
                    result += p.getTraitsSum();
                }
            }

            System.out.println("result:"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Part {
        private final int x;
        private final int m;
        private final int a;
        private final int s;
        private Boolean accepted;

        public Part(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
            accepted = null;
        }

        public int getX() {
            return x;
        }

        public int getM() {
            return m;
        }

        public int getA() {
            return a;
        }

        public int getS() {
            return s;
        }

        public Boolean getAccepted() {
            return accepted;
        }

        public void setAccepted(Boolean accepted) {
            this.accepted = accepted;
        }

        public int getTraitsSum() {
            return x + m + a + s;
        }
    }
}