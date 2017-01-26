package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Companies {

    private ArrayList<Company> companyinfo;

    public Companies() {

    }

    public ArrayList<Company> getCompanyinfo() {
        return companyinfo;
    }

    @XmlElement
    public void setCompanyinfo(ArrayList<Company> companies) {
        this.companyinfo = companies;
    }

    @Override
    public String toString() {
        return "Companies{" +
                "companyinfo=" + companyinfo +
                '}';
    }
}
