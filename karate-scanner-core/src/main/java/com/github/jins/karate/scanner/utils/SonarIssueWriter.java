package com.github.jins.karate.scanner.utils;

import com.github.jins.karate.scanner.FeatureQualityAnalyzer;
import com.github.jins.karate.scanner.issues.FeatureIssue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SonarIssueWriter {

    private static final Logger logger = LoggerFactory.getLogger(FeatureQualityAnalyzer.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();

    /**
     * Writes a list of QualityIssues to a Sonar-compatible JSON file.
     *
     * @param issues the list of issues detected
     * @param outputFile the output file (e.g., "target/sonar/issues.json")
     * @throws IOException if writing fails
     */
    public static void writeIssues(List<FeatureIssue> issues, File outputFile) throws IOException {
        if (issues == null || issues.isEmpty()) {
            logger.info("No issues found to write to Sonar report.");
            return;
        }

        // Ensure parent directory exists
        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // Write JSON to file
        prettyWriter.writeValue(outputFile, issues);
        logger.info("Sonar issues written to: " + outputFile.getAbsolutePath());
    }


}
