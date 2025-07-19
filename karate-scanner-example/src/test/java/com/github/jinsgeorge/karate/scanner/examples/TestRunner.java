package com.github.jinsgeorge.karate.scanner.examples;
import com.intuit.karate.junit5.Karate;

public class TestRunner {

    @Karate.Test
    Karate testAllFeatures() {
        return Karate.run("classpath:com/github/jinsgeorge/karate/scanner/examples");
    }
}
