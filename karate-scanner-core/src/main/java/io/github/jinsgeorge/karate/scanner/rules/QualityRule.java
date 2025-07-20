package io.github.jinsgeorge.karate.scanner.rules;

import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import io.github.jinsgeorge.karate.scanner.models.FeatureModel;

import java.util.List;

public interface QualityRule {
    List<FeatureIssue> apply(FeatureModel model);

    String getId();           // e.g., "print-usage"
    String getName();         // e.g., "Discouraged use of print"
    String getDescription();  // For Sonar rule description
    String getSeverity();     // e.g., "MINOR", "INFO"
    String getType();
}