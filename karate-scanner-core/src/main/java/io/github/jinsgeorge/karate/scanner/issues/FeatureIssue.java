package io.github.jinsgeorge.karate.scanner.issues;

import io.github.jinsgeorge.karate.scanner.report.ExternalIssue;
import io.github.jinsgeorge.karate.scanner.report.PrimaryLocation;
import io.github.jinsgeorge.karate.scanner.report.TextRange;

public class FeatureIssue {
    public String file;
    public int line;
    public String message;
    public String ruleId;

    public FeatureIssue(String file, int line, String message, String ruleId) {
        this.file = file;
        this.line = line;
        this.message = message;
        this.ruleId = ruleId;
    }

    public ExternalIssue toExternalIssue(String engineId) {
        if (ruleId == null) {
            throw new IllegalStateException("ruleId must not be null for FeatureIssue: " + message);
        }
        return new ExternalIssue(
                engineId,
                ruleId,
                new PrimaryLocation(message, file, new TextRange(line, line))
        );
    }
}