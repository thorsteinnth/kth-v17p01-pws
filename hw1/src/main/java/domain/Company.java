package domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "address", "phonenumber", "email"})
public class Company
{
    /*
    <companyinfo companyid="1234567891">
        <name>LS Retail</name>
        <address>Katrinartun 1, 105 Reykjavik, Iceland</address>
        <phonenumber>12345678912345</phonenumber>
        <email>contact@lsretail.com</email>
    </companyinfo>
    */

    private String companyid;
    private String name;
    private String address;
    private String phonenumber;
    private String email;

    public Company() {

    }

    public Company(String companyid, String name, String address, String phonenumber, String email)
    {
        this.companyid = companyid;
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
    }

    public String getCompanyid()
    {
        return companyid;
    }

    @XmlAttribute
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getName()
    {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    @XmlElement
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail()
    {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "Company{" +
                "id='" + companyid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
