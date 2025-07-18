package com.github.jinsgeorge.karate.scanner.report;

public class PrimaryLocation {
    public String message;
    public String filePath;
    public TextRange textRange;

    public PrimaryLocation(String message, String filePath, TextRange textRange) {
        this.message = message;
        this.filePath = filePath;
        this.textRange = textRange;
    }
}
