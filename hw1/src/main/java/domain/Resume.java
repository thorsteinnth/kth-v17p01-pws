package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = {"basicinfo", "contactinfo", "placesdesiredtowork", "references", "otherinfo"})
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
            <cancontact>Yes</cancontact>
        </contactinfo>
        <placesdesiredtowork>King</placesdesiredtowork>
        <references>
            <name>William Jonsson</name>
            <companyname>Spotify</companyname>
            <contactinfo>
                <telephonenumber>12345678901221</telephonenumber>
                <email>william@spotify.com</email>
                <cancontact>No</cancontact>
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

    @Override
    public String toString() {
        return "Resume{" +
                "basicinfo=" + basicinfo +
                ", contactinfo=" + contactinfo +
                ", placesdesiredtowork='" + placesdesiredtowork + '\'' +
                ", references=" + references +
                ", otherinfo=" + otherinfo +
                '}';
    }
}
