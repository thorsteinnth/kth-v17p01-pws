package domain;

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

    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    public Company(String id, String name, String address, String phoneNumber, String email)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String toString()
    {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
