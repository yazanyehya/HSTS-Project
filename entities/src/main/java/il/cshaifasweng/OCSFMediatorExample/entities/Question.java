 package il.cshaifasweng.OCSFMediatorExample.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.hibernate.Hibernate;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Questions")
public class Question implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String idd;
    @Column
    private String qText;
    @Column
    private String answer1;
    @Column
    private String answer2;
    @Column
    private String answer3;
    @Column
    private String answer4;

    @Column
    private Integer score;

    @Column
    private String isClone;
    @Column
    private String correctAnswer;

    @Column
    private String studentAnswer;

    @Column
    private String answeredCorrectly;

    @Column(name = "selected")
    private Boolean selected = false;

    @ManyToMany(mappedBy = "listOfQuestions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exam> exams;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany(mappedBy = "listOfQuestions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> course;

    public Question(String qText, String answer1, String answer2, String answer3, String answer4, Subject subject, Teacher teacher,String correctAnswer, List<Course> courseList, String isclone)
    {
        this.qText = qText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.setSubject(subject);
        this.teacher = teacher;
        this.course = new ArrayList<Course>();
        System.out.println("im here courses");
        this.setCourse(courseList);
        System.out.println("after im here courses");
        this.exams = new ArrayList<Exam>();
        this.correctAnswer = correctAnswer;
        this.score = 0;
        this.isClone = isclone;
        studentAnswer = "";
        this.answeredCorrectly = "no";

    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public Question clone() {
        Question clonedQuestion = new Question();
        clonedQuestion.setId(0);
        clonedQuestion.setqText(this.qText);
        clonedQuestion.setAnswer1(this.answer1);
        clonedQuestion.setAnswer2(this.answer2);
        clonedQuestion.setAnswer3(this.answer3);
        clonedQuestion.setAnswer4(this.answer4);
        clonedQuestion.setCorrectAnswer(this.correctAnswer);
        //clonedQuestion.setTeacher(this.teacher);
        //clonedQuestion.setSubject(this.subject);
        clonedQuestion.setScore(0);
        //clonedQuestion.setCourse(this.course);
        clonedQuestion.isClone = "yes";
        clonedQuestion.studentAnswer = "";
        clonedQuestion.answeredCorrectly = "no";
        //clonedQuestion.setSelected(this.selected);
        return clonedQuestion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Question other = (Question) obj;
        System.out.println("id = " + id+ " other.id =  " + other.id);
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qText);
    }
    public Boolean getSelected() {
        return selected;
    }

    public int getScore() {
        return score != null ? score : 0; // Return 0 as the default value if score is null
    }

    public void setScore(int score) {
        this.score = score;
    }


    public BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = false; // Set a default value if null
        }
        return new SimpleBooleanProperty(selected);
    }


    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Question()
    {
        exams = new ArrayList<Exam>();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
        subject.getListOfQuestions().add(this);
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public int getId() {
        return id;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getQText() {
        return qText;
    }

    public String getqText() {
        return qText;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(String answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
        for (Course c : course)
        {
            c.getListOfQuestions().add(this);
        }
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getIdd() {
        return idd;
    }
}
 