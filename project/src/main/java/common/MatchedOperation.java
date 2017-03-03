package common;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class MatchedOperation {

    private String outputOperationName;
    private String inputOperationName;
    private double opScore;
    private List<MatchedElement> matchedElement;

    public MatchedOperation() {
    }

    public String getOutputOperationName() {
        return outputOperationName;
    }

    @XmlElement(name = "OutputOperationName")
    public void setOutputOperationName(String outputOperationName) {
        this.outputOperationName = outputOperationName;
    }

    public String getInputOperationName() {
        return inputOperationName;
    }

    @XmlElement(name = "InputOperationName")
    public void setInputOperationName(String inputOperationName) {
        this.inputOperationName = inputOperationName;
    }

    public double getOpScore() {
        return opScore;
    }

    @XmlElement(name = "OpScore")
    public void setOpScore(double opScore) {
        this.opScore = opScore;
    }

    public List<MatchedElement> getMatchedElement() {
        return matchedElement;
    }

    @XmlElement(name = "MatchedElement")
    public void setMatchedElement(List<MatchedElement> matchedElement) {
        this.matchedElement = matchedElement;
    }
}
