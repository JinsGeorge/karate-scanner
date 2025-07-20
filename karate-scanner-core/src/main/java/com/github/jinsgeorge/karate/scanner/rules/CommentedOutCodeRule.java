package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommentedOutCodeRule implements QualityRule {

    private static final Pattern CODE_COMMENT_PATTERN = Pattern.compile("^\\s*#\\s*(Given|When|Then|And|\\*).+");

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {
                if (CODE_COMMENT_PATTERN.matcher(step.getText()).matches()) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Commented-out code detected: '" + step.getText().trim() + "'",
                            getId()
                    ));
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "commented-out-code"; }
    @Override public String getName() { return "Commented Code Detection"; }
    @Override public String getDescription() { return "Detects commented code to be removed"; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
