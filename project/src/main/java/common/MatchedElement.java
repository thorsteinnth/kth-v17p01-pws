package common;

import javax.xml.bind.annotation.XmlElement;

public class MatchedElement {

    private String outputElement;
    private String inputElement;
    private float score;

    public MatchedElement() {
    }

    public String getOutputElement() {
        return outputElement;
    }

    @XmlElement(name = "OutputElement")
    public void setOutputElement(String outputElement) {
        this.outputElement = outputElement;
    }

    public String getInputElement() {
        return inputElement;
    }

    @XmlElement(name = "InputElement")
    public void setInputElement(String inputElement) {
        this.inputElement = inputElement;
    }

    public float getScore() {
        return score;
    }

    @XmlElement(name = "Score")
    public void setScore(float score) {
        this.score = score;
    }
}
