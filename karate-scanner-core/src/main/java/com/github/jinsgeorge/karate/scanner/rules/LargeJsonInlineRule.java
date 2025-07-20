package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;

public class LargeJsonInlineRule implements QualityRule {

    private static final int MULTILINE_JSON_THRESHOLD = 10; // lines
    private static final int SINGLE_LINE_JSON_LENGTH = 300; // characters

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {

                String text = step.getText().trim();

                // Case 1: Multiline JSON block (triple-quoted)
                if (step.isMultiLineJson() && step.getMultilineTextLineCount() > MULTILINE_JSON_THRESHOLD) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Large multiline JSON block detected. Consider externalizing the payload.",
                            getId()
                    ));
                }

                // Case 2: Inline JSON in a single line
                else if (text.startsWith("* def") && text.contains("{") && text.length() > SINGLE_LINE_JSON_LENGTH) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Large inline JSON block detected. Consider externalizing the payload.",
                            getId()
                    ));
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "large-json-inline"; }
    @Override public String getName() { return "Large inline json detection"; }
    @Override public String getDescription() { return "Detects any large inline json and suggest externalizing"; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
