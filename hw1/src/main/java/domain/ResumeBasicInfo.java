package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"ssn", "name", "address", "city", "country"})
public class ResumeBasicInfo {

    /*
    <ssn>8001010000</ssn>
        <name>Jon Jonsson</name>
        <address>Sveavägen 1</address>
        <city>Stockholm</city>
        <country>Sweden</country>
     */

    private String ssn;
    private String name;
    private String address;
    private String city;
    private String country;

    public ResumeBasicInfo() {

    }

    public String getSsn() {
        return ssn;
    }

    @XmlElement
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "ResumeBasicInfo{" +
                "ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
