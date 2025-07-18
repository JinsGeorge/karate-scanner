package com.github.jinsgeorge.karate.scanner.models;

import java.util.ArrayList;
import java.util.List;

public class ScenarioModel {
    private int line;
    private String name;
    private List<StepModel> steps = new ArrayList<>();

    public void setLine(int line) { this.line = line; }
    public void setName(String name) { this.name = name; }
    public void addStep(StepModel step) { steps.add(step); }

    public int getLine() { return line; }
    public String getName() { return name; }
    public List<StepModel> getSteps() { return steps; }
}