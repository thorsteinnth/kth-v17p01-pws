package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Resume {

    /*
    <resume>
    <basicinfo>
        <ssn>8001010000</ssn>
        <name>Jon Jonsson</name>
        <address>Sveavägen 1</address>
        <city>Stockholm</city>
        <country>Sweden</country>
    </basicinfo>
    <contactinfo>
        <telephonenumber>12345678901234</telephonenumber>
        <email>jonjonsson@kth.se</email>
    </contactinfo>
    <placesdesiredtowork>King</placesdesiredtowork>
    <references>
        <name>William Jonsson</name>
        <companyname>Spotify</companyname>
        <contactinfo>
            <telephonenumber>12345678901221</telephonenumber>
            <email>william@spotify.com</email>
        </contactinfo>
    </references>
    <otherinfo>
        <typeofjob>permanent</typeofjob>
        <motivationletter>I would really like to work at King Games</motivationletter>
        <othercomments>No comment</othercomments>
    </otherinfo>
</resume>
     */

    private ResumeBasicInfo basicinfo;
    private ResumeContactInfo contactinfo;
    private String placesdesiredtowork;
    private List<ResumeReference> references;
    private ResumeOtherInfo otherinfo;

    public Resume() {

    }

    public ResumeBasicInfo getBasicinfo() {
        return basicinfo;
    }

    @XmlElement
    public void setBasicinfo(ResumeBasicInfo basicinfo) {
        this.basicinfo = basicinfo;
    }

    public ResumeContactInfo getContactinfo() {
        return contactinfo;
    }

    @XmlElement
    public void setContactinfo(ResumeContactInfo contactinfo) {
        this.contactinfo = contactinfo;
    }

    public String getPlacesdesiredtowork() {
        return placesdesiredtowork;
    }

    @XmlElement
    public void setPlacesdesiredtowork(String placesdesiredtowork) {
        this.placesdesiredtowork = placesdesiredtowork;
    }

    public List<ResumeReference> getReferences() {
        return references;
    }

    @XmlElement
    public void setReferences(List<ResumeReference> references) {
        this.references = references;
    }

    public ResumeOtherInfo getOtherinfo() {
        return otherinfo;
    }

    @XmlElement
    public void setOtherinfo(ResumeOtherInfo otherinfo) {
        this.otherinfo = otherinfo;
    }
}
