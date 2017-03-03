package common;


import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Matching {

    private String outputServiceName;
    private String inputServiceName;
    private List<MatchedOperation> matchedOperation;
    private double wsScore;

    public Matching() {
    }

    public String getOutputServiceName() {
        return outputServiceName;
    }

    @XmlElement(name = "OutputServiceName")
    public void setOutputServiceName(String outputServiceName) {
        this.outputServiceName = outputServiceName;
    }

    public String getInputServiceName() {
        return inputServiceName;
    }

    @XmlElement(name = "InputServiceName")
    public void setInputServiceName(String inputServiceName) {
        this.inputServiceName = inputServiceName;
    }

    public List<MatchedOperation> getMatchedOperation() {
        return matchedOperation;
    }

    @XmlElement(name = "MatchedOperation")
    public void setMatchedOperation(List<MatchedOperation> matchedOperation) {
        this.matchedOperation = matchedOperation;
    }

    public double getWsScore() {
        return wsScore;
    }

    @XmlElement(name = "WsScore")
    public void setWsScore(double wsScore) {
        this.wsScore = wsScore;
    }
}
