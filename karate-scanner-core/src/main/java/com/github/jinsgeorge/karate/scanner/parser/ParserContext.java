package com.github.jinsgeorge.karate.scanner.parser;

import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;

public class ParserContext {
    FeatureModel feature ;
    ScenarioModel currentScenario;
    boolean capturingMultiline;
    StringBuilder multilineBuffer;
    StepModel multilineStep ;
    String pendingScenarioTag ;
    boolean featureProcessed;

    ParserContext() {
        feature = new FeatureModel();
        capturingMultiline = false;
        multilineBuffer = null;
        multilineStep = null;
        pendingScenarioTag = null;
        featureProcessed = false;
    }

    void finalizeMultiline() {
        if (multilineStep != null && currentScenario != null) {
            multilineStep.setMultilineText(multilineBuffer.toString());
            currentScenario.addStep(multilineStep);
        }
        capturingMultiline = false;
        multilineBuffer = null;
        multilineStep = null;
    }

    void beginMultiline(int lineNumber, String line) {
        capturingMultiline = true;
        multilineBuffer = new StringBuilder();
        multilineStep = new StepModel(lineNumber, line);
    }
}
