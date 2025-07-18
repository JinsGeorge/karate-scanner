package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateStepRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {
                String key = step.getText().trim();
                if (seen.contains(key)) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Duplicate step found: '" + key + "'",
                            getId()
                    ));
                } else {
                    seen.add(key);
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "duplicate-step"; }
    @Override public String getName() { return "Duplicate Step Detection"; }
    @Override public String getDescription() { return "Detects redundant steps within Karate feature files, indicating potential for refactoring."; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
