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
    private String teacherName;
    @Column(name = "examPeriod")
    private String examPeriod;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    public Exam(String teacherName, String examPeriod)
    {
        this.teacherName = teacherName;
        this.examPeriod = examPeriod;
        this.listOfQuestions = new ArrayList<Question>();
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
}
