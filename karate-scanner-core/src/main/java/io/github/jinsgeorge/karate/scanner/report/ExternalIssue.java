package io.github.jinsgeorge.karate.scanner.report;

public class ExternalIssue {
    public String engineId;
    public String ruleId;
    public PrimaryLocation primaryLocation;

    public ExternalIssue(String engineId, String ruleId, PrimaryLocation primaryLocation) {
        this.engineId = engineId;
        this.ruleId = ruleId;
        this.primaryLocation = primaryLocation;
    }
}
