package com.github.jinsgeorge.karate.scanner.models;


public class StepModel {
    private int line;
    private String text;

    public StepModel(int line, String text) {
        this.line = line;
        this.text = text;
    }

    public int getLine() { return line; }
    public String getText() { return text; }
}