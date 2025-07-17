package com.github.jins.karate.scanner.rules;

import com.github.jins.karate.scanner.models.FeatureModel;
import com.github.jins.karate.scanner.issues.FeatureIssue;
import com.github.jins.karate.scanner.QualityRule;
import com.github.jins.karate.scanner.models.ScenarioModel;
import com.github.jins.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;

public class PrintUsageRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {
                if (step.getText().contains("print")) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Avoid using 'print'; use 'karate.log()' instead."
                    ));
                }
            }
        }

        return issues;
    }
}