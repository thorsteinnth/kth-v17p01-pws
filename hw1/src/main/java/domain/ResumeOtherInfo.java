package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"typeofjob", "motivationletter", "othercomments"})
public class ResumeOtherInfo {

    /*
        <typeofjob>permanent</typeofjob>
        <motivationletter>I would really like to work at King Games</motivationletter>
        <othercomments>No comment</othercomments>
     */

    private String typeofjob;
    private String motivationletter;
    private String othercomments;

    public ResumeOtherInfo() {

    }

    public String getTypeofjob() {
        return typeofjob;
    }

    @XmlElement
    public void setTypeofjob(String typeofjob) {
        this.typeofjob = typeofjob;
    }

    public String getMotivationletter() {
        return motivationletter;
    }

    @XmlElement
    public void setMotivationletter(String motivationletter) {
        this.motivationletter = motivationletter;
    }

    public String getOthercomments() {
        return othercomments;
    }

    @XmlElement
    public void setOthercomments(String othercomments) {
        this.othercomments = othercomments;
    }

    @Override
    public String toString() {
        return "ResumeOtherInfo{" +
                "typeofjob='" + typeofjob + '\'' +
                ", motivationletter='" + motivationletter + '\'' +
                ", othercomments='" + othercomments + '\'' +
                '}';
    }
}
