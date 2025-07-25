package io.github.jinsgeorge.karate.scanner.maven;

import io.github.jinsgeorge.karate.scanner.FeatureQualityAnalyzer;
import io.github.jinsgeorge.karate.scanner.report.RuleRegistry;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Path;
import java.nio.file.Paths;

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
            FeatureQualityAnalyzer.run(dirPath, RuleRegistry.getAll());

            getLog().info("Karate Scanner completed successfully.");
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run Karate Scanner", e);
        }
    }
}
