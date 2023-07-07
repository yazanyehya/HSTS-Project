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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany(mappedBy = "listOfTeachers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher(String firstName, String lastName, String username, String password, int whoAreYou, Subject subject)
    {
        super(firstName, lastName, username, password, whoAreYou, false);
        this.subject = subject;
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

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
