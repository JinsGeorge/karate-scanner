package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;

import java.util.ArrayList;
import java.util.List;

public class EmptyScenarioRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            if (scenario.getSteps().isEmpty()) {
                issues.add(new FeatureIssue(
                        model.getFilePath(),
                        scenario.getLine(),
                        "Scenario has no executable steps.",
                        getId()
                ));
            }
        }

        return issues;
    }

    @Override public String getId() { return "empty-scenario"; }
    @Override public String getName() { return "Empty Detection"; }
    @Override public String getDescription() { return "Detects empty scenario with no steps"; }
    @Override public String getSeverity() { return "MAJOR"; }
    @Override public String getType() { return "BUG"; }
}
