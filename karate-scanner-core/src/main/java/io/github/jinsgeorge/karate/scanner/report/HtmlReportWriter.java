package io.github.jinsgeorge.karate.scanner.report;


import io.github.jinsgeorge.karate.scanner.issues.FeatureIssue;

import java.io.*;
import java.util.List;

public class HtmlReportWriter {

    private HtmlReportWriter(){}
    public static void write(List<FeatureIssue> issues, File output) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(output))) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Karate Quality Report</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; padding: 1em; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 1em; }");
            out.println("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f0f0f0; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h1>Karate Quality Report</h1>");
            out.println("<table>");
            out.println("<tr><th>File</th><th>Line</th><th>Rule</th><th>Message</th></tr>");
            for (FeatureIssue issue : issues) {
                out.printf("<tr><td>%s</td><td>%d</td><td>%s</td><td>%s</td></tr>%n",
                        issue.file, issue.line, issue.ruleId, issue.message);
            }
            out.println("</table>");
            out.println("</body></html>");
        }
    }
}
