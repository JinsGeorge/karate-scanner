package io.github.jinsgeorge.karate.scanner.examples;
import com.intuit.karate.junit5.Karate;

public class TestRunner {

    @Karate.Test
    Karate testAllFeatures() {
        return Karate.run("classpath:io/github/jinsgeorge/karate/scanner/examples");
    }
}
