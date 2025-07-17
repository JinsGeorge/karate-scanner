package com.github.jins.karate.scanner.issues;

public class FeatureIssue {
    public String file;
    public int line;
    public String message;
    public String ruleId;

    public FeatureIssue(String file, int line, String message) {
        this.file = file;
        this.line = line;
        this.message = message;
    }
}