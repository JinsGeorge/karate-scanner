package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;

public class GherkinsStructureRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            boolean hasGiven = false;
            boolean hasWhen = false;
            boolean hasThen = false;

            for (StepModel step : scenario.getSteps()) {
                String keyword = step.getText().trim().split(" ")[0];
                switch (keyword) {
                    case "Given" -> hasGiven = true;
                    case "When" -> hasWhen = true;
                    case "Then" -> hasThen = true;
                }
            }

            if (!(hasGiven && hasWhen && hasThen)) {
                issues.add(new FeatureIssue(
                        model.getFilePath(),
                        scenario.getLine(),
                        "Scenario missing full Given–When–Then structure.",
                        "incomplete-gherkin-structure"
                ));
            }
        }

        return issues;
    }

    @Override public String getId() { return "incomplete-gherkin-structure"; }
    @Override public String getName() { return "No Gherkin Detection"; }
    @Override public String getDescription() { return "Detects which doesn't follow gherkin syntax"; }
    @Override public String getSeverity() { return "MAJOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
