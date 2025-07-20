package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;

import java.util.ArrayList;
import java.util.List;

public class MissingAssertionRule implements QualityRule {

    private static final List<String> ASSERT_KEYWORDS = List.of("match", "status", "assert", "contains", "equals");

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            boolean hasAssertion = scenario.getSteps().stream()
                    .anyMatch(step -> ASSERT_KEYWORDS.stream()
                            .anyMatch(keyword -> step.getText().toLowerCase().contains(keyword)));

            if (!hasAssertion) {
                issues.add(new FeatureIssue(
                        model.getFilePath(),
                        scenario.getLine(),
                        "Scenario has no assertion or validation step (e.g. status, match, assert).",
                        getId()
                ));
            }
        }

        return issues;
    }

    @Override public String getId() { return "missing-assertion"; }
    @Override public String getName() { return "Missing Assertions"; }
    @Override public String getDescription() { return "Detects when scenario contains no assertions"; }
    @Override public String getSeverity() { return "MAJOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
