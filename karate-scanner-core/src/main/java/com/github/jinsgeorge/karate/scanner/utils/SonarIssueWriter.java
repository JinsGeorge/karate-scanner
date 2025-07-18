package com.github.jinsgeorge.karate.scanner.utils;

import com.github.jinsgeorge.karate.scanner.FeatureQualityAnalyzer;
import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.jinsgeorge.karate.scanner.report.ExternalIssueReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SonarIssueWriter {

    private SonarIssueWriter(){}
    private static final Logger logger = LoggerFactory.getLogger(SonarIssueWriter.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();

    /**
     * Writes a list of QualityIssues to a Sonar-compatible JSON file.
     *
     * @param report the list of issues detected
     * @param outputFile the output file (e.g., "target/sonar/issues.json")
     * @throws IOException if writing fails
     */
    public static void write(ExternalIssueReport report, File outputFile) throws IOException {
        // Ensure parent directory exists
        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // Write JSON to file
        prettyWriter.writeValue(outputFile, report);
        logger.info("Sonar issue report is generated at {}", outputFile.getAbsolutePath());
    }


}
