package com.github.jins.karate.scanner;

import com.github.jins.karate.scanner.issues.FeatureIssue;
import com.github.jins.karate.scanner.models.FeatureModel;
import com.github.jins.karate.scanner.parser.FeatureParser;
import com.github.jins.karate.scanner.utils.SonarIssueWriter;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureQualityAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(FeatureQualityAnalyzer.class);
    private FeatureQualityAnalyzer(){}
    public static void run(Path featuresDir, List<QualityRule> rules) throws IOException {
        List<FeatureIssue> allIssues = new ArrayList<>();
        List<Path> featureFiles = findFeatureFiles(featuresDir);

        for (Path featurePath : featureFiles) {
            FeatureModel model = FeatureParser.parse(featurePath);
            for (QualityRule rule : rules) {
                allIssues.addAll(rule.apply(model));
            }
        }

        // Output regular JSON
        try (FileWriter writer = new FileWriter("target/quality-report.json")) {
            new Gson().toJson(allIssues, writer);
        }

        // Output SonarQube Generic Issue Format
        SonarIssueWriter.writeIssues(allIssues, Paths.get("target/sonar-issues.json").toFile());
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
