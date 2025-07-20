package com.github.jinsgeorge.karate.scanner.parser;

import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FeatureParser {

    private static final String GHERKIN_DOC_STRING_DELIMITER  = "\"\"\"";
    private static final String FEATURE_PREFIX = "Feature:";
    private static final String SCENARIO_PREFIX = "Scenario"; // Covers both "Scenario:" and "Scenario Outline:"


    private FeatureParser() {}
    public static FeatureModel parse(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        ParserContext ctx = new ParserContext();
        ctx.feature.setFilePath(path.toAbsolutePath().toString().replace("\\", "/"));

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;
            processLine(line, lineNumber, ctx);
        }
        return ctx.feature;
    }

    private static void processLine(String line, int lineNumber, ParserContext ctx) {
            // 1. Always check for multiline capture first
            if (handleMultiline(line, ctx)) {
                return; // Line was handled as part of a multiline block
            }

            // 2. Handle Feature definition
            if (line.startsWith(FEATURE_PREFIX)) {
                handleFeatureLine(line, ctx);
                return;
            }

            // 3. Handle Tags (distinguish feature vs. scenario tags)
            if (line.startsWith("@")) {
                handleTagLine(line, ctx);
                return;
            }

            // 4. Handle Scenario definition
            if (line.startsWith(SCENARIO_PREFIX)) { // "Scenario" covers "Scenario Outline" too
                handleScenarioLine(line, lineNumber, ctx);
                return;
            }

            // 5. Handle Steps (Given, When, Then, And, *)
            if (isStep(line)) {
                handleStepLine(line, lineNumber, ctx);
                return;
            }

            // 6. Handle start of multiline (Doc String)
            if (line.equals(GHERKIN_DOC_STRING_DELIMITER)) {
                startMultilineCapture(line, lineNumber, ctx);
            }
    }

    private static void handleTagLine(String line, ParserContext ctx) {
        if (!ctx.featureProcessed) {
            // If feature not yet processed, assume this is a feature-level tag
            ctx.feature.addTag(line);
        } else {
            // Otherwise, it's a tag for the next scenario
            ctx.pendingScenarioTag = line;
        }
    }

    private static void handleFeatureLine(String line, ParserContext ctx) {
        ctx.feature.setTitle(line.substring(FEATURE_PREFIX.length()).trim());
        ctx.featureProcessed = true;
    }

    private static void handleScenarioLine(String line, int lineNumber, ParserContext ctx) {
        ctx.currentScenario = new ScenarioModel();
        if (ctx.pendingScenarioTag != null) {
            ctx.currentScenario.addTag(ctx.pendingScenarioTag);
            ctx.pendingScenarioTag = null; // Consume the pending tag
        }
        ctx.currentScenario.setLine(lineNumber);
        ctx.currentScenario.setName(extractScenarioName(line));
        ctx.feature.addScenario(ctx.currentScenario);
    }

    private static void handleStepLine(String line, int lineNumber, ParserContext ctx) {
        if (ctx.currentScenario != null) {
            ctx.currentScenario.addStep(new StepModel(lineNumber, line));
        }
    }

    private static void startMultilineCapture(String line, int lineNumber, ParserContext ctx) {
        ctx.capturingMultiline = true;
        ctx.multilineBuffer = new StringBuilder();
        // The step model itself should store the raw """ for the step line text
        ctx.multilineStep = new StepModel(lineNumber, line);
    }

    private static boolean handleMultiline(String line, ParserContext ctx) {
        if (!ctx.capturingMultiline) {
            return false; // Not currently capturing a multiline block
        }

        if (line.equals(GHERKIN_DOC_STRING_DELIMITER)) {
            ctx.capturingMultiline = false;
            if (ctx.multilineStep != null && ctx.currentScenario != null) {
                ctx.multilineStep.setMultilineText(ctx.multilineBuffer.toString());
                ctx.currentScenario.addStep(ctx.multilineStep);
            }
        } else {
            // Append line and a newline, preserving original formatting for doc string
            ctx.multilineBuffer.append(line).append("\n");
        }
        return true; // This line was part of a multiline block
    }

    private static boolean isStep(String line) {
        return line.startsWith("Given") || line.startsWith("When") ||
                line.startsWith("Then") || line.startsWith("And") || line.startsWith("*");
    }

    private static String extractScenarioName(String line) {
        return line.replace("Scenario Outline:", "")
                .replace("Scenario:", "")
                .trim();
    }

}
