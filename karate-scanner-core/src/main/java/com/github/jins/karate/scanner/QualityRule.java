package com.github.jins.karate.scanner;

import com.github.jins.karate.scanner.issues.FeatureIssue;
import com.github.jins.karate.scanner.models.FeatureModel;

import java.util.List;

public interface QualityRule {
    List<FeatureIssue> apply(FeatureModel model);
}