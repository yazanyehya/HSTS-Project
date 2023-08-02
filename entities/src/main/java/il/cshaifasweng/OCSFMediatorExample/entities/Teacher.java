package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Teachers")
public class Teacher extends User implements Serializable
{
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "teacher_id")
    private List<Question> listOfQuestions;

    @ManyToMany(mappedBy = "listOfTeachers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @ManyToMany(mappedBy = "listOfTeachers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "principle_id")
    private Principle principle;


    public List<Course> getCourses() {
        return courses;
    }

    public List<Subject> getSubjects(){return subjects;}

    public Teacher(String firstName, String lastName, String username, String password, int whoAreYou)
    {
        super(firstName, lastName, username, password, whoAreYou, false);
        this.subjects = new ArrayList<Subject>();
        this.listOfQuestions = new ArrayList<Question>();
        this.courses = new ArrayList<Course>();
    }
    public Teacher(){
        this.listOfQuestions = new ArrayList<Question>();
        this.courses = new ArrayList<Course>();
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    public Principle getPrinciple() {
        return principle;
    }
    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }
}
