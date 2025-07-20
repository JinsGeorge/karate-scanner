package io.github.jinsgeorge.karate.scanner.rules;

import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import io.github.jinsgeorge.karate.scanner.models.FeatureModel;
import io.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import io.github.jinsgeorge.karate.scanner.models.StepModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HardcodedAuthRule implements QualityRule {

    private static final Pattern AUTH_PATTERN = Pattern.compile("(Basic|Bearer)\\s+[A-Za-z0-9\\-._~+/=]+");

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            for (StepModel step : scenario.getSteps()) {
                Matcher matcher = AUTH_PATTERN.matcher(step.getText());
                if (matcher.find()) {
                    issues.add(new FeatureIssue(
                            model.getFilePath(),
                            step.getLine(),
                            "Hardcoded authentication token detected. Use variables instead.",
                            getId()
                    ));
                }
            }
        }

        return issues;
    }

    @Override public String getId() { return "hardcoded-auth"; }
    @Override public String getName() { return "Harder coded auth detection"; }
    @Override public String getDescription() { return "Detects hard coded authentication"; }
    @Override public String getSeverity() { return "CRITICAL"; }
    @Override public String getType() { return "VULNERABILITY"; }
}
