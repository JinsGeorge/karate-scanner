package com.github.jins.karate.scanner.rules;

import com.github.jins.karate.scanner.models.FeatureModel;
import com.github.jins.karate.scanner.issues.FeatureIssue;
import com.github.jins.karate.scanner.QualityRule;
import com.github.jins.karate.scanner.models.ScenarioModel;
import com.github.jins.karate.scanner.models.StepModel;

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
                            "Duplicate step found: '" + key + "'"
                    ));
                } else {
                    seen.add(key);
                }
            }
        }

        return issues;
    }
}
