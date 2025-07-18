package com.github.jinsgeorge.karate.scanner.report;

import java.util.List;

public class ExternalIssueReport {
    public List<ExternalRule> rules;
    public List<ExternalIssue> issues;

    public ExternalIssueReport(List<ExternalRule> rules, List<ExternalIssue> issues) {
        this.rules = rules;
        this.issues = issues;
    }
}

