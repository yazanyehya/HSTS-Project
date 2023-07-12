package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exams")
public class Exam implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "teacherName")
    private String username;
    @Column(name = "examPeriod")
    private String examPeriod;

    private String teacherComments;

    private String studentComments;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "exam_question",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "listOfQuestions")
    private List<Question> listOfQuestions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "exam_student",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )

    private List<Student> listOfStudents;
    public Exam(String username, String examPeriod, Subject subject, Course course, String teacherComments, String studentComments)
    {
        this.username = username;
        this.examPeriod = examPeriod;
        this.listOfQuestions = new ArrayList<Question>();
        this.subject = subject;
        this.course = course;
        this.teacherComments = teacherComments;
        this.studentComments = studentComments;

    }

    public String getExamPeriod() {
        return examPeriod;
    }

    public void setExamPeriod(String examPeriod) {
        this.examPeriod = examPeriod;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Course getCourse() {
        return course;
    }

    public Exam()
    {
        this.listOfQuestions = new ArrayList<Question>();
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public String getStudentComments() {
        return studentComments;
    }

    public void setStudentComments(String studentComments) {
        this.studentComments = studentComments;
    }

    public void setTeacherComments(String teacherComments) {
        this.teacherComments = teacherComments;
    }

    public String getTeacherComments() {
        return teacherComments;
    }

}
