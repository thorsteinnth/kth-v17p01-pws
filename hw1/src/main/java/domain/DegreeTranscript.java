package domain;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class DegreeTranscript {

    private String degree;
    private String graduated;
    private ArrayList<Course> course;

    public DegreeTranscript() {

    }

    public String getDegree() {
        return degree;
    }

    @XmlElement
    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGraduated() {
        return graduated;
    }

    @XmlElement
    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public ArrayList<Course> getCourse() {
        return course;
    }

    @XmlElement
    public void setCourse(ArrayList<Course> course) {
        this.course = course;
    }
}
