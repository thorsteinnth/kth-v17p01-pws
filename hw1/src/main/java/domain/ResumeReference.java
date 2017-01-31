package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "companyname", "contactinfo"})
public class ResumeReference {

    /*
        <name>William Jonsson</name>
        <companyname>Spotify</companyname>
        <contactinfo>
            <telephonenumber>12345678901221</telephonenumber>
            <email>william@spotify.com</email>
            <cancontact>No</cancontact>
        </contactinfo>
     */

    private String name;
    private String companyname;
    private ResumeContactInfo contactinfo;

    public ResumeReference() {

    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyname() {
        return companyname;
    }

    @XmlElement
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public ResumeContactInfo getContactinfo() {
        return contactinfo;
    }

    @XmlElement
    public void setContactinfo(ResumeContactInfo contactInfo) {
        this.contactinfo = contactInfo;
    }

    @Override
    public String toString() {
        return "ResumeReference{" +
                "name='" + name + '\'' +
                ", companyname='" + companyname + '\'' +
                ", contactinfo=" + contactinfo +
                '}';
    }
}
