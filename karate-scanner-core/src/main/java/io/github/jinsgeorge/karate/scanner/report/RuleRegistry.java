package io.github.jinsgeorge.karate.scanner.report;

import io.github.jinsgeorge.karate.scanner.rules.*;

import java.util.List;

public class RuleRegistry {

    private RuleRegistry(){}
    public static List<QualityRule> getAll() {
        return List.of(
                new PrintUsageRule(),
                new DuplicateStepRule(),
                new EmptyScenarioRule(),
                new DuplicateScenarioNameRule(),
                new CommentedOutCodeRule(),
                new HardcodedUrlRule(),
                new GherkinsStructureRule(),
                new HardcodedAuthRule(),
                new IncorrectStepOrderRule(),
                new LargeJsonInlineRule(),
                new MissingAssertionRule(),
                new MissingTagRule());
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
