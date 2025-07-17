package com.github.jins.karate.scanner.models;

import java.util.ArrayList;
import java.util.List;

public class FeatureModel {

    private String filePath;
    private String title;
    private List<String> tags = new ArrayList<>();
    private List<ScenarioModel> scenarios = new ArrayList<>();

    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setTitle(String title) { this.title = title; }
    public void addTag(String tag) { tags.add(tag); }
    public void addScenario(ScenarioModel scenario) { scenarios.add(scenario); }

    public String getFilePath() { return filePath; }
    public String getTitle() { return title; }
    public List<String> getTags() { return tags; }
    public List<ScenarioModel> getScenarios() { return scenarios; }
}
