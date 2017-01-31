package domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = {"ssn", "schoolname", "date", "degreetranscript"})
@XmlRootElement
public class Transcript {

    /*
    <transcript transcriptid="123456">
    <ssn>8903298888</ssn>
    <schoolname>Kungliga Tekniska Högskolan</schoolname>
    <date>2017-01-25</date>
    <degreetranscript>
        <degree>Software Engineering of Distributed Systems</degree>
        <graduated>false</graduated>
        <course>
            <name>Programming Web Services</name>
            <grade>A</grade>
            <gradedecimal>9.5</gradedecimal>
            <result>pass</result>
        </course>
        <course>
            <name>Modern Dance</name>
            <grade>F</grade>
            <gradedecimal>9.5</gradedecimal>
            <result>fail</result>
        </course>
    </degreetranscript>
</transcript>
     */

    private String transcriptid;
    private String ssn;
    private String schoolname;
    private String date;
    private List<DegreeTranscript> degreetranscript;

    public Transcript() {

    }

    public String getTranscriptid() {
        return transcriptid;
    }

    @XmlAttribute
    public void setTranscriptid(String id) {
        this.transcriptid = id;
    }

    public String getSsn() {
        return this.ssn;
    }

    @XmlElement
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSchoolname() {
        return schoolname;
    }

    @XmlElement
    public void setSchoolname(String schoolName) {
        this.schoolname = schoolName;
    }

    public String getDate() {
        return date;
    }

    @XmlElement
    public void setDate(String date) {
        this.date = date;
    }

    public List<DegreeTranscript> getDegreetranscript() {
        return degreetranscript;
    }

    @XmlElement
    public void setDegreetranscript(List<DegreeTranscript> degreeTranscript) {
        this.degreetranscript = degreeTranscript;
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "transcriptid='" + transcriptid + '\'' +
                ", schoolname='" + schoolname + '\'' +
                ", date='" + date + '\'' +
                ", degreetranscript=" + degreetranscript +
                '}';
    }
}
