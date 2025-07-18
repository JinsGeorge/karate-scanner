package com.github.jinsgeorge.karate.scanner.karate.scanner.cli;



import com.github.jinsgeorge.karate.scanner.FeatureQualityAnalyzer;
import com.github.jinsgeorge.karate.scanner.rules.QualityRule;
import com.github.jinsgeorge.karate.scanner.rules.DuplicateStepRule;
import com.github.jinsgeorge.karate.scanner.rules.PrintUsageRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.List;
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

        List<QualityRule> rules = List.of(
                new PrintUsageRule(),
                new DuplicateStepRule()
        );

        FeatureQualityAnalyzer.run(featuresDir.toPath(), rules);
        logger.info("✅ Quality report written to target/quality-report.json");

        return 0;
    }
}