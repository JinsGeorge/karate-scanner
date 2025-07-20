package io.github.jinsgeorge.karate.scanner.models;

import java.util.ArrayList;
import java.util.List;

public class ScenarioModel {
    private int line;
    private String name;
    private List<StepModel> steps = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public void setLine(int line) { this.line = line; }
    public void setName(String name) { this.name = name; }
    public void addStep(StepModel step) { steps.add(step); }
    public void addTag(String tag) { tags.add(tag); }

    public int getLine() { return line; }
    public String getName() { return name; }
    public List<StepModel> getSteps() { return steps; }
    public List<String> getTags() { return tags; }
}