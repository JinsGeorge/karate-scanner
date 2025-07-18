package com.github.jinsgeorge.karate.scanner.parser;

import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FeatureParser {

    private FeatureParser()
    {

    }
    public static FeatureModel parse(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        FeatureModel feature = new FeatureModel();
        feature.setFilePath(path.toString());

        ScenarioModel currentScenario = null;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int lineNumber = i + 1;

            if (line.startsWith("@")) {
                feature.addTag(line);
            } else if (line.startsWith("Feature:")) {
                feature.setTitle(line.substring(8).trim());
            } else if (line.startsWith("Scenario") || line.startsWith("Scenario Outline")) {
                currentScenario = new ScenarioModel();
                currentScenario.setLine(lineNumber);
                currentScenario.setName(line.replace("Scenario Outline:", "").replace("Scenario:", "").trim());
                feature.addScenario(currentScenario);
            } else if ((line.startsWith("Given") || line.startsWith("When") || line.startsWith("Then") ||
                    line.startsWith("And") || line.startsWith("*")) && currentScenario != null) {
                    currentScenario.addStep(new StepModel(lineNumber, line));
                }

        }

        return feature;
    }
}
