package domain;

import javax.xml.bind.annotation.XmlElement;

public class Course {

    private String name;
    private String grade;
    private String result;

    public Course() {
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    @XmlElement
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getResult() {
        return result;
    }

    @XmlElement
    public void setResult(String result) {
        this.result = result;
    }
}
