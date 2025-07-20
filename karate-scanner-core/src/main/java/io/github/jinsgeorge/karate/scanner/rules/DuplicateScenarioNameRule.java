package io.github.jinsgeorge.karate.scanner.rules;

import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import io.github.jinsgeorge.karate.scanner.models.FeatureModel;
import io.github.jinsgeorge.karate.scanner.models.ScenarioModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateScenarioNameRule implements QualityRule {

    @Override
    public List<FeatureIssue> apply(FeatureModel model) {
        List<FeatureIssue> issues = new ArrayList<>();
        Map<String, Integer> seen = new HashMap<>();

        for (ScenarioModel scenario : model.getScenarios()) {
            String name = scenario.getName().toLowerCase();
            if (seen.containsKey(name)) {
                issues.add(new FeatureIssue(
                        model.getFilePath(),
                        scenario.getLine(),
                        "Duplicate scenario name: '" + scenario.getName() + "'",
                        getId()
                ));
            } else {
                seen.put(name, scenario.getLine());
            }
        }

        return issues;
    }

    @Override public String getId() { return "duplicate-scenario-name"; }
    @Override public String getName() { return "Duplicate scenario name usage"; }
    @Override public String getDescription() { return "Detects duplicate scenario names"; }
    @Override public String getSeverity() { return "MINOR"; }
    @Override public String getType() { return "CODE_SMELL"; }
}
