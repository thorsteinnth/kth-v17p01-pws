package domain;

import javax.xml.bind.annotation.XmlElement;

public class ResumeContactInfo
{
    /*
    <contactinfo>
        <telephonenumber>12345678901221</telephonenumber>
        <email>william@spotify.com</email>
        <cancontact>No</cancontact>
    </contactinfo>
     */

    private String telephonenumber;
    private String email;
    private String cancontact;

    public ResumeContactInfo() {
    }

    public String getTelephonenumber() {
        return telephonenumber;
    }

    @XmlElement
    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber = telephonenumber;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCancontact() {
        return cancontact;
    }

    @XmlElement
    public void setCancontact(String cancontact) {
        this.cancontact = cancontact;
    }

    @Override
    public String toString()
    {
        return "ResumeContactInfo{" +
                "telephonenumber='" + telephonenumber + '\'' +
                ", email='" + email + '\'' +
                ", cancontact='" + cancontact + '\'' +
                '}';
    }
}
