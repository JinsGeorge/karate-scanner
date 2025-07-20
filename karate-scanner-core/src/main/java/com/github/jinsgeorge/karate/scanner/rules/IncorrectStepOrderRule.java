package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;

public class IncorrectStepOrderRule implements QualityRule {

    private static final List<String> EXPECTED_ORDER = List.of("Given", "When", "Then");

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            int lastSeen = -1;

            for (StepModel step : scenario.getSteps()) {
                String keyword = step.getText().trim().split(" ")[0];

                int index = EXPECTED_ORDER.indexOf(keyword);
                if (index != -1 && index < lastSeen) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Step out of order: '" + keyword + "' appears before expected step type.",
                            "incorrect-step-order"
                    ));
                    break;
                }

                if (index != -1) lastSeen = index;
            }
        }

        return issues;
    }

    @Override public String getId() { return "incorrect-step-order"; }
    @Override public String getName() { return "Detects deviation from Gherkins"; }
    @Override public String getDescription() { return "Detects deviation from Gherkins  Given- When- Then syntax"; }
    @Override public String getSeverity() { return "MAJOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
