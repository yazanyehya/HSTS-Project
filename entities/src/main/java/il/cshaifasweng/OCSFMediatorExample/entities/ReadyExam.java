package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Ready_Exams")
public class ReadyExam implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String examType;

    @Column(name = "execution_code")
    private String executionCode;

    @Column(name = "Course")
    private String course;

    @Column(name = "username")
    private String username;

    @Column(name = "isClone")
    private String isClone;

    @Column(name = "approved")
    private String approved;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "examinee")
    private String examinee;

    private Integer originalId;

    private String FullName;

    private String studentId;
    @OneToOne
    @JoinColumn(name = "exam_id") // The foreign key column in the Ready_Exams table
    private Exam exam;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "ReadyExam_student",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> listOfStudents;



    public ReadyExam(String examType, String course, String executionCode,Exam exam, String isClone, String username, Integer originalId, String fullName, String studentId)
    {
        this.examType = examType;
        this.exam = exam;
        this.course = course;
        this.executionCode = executionCode;
        this.isClone = isClone;
        listOfStudents = new ArrayList<Student>();
        this.approved = "no";
        this.grade = 0;
        this.username = username;
        this.originalId = originalId;
        this.FullName = fullName;
        this.studentId = studentId;

    }

    public ReadyExam clone()
    {
        ReadyExam readyExam = new ReadyExam();
        readyExam.setExam(exam);
        readyExam.setExamType(examType);
        readyExam.setCourse(course);
        readyExam.setUsername(username);
        readyExam.setId(0);
        readyExam.isClone = "yes";
        readyExam.grade = 0;
        readyExam.approved = "no";
        readyExam.examinee = examinee;
        readyExam.FullName = FullName;
        readyExam.studentId = studentId;
        return readyExam;
    }
    public String getUsername() {
        return username;
    }

    public String getCourse() {
        return course;
    }

    public ReadyExam()
    {

    }


    public String getApproved() {
        return approved;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public String getIsClone() {
        return isClone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public void setExecutionCode(String executionCode) {
        this.executionCode = executionCode;
    }

    public void setIsClone(String isClone) {
        this.isClone = isClone;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getExaminee() {
        return examinee;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        //exam.setReadyExam(this);
    }

    public void setExaminee(String examinee) {
        this.examinee = examinee;
    }

    public String getExamType() {
        return examType;
    }

    public int getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public String getExecutionCode() {
        return executionCode;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
