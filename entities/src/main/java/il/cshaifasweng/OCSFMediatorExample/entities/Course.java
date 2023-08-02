package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_question",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "listOfQuestions")
    private List<Question> listOfQuestions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Exam> listOfExams;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "principle_id")
    private Principle principle;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> listOfTeachers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> listOfStudents;

    public Course(String name)
    {
        this.name = name;
        this.listOfQuestions = new ArrayList<Question>();
        this.listOfTeachers = new ArrayList<Teacher>();
        this.listOfStudents = new ArrayList<Student>();
    }
    public Course()
    {
        this.listOfQuestions = new ArrayList<Question>();
        this.listOfTeachers = new ArrayList<Teacher>();
        this.listOfStudents = new ArrayList<Student>();

    }
    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public String getName() {
        return name;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }

    public int getId() {
        return id;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListOfTeachers(List<Teacher> listOfTeachers) {
        this.listOfTeachers = listOfTeachers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public List<Exam> getListOfExams() {
        return listOfExams;
    }

    public Principle getPrinciple() {
        return principle;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public void setListOfExams(List<Exam> listOfExams) {
        this.listOfExams = listOfExams;
    }

    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }
}
