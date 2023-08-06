
package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.annotations.BatchSize;

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

    @Column(name = "username")
    private String username;

    @Column(name = "teacherFullName")
    private String teacherFullName;

    @Column(name = "examPeriod")
    private String examPeriod;

    private String teacherComments;

    private String studentComments;

    private String isClone;

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
            name = "Exam_student",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> listOfStudents;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Exam_Teacher",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Teacher> listOfTeachers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam")
    private List<ReadyExam> listOfReadyExams;

    private String idd;
    public Exam(String username,String teacherFullName, String examPeriod, Subject subject, Course course, String teacherComments, String studentComments, String isClone)
    {
        this.username = username;
        this.teacherFullName = teacherFullName;
        this.examPeriod = examPeriod;
        this.listOfQuestions = new ArrayList<Question>();
        this.listOfReadyExams = new ArrayList<ReadyExam>();
        this.subject = subject;
        this.course = course;
        this.teacherComments = teacherComments;
        this.studentComments = studentComments;
        this.isClone = isClone;
        this.listOfTeachers = new ArrayList<Teacher>();

    }
    public Exam clone()
    {
        Exam exam = new Exam();
        exam.username = username;
        exam.teacherFullName = teacherFullName;
        exam.course = course;
        exam.subject = subject;
        exam.examPeriod = examPeriod;
        exam.teacherComments = teacherComments;
        exam.studentComments = studentComments;
        exam.listOfQuestions = new ArrayList<Question>();
        exam.listOfReadyExams = new ArrayList<ReadyExam>();
        exam.listOfTeachers = new ArrayList<Teacher>();
        exam.id = 0;
        exam.isClone = "yes";
        return exam;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
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

    public List<ReadyExam> getListOfReadyExams() {
        return listOfReadyExams;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }
    //    public void setReadyExam(ReadyExam readyExam) {
//        this.readyExam = readyExam;
//        readyExam.setExam(this);
//    }
//
//    public ReadyExam getReadyExam() {
//        return readyExam;
//    }
}
 