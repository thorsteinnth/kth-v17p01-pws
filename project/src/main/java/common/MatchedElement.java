package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"outputElement", "inputElement", "score"})
public class MatchedElement {

    private String outputElement;
    private String inputElement;
    private double score;

    public MatchedElement() {
    }

    public String getOutputElement() {
        return outputElement;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "OutputElement")
    public void setOutputElement(String outputElement) {
        this.outputElement = outputElement;
    }

    public String getInputElement() {
        return inputElement;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "InputElement")
    public void setInputElement(String inputElement) {
        this.inputElement = inputElement;
    }

    public double getScore() {
        return score;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "Score")
    public void setScore(double score) {
        this.score = score;
    }
}
