package com.github.jins.karate.scanner.karate.scanner.cli;



import com.github.jins.karate.scanner.FeatureQualityAnalyzer;
import com.github.jins.karate.scanner.QualityRule;
import com.github.jins.karate.scanner.rules.DuplicateStepRule;
import com.github.jins.karate.scanner.rules.PrintUsageRule;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "quality", description = "Analyze Karate feature files for quality issues")
public class QualityCommand implements Callable<Integer> {

    @Option(names = {"-f", "--features"}, description = "Path to Karate .feature files", required = true)
    private File featuresDir;

    @Override
    public Integer call() throws Exception {
        System.out.println("📋 Running Karate Quality Analysis on: " + featuresDir.getAbsolutePath());

        if (!featuresDir.exists()) {
            System.err.println("❌ Features directory does not exist: " + featuresDir);
            return 1;
        }

        List<QualityRule> rules = List.of(
                new PrintUsageRule(),
                new DuplicateStepRule()
        );

        FeatureQualityAnalyzer.run(featuresDir.toPath(), rules);
        System.out.println("✅ Quality report written to target/quality-report.json");

        return 0;
    }
}