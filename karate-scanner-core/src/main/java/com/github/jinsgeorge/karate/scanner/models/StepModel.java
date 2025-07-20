package com.github.jinsgeorge.karate.scanner.models;


public class StepModel {
    private int line;
    private String text;
    private String multilineText;
    private int multilineTextLineCount;

    public StepModel(int line, String text) {
        this.line = line;
        this.text = text;
    }

    public int getLine() { return line; }
    public String getText() { return text; }
    public String getMultilineText() {return multilineText; }
    public boolean isMultiLineJson()
    {
        if (multilineText == null || text.isEmpty()) {
            return false;
        }
        else {

            boolean containsOpeningBracket = multilineText.contains("{");
            boolean containsClosingBracket = multilineText.contains("}");
            return containsOpeningBracket && containsClosingBracket;
        }

    }
    public int getMultilineTextLineCount(){return multilineTextLineCount;}

    public void setMultilineText(String value){
        this.multilineText  = value;
    }

}