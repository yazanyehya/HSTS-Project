package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Subjects")
public class Subject implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private List<Question> listOfQuestions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private List<Exam> listOfExams;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> listOfTeachers;

    public Subject(String name)
    {
        this.name = name;
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
        courses = new ArrayList<Course>();
    }
    public Subject()
    {
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
        courses = new ArrayList<Course>();
    }

    public int getId() {
        return id;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void setListOfTeachers(List<Teacher> listOfTeachers) {
        this.listOfTeachers = listOfTeachers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
