package domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
            <result>pass</result>
        </course>
        <course>
            <name>Modern Dance</name>
            <grade>F</grade>
            <result>fail</result>
        </course>
    </degreetranscript>
</transcript>
     */

    private String transcriptid;
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
}
