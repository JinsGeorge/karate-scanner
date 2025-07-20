package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;

import java.util.ArrayList;
import java.util.List;

public class MissingTagRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            if (scenario.getTags().isEmpty() && model.getTags().isEmpty()) {
                issues.add(new FeatureIssue(
                        model.getFilePath(),
                        scenario.getLine(),
                        "Scenario or Feature is missing @tag. Add tags to the feature or scenario for filtering and grouping.",
                        getId()
                ));
            }
        }

        return issues;
    }

    @Override public String getId() { return "missing-tag"; }
    @Override public String getName() { return "Missing tag detection"; }
    @Override public String getDescription() { return "Detects when feature or scenario doesn't use any tags"; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
