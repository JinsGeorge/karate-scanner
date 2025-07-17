package com.github.jins.karate.scanner.maven;

import com.github.jins.karate.scanner.FeatureQualityAnalyzer;
import com.github.jins.karate.scanner.QualityRule;
import com.github.jins.karate.scanner.rules.DuplicateStepRule;
import com.github.jins.karate.scanner.rules.PrintUsageRule;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Mojo to scan Karate .feature files and generate quality reports.
 */
@Mojo(name = "scan", defaultPhase = LifecyclePhase.VERIFY)
public class KarateScannerMojo extends AbstractMojo {

    /**
     * Directory containing .feature files.
     */
    @Parameter(property = "karateScanner.directory", defaultValue = "${project.basedir}/src/test/java", required = true)
    private String directory;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            getLog().info("Running Karate Scanner on: " + directory);

            Path dirPath = Paths.get(directory);
            List<QualityRule> rules = Arrays.asList(
                    new DuplicateStepRule(),
                    new PrintUsageRule()
                    // Add more rule classes here as you create them
            );

            FeatureQualityAnalyzer.run(dirPath, rules);

            getLog().info("Karate Scanner completed successfully.");
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run Karate Scanner", e);
        }
    }
}
