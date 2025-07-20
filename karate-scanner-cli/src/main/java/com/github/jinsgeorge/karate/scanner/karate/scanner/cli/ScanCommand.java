package com.github.jinsgeorge.karate.scanner.karate.scanner.cli;

import com.github.jinsgeorge.karate.scanner.FeatureQualityAnalyzer;
import com.github.jinsgeorge.karate.scanner.report.RuleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "scan", description = "Analyze Karate feature files for quality issues")
public class ScanCommand implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(ScanCommand.class);

    @Option(names = {"-f", "--features"}, description = "Path to Karate .feature files", required = true)
    private File featuresDir;

    @Override
    public Integer call() throws Exception {
        logger.info("📋 Running Karate Quality Analysis on: {}" , featuresDir.getAbsolutePath());

        if (!featuresDir.exists()) {
            logger.error("❌ Features directory does not exist: {}" , featuresDir);
            return 1;
        }

        FeatureQualityAnalyzer.run(featuresDir.toPath(), RuleRegistry.getAll());
        logger.info("✅ Quality report written to target/quality-report.json");

        return 0;
    }
}