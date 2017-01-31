package domain;

import javax.xml.bind.annotation.XmlElement;

public class EmploymentRecord {

    /*
    <employment>
        <companyname>Polar Seafood</companyname>
        <roledescription>Fisherman</roledescription>
        <startdate>2012-12-13</startdate>
        <enddate>2014-01-25</enddate>
    </employment>
     */

    private String companyname;
    private String roledescription;
    private String startdate;
    private String enddate;

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

    @Override
    public String toString()
    {
        return "EmploymentRecord{" +
                "companyname='" + companyname + '\'' +
                ", roledescription='" + roledescription + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                '}';
    }
}
