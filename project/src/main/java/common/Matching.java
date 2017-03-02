package common;


import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class Matching {

    private String outputServiceName;
    private String inputServiceName;
    private ArrayList<MatchedOperation> matchedOperation;
    private String wsScore;

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

    public ArrayList<MatchedOperation> getMatchedOperation() {
        return matchedOperation;
    }

    @XmlElement(name = "MatchedOperation")
    public void setMatchedOperation(ArrayList<MatchedOperation> matchedOperation) {
        this.matchedOperation = matchedOperation;
    }

    public String getWsScore() {
        return wsScore;
    }

    @XmlElement(name = "WsScore")
    public void setWsScore(String wsScore) {
        this.wsScore = wsScore;
    }
}
