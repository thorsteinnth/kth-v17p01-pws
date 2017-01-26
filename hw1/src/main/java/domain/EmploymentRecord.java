package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class EmploymentRecord {

    /*
    <employment>
        <companyname>LS Retail</companyname>
        <roledescription>iOS developer</roledescription>
        <startdate>2015-12-13</startdate>
        <enddate>2017-01-25</enddate>
        <contactemail>boss@lsretail.com</contactemail>
        <cancontact>Yes</cancontact>
    </employment>
     */

    private String companyname;
    private String roledescription;
    private String startdate;
    private String enddate;
    private String contactemail;
    private String cancontact;

    public EmploymentRecord() {
    }

    public String getCompanyname() {
        return companyname;
    }

    @XmlElement
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getRoledescription() {
        return roledescription;
    }

    @XmlElement
    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription;
    }

    public String getStartdate() {
        return startdate;
    }

    @XmlElement
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    @XmlElement
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getContactemail() {
        return contactemail;
    }

    @XmlElement
    public void setContactemail(String contactEmail) {
        this.contactemail = contactEmail;
    }

    public String getCancontact() {
        return cancontact;
    }

    @XmlElement
    public void setCancontact(String cancontact) {
        this.cancontact = cancontact;
    }

    @Override
    public String toString() {
        return "EmploymentRecord{" +
                "companyName='" + companyname + '\'' +
                ", roleDescription='" + roledescription + '\'' +
                ", startDate='" + startdate + '\'' +
                ", endDate='" + enddate + '\'' +
                ", contactEmail='" + contactemail + '\'' +
                ", canContact='" + cancontact + '\'' +
                '}';
    }
}
