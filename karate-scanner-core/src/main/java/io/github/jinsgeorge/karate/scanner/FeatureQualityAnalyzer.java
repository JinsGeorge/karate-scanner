package io.github.jinsgeorge.karate.scanner;

import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import io.github.jinsgeorge.karate.scanner.parser.FeatureParser;
import io.github.jinsgeorge.karate.scanner.report.HtmlReportWriter;
import io.github.jinsgeorge.karate.scanner.report.RuleRegistry;
import io.github.jinsgeorge.karate.scanner.rules.QualityRule;
import io.github.jinsgeorge.karate.scanner.utils.SonarIssueWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.jinsgeorge.karate.scanner.report.ExternalIssueReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureQualityAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(FeatureQualityAnalyzer.class);
    private FeatureQualityAnalyzer(){}
    public static void run(Path featuresDir, List<QualityRule> rules) throws IOException {
        List<FeatureIssue> allIssues = new ArrayList<>();
        List<Path> featureFiles = findFeatureFiles(featuresDir);

        for (Path featurePath : featureFiles) {
            var model = FeatureParser.parse(featurePath);
            for (QualityRule rule : rules) {
                allIssues.addAll(rule.apply(model));
            }
        }

        // Write quality report
        try (var writer = new FileWriter("target/quality-report.json")) {
            new com.google.gson.Gson().toJson(allIssues, writer);
        }

        // Prepare SonarQube metadata and issues
        var externalRules = RuleRegistry.getExternalRules();
        var externalIssues = allIssues.stream()
                .map(issue -> issue.toExternalIssue("karate-scanner"))
                .collect(Collectors.toList());

        // Output Sonar-compatible JSON
        SonarIssueWriter.write(
                new ExternalIssueReport(externalRules, externalIssues),
                Paths.get("target/sonar-issues.json").toFile()
        );

        // Output html
        HtmlReportWriter.write(allIssues, new File("target/quality-report.html"));

    }

    public static List<Path> findFeatureFiles(Path dir) {
        List<Path> result = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(dir)) {
            stream
                    .filter(p -> p.toString().endsWith(".feature"))
                    .forEach(result::add);
        } catch (IOException e) {
            logger.error("Error walking directory: {}", dir, e);
        }
        return result;
    }
}
