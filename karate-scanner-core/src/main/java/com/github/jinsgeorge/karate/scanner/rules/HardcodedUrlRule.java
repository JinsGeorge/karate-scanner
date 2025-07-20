package com.github.jinsgeorge.karate.scanner.rules;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HardcodedUrlRule implements QualityRule {

    private static final Pattern URL_PATTERN = Pattern.compile("https?://[^\\s'\"]+");

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {
                Matcher matcher = URL_PATTERN.matcher(step.getText());
                if (matcher.find()) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Hardcoded URL detected: '" + matcher.group() + "'. Use config or variables instead.",
                            getId()
                    ));
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "hardcoded-url"; }
    @Override public String getName() { return "Hardcoded URL Detection"; }
    @Override public String getDescription() { return "Detects hardcoded URLs within Karate feature files, indicating potential for using variables or config"; }
    @Override public String getSeverity() { return "MAJOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
