package domain;

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

    private String companyName;
    private String roleDescription;
    private String startDate;
    private String endDate;
    private String contactEmail;
    private String canContact;

    public EmploymentRecord() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCanContact() {
        return canContact;
    }

    public void setCanContact(String canContact) {
        this.canContact = canContact;
    }
}
