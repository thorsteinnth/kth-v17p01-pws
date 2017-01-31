package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
public class Employmentrecord {

    private String ssn;
    private List<Employment> employment;

    public Employmentrecord() {
    }

    public String getSsn() {
        return ssn;
    }

    @XmlElement
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public List<Employment> getEmployment() {
        return employment;
    }

    @XmlElement
    public void setEmployment(List<Employment> employment) {
        this.employment = employment;
    }

    @Override
    public String toString() {
        return "EmploymentRecords{" +
                "ssn='" + ssn + '\'' +
                ", employment=" + employment +
                '}';
    }
}
