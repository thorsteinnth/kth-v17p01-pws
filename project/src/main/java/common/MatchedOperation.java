package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder={"outputOperationName", "inputOperationName", "opScore", "matchedElement"})
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

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "OutputOperationName")
    public void setOutputOperationName(String outputOperationName) {
        this.outputOperationName = outputOperationName;
    }

    public String getInputOperationName() {
        return inputOperationName;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "InputOperationName")
    public void setInputOperationName(String inputOperationName) {
        this.inputOperationName = inputOperationName;
    }

    public double getOpScore() {
        return opScore;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "OpScore")
    public void setOpScore(double opScore) {
        this.opScore = opScore;
    }

    public List<MatchedElement> getMatchedElement() {
        return matchedElement;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "MatchedElement")
    public void setMatchedElement(List<MatchedElement> matchedElement) {
        this.matchedElement = matchedElement;
    }
}
