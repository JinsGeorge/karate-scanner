package com.github.jinsgeorge.karate.scanner;

import com.github.jinsgeorge.karate.scanner.issues.FeatureIssue;
import com.github.jinsgeorge.karate.scanner.models.FeatureModel;
import com.github.jinsgeorge.karate.scanner.models.ScenarioModel;
import com.github.jinsgeorge.karate.scanner.models.StepModel;
import com.github.jinsgeorge.karate.scanner.rules.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QualityRulesTest {
    @Test
    void testPrintRule() {
        ScenarioModel scenario = new ScenarioModel();
        scenario.addStep(new StepModel(5, "* print 'bad logging'"));

        FeatureModel model = new FeatureModel();
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);

        PrintUsageRule rule = new PrintUsageRule();
        List<FeatureIssue> issues = rule.apply(model);

        assertEquals(1, issues.size());
        assertEquals("Avoid using 'print'; use 'karate.log()' instead.", issues.get(0).message);
    }

    @Test
    void testUrlRule() {
        ScenarioModel scenario = new ScenarioModel();
        scenario.addStep(new StepModel(5, "Given url 'http://example.com''"));

        FeatureModel model = new FeatureModel();
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);

        HardcodedUrlRule rule = new HardcodedUrlRule();
        List<FeatureIssue> issues = rule.apply(model);

        assertEquals(1, issues.size());
        assertEquals("Hardcoded URL detected: 'http://example.com'. Use config or variables instead.", issues.get(0).message);
    }

    @Test
    void testDuplicateStepRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(6, "*def responseStatus = response.status"));
        scenario.addStep(new StepModel(7, "*def responseStatus = response.status"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        DuplicateStepRule rule = new DuplicateStepRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testEmptyScenarioRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.setLine(1);
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        EmptyScenarioRule rule = new EmptyScenarioRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testDuplicateScenarioNameRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel firstScenario = new ScenarioModel();
        firstScenario.setName("Sample scenario");
        firstScenario.setLine(1);
        ScenarioModel secondScenario = new ScenarioModel();
        secondScenario.setName("Sample scenario");
        secondScenario.setLine(1);
        model.setFilePath("dummy.feature");
        model.addScenario(firstScenario);
        model.addScenario(secondScenario);
        DuplicateScenarioNameRule rule = new DuplicateScenarioNameRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testCommentedOutCodeRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "# Given url 'http://example.com''"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        CommentedOutCodeRule rule = new CommentedOutCodeRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testGherkinsStructureRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "Given url 'http://example.com''"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        GherkinsStructureRule rule = new GherkinsStructureRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testHardcodedAuthPositiveRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "* header Authorization = 'Bearer ' + token"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        HardcodedAuthRule rule = new HardcodedAuthRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(0, issues.size());
    }

    @Test
    void testHardcodedAuthNegativeRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "* header Authorization = 'Bearer eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkR1bW15I"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        HardcodedAuthRule rule = new HardcodedAuthRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testIncorrectStepOrderRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "Given url"));
        scenario.addStep(new StepModel(6, "Then status 200"));
        scenario.addStep(new StepModel(7, "When status != 200"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        IncorrectStepOrderRule rule = new IncorrectStepOrderRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testLargeJsonInlineRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "* def re = '{\"id\": \"a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2g3h4i5j6k7l8m9n0o1p2q3r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2g3h4i5j6k7l8m9n0o1p2q3r4s5t6u7v8w9x0y1z2a3b4c5d6e7f8g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w4y5z6a7b8c9d0e1f2g3h4i5j6k7l8m9n0o1p2q3r4s5t6u7v8w9x0y1z2a3b4c5d6e7f8g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w\",\"name\": \"Large Dummy Data Example Inc.\",\"status\": \"active\",\"version\": 1.5,\"details\": {\"employees\": 12345,\"departments\": 50,\"projects\": 250,\"locations\": [{\"city\": \"Anytown\",\"state\": \"CA\"}, {\"city\": \"Otherville\",\"state\": \"NY\"}]}, \"description\": \"This is an intentionally long and verbose description to ensure the JSON string exceeds the 300-character limit. It contains various details and nested structures for demonstration purposes, showcasing a typical data payload for testing or development environments that require substantial mock data for validation and performance testing of parsing or storage mechanisms. This part is extra long.\",\"timestamp\": \"2025-07-19T23:15:28Z\"}"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        LargeJsonInlineRule rule = new LargeJsonInlineRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testMissingAssertionRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "Given url"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        MissingAssertionRule rule = new MissingAssertionRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }

    @Test
    void testMissingAssertionPositiveRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "Given url"));
        scenario.addStep(new StepModel(6, "When get"));
        scenario.addStep(new StepModel(7, "Then status 200"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        MissingAssertionRule rule = new MissingAssertionRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(0, issues.size());
    }

    @Test
    void testMissingTagRule() {
        FeatureModel model = new FeatureModel();
        ScenarioModel scenario = new ScenarioModel();
        scenario.setName("Sample scenario");
        scenario.addStep(new StepModel(5, "Given url"));
        scenario.addStep(new StepModel(6, "When get"));
        scenario.addStep(new StepModel(7, "Then status 200"));
        model.setFilePath("dummy.feature");
        model.addScenario(scenario);
        MissingTagRule rule = new MissingTagRule();
        List<FeatureIssue> issues = rule.apply(model);
        assertEquals(1, issues.size());
    }
}
