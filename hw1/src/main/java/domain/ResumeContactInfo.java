package domain;

import javax.xml.bind.annotation.XmlElement;

public class ResumeContactInfo {
    /*
        <telephonenumber>12345678901234</telephonenumber>
        <email>jonjonsson@kth.se</email>
     */

    private String telephonenumber;
    private String email;

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

    @Override
    public String toString() {
        return "ResumeContactInfo{" +
                "telephonenumber='" + telephonenumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
