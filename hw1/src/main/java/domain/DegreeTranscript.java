package domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(propOrder = {"degree", "graduated", "course"})
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

    @Override
    public String toString() {
        return "DegreeTranscript{" +
                "degree='" + degree + '\'' +
                ", graduated='" + graduated + '\'' +
                ", course=" + course +
                '}';
    }
}
