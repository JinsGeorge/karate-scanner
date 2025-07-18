package com.github.jinsgeorge.karate.scanner.report;

public class ExternalRule {
    public String id;
    public String name;
    public String description;
    public String engineId;
    public String type;      // e.g. CODE_SMELL
    public String severity;  // e.g. MINOR

    public ExternalRule(String id, String name, String description, String engineId, String type, String severity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.engineId = engineId;
        this.type = type;
        this.severity = severity;
    }
}
