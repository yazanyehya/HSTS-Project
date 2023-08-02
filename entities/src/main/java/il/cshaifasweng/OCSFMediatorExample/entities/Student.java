package il.cshaifasweng.OCSFMediatorExample.entities;;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name="students")
public class Student  extends User implements Serializable
{

    @ManyToMany(mappedBy = "listOfStudents", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReadyExam> exams;

    @ManyToMany(mappedBy = "listOfStudents", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @ManyToMany(mappedBy = "listOfStudents", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "principle_id")
    private Principle principle;

    public Student(String firstName, String lastName, String username, String password, int whoAreYou)
    {
        super(firstName, lastName, username, password, whoAreYou, false);
        this.subjects = new ArrayList<Subject>();
        this.exams = new ArrayList<ReadyExam>();
        this.courses = new ArrayList<Course>();
    }
    public Student(){
        this.subjects = new ArrayList<Subject>();
        this.exams = new ArrayList<ReadyExam>();
        this.courses = new ArrayList<Course>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ReadyExam> getExams() {
        return exams;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
