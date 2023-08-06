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

    private String idd;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "principle_id")
    private Principle principle;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "subject_teacher",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> listOfTeachers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> listOfStudents;

    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }

    public Principle getPrinciple() {
        return principle;
    }

    public Subject(String name)
    {
        this.name = name;
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
        this.listOfStudents = new ArrayList<Student>();
        courses = new ArrayList<Course>();
    }
    public Subject()
    {
        listOfQuestions = new ArrayList<Question>();
        listOfTeachers = new ArrayList<Teacher>();
        courses = new ArrayList<Course>();
        this.listOfStudents = new ArrayList<Student>();

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

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public List<Exam> getListOfExams() {
        return listOfExams;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getIdd() {
        return idd;
    }
}
 