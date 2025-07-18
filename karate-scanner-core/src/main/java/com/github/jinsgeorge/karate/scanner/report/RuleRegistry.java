package com.github.jinsgeorge.karate.scanner.report;

import com.github.jinsgeorge.karate.scanner.rules.DuplicateStepRule;
import com.github.jinsgeorge.karate.scanner.rules.PrintUsageRule;
import com.github.jinsgeorge.karate.scanner.rules.QualityRule;

import java.util.List;

public class RuleRegistry {

    private RuleRegistry(){}
    public static List<QualityRule> getAll() {
        return List.of(new PrintUsageRule(), new DuplicateStepRule());
    }

    public static List<ExternalRule> getExternalRules() {
        return getAll().stream()
                .map(rule -> new ExternalRule(
                        rule.getId(),
                        rule.getName(),
                        rule.getDescription(),
                        "karate-scanner",
                        rule.getType(),
                        rule.getSeverity()
                )).toList();
    }
}
