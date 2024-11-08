package com.github.grkozlowski.aoc.utils;

public enum InputType {
    SMALL("small/"),
    LARGE("large/"),
    ;

    private final String subPath;

    InputType(String subPath) {
        this.subPath = subPath;
    }

    public String subPath() {
        return subPath;
    }
}
