package domain;

import javax.xml.bind.annotation.XmlElement;

public class Course {

    private String name;
    private String grade;
    private String gradedecimal;
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

    public String getGradedecimal() {
        return gradedecimal;
    }

    @XmlElement
    public void setGradedecimal(String gradedecimal) {
        this.gradedecimal = gradedecimal;
    }

    public String getResult() {
        return result;
    }

    @XmlElement
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "Course{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", gradedecimal='" + gradedecimal + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
