package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class EmploymentRecords {

    private String ssn;
    private List<EmploymentRecord> employment;

    public EmploymentRecords() {

    }

    public String getSsn() {
        return ssn;
    }

    @XmlElement
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public List<EmploymentRecord> getEmployment() {
        return employment;
    }

    @XmlElement
    public void setEmployment(List<EmploymentRecord> employment) {
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
