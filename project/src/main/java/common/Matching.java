package common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder={"outputServiceName", "inputServiceName", "matchedOperation", "wsScore"})
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

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "OutputServiceName")
    public void setOutputServiceName(String outputServiceName) {
        this.outputServiceName = outputServiceName;
    }

    public String getInputServiceName() {
        return inputServiceName;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "InputServiceName")
    public void setInputServiceName(String inputServiceName) {
        this.inputServiceName = inputServiceName;
    }

    public List<MatchedOperation> getMatchedOperation() {
        return matchedOperation;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "MatchedOperation")
    public void setMatchedOperation(List<MatchedOperation> matchedOperation) {
        this.matchedOperation = matchedOperation;
    }

    public double getWsScore() {
        return wsScore;
    }

    @XmlElement(namespace = "http://www.kth.se/ict/id2208/Matching", name = "WsScore")
    public void setWsScore(double wsScore) {
        this.wsScore = wsScore;
    }
}
