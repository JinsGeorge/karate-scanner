package io.github.jinsgeorge.karate.scanner.rules;

import io.github.jinsgeorge.karate.scanner.models.FeatureModel;
import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import io.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import io.github.jinsgeorge.karate.scanner.models.StepModel;

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
                            "Avoid using 'print'; use 'karate.log()' instead.",
                            getId()
                    ));
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "print-usage"; }
    @Override public String getName() { return "Discouraged use of print()"; }
    @Override public String getDescription() { return "Use karate.log() instead of print() to ensure consistent logging."; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}